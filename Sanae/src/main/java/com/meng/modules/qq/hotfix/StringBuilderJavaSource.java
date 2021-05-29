package com.meng.modules.qq.hotfix;

import java.net.URI;
import javax.tools.SimpleJavaFileObject;

public class StringBuilderJavaSource extends SimpleJavaFileObject {
    private StringBuilder code = new StringBuilder();

    public StringBuilderJavaSource(String name) {
        super(URI.create("string:///" + name.replace(".", "/") + Kind.SOURCE.extension), Kind.SOURCE);
    }

    public CharSequence getCharContent(boolean ignoreEncodingError) {
        return code;
    }

    public StringBuilderJavaSource append(String str) {
        code.append(str).append("\n");
        return this;
    }
}
