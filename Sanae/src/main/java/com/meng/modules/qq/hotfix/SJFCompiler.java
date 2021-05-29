package com.meng.modules.qq.hotfix;

import com.meng.modules.qq.ModuleManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import com.meng.modules.qq.SBot;

/**
 * @author 司徒灵羽
 */

public class SJFCompiler {

    public static HotfixClassLoader generate(HotfixClassLoader classloader, String className, String code) throws Exception {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        final List<ByteArrayJavaClass> classFileObjects = new ArrayList<>();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        JavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
        fileManager = new ForwardingJavaFileManager<JavaFileManager>(fileManager){
            public JavaFileObject getJavaFileForOutput(JavaFileManager.Location location, String className, JavaFileObject.Kind kind, FileObject sibling) {
                ByteArrayJavaClass fileObject = new ByteArrayJavaClass(className);
                classFileObjects.add(fileObject);
                return fileObject;
            }
        };
        StringBuilderJavaSource source = new StringBuilderJavaSource(className);
        source.append(code);
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, Arrays.asList(source));
        boolean result = task.call();
        StringBuilder builder = new StringBuilder();
        for (Diagnostic<? extends JavaFileObject> d : diagnostics.getDiagnostics()) {
            builder.append(d.getKind()).append(":").append(d.getMessage(null)).append("\n");  
            SBot.instance.sendGroupMessage(SBot.yysGroup, builder.toString());
        }
        fileManager.close();
        if (!result) {
            System.out.println("compile failed");
            return classloader;
        }
        for (ByteArrayJavaClass cl:classFileObjects) {
            classloader.put(cl.getName().substring(1), cl.getBytes());
            System.out.println(cl.getName().substring(1));
        }
        return classloader;
    }
}
