package com.meng.modules.windows;

import com.meng.tools.ExceptionCatcher;

public class CmdExecuter {
    
    public static void Main(String... args){
        StringBuilder sb = new StringBuilder();
        sb.append("python\nfrom threp import THReplay\ntr=THReplay(\"C:\\th7_03.rpy\")\nprint(tr.getBaseInfo())");
        execute(sb.toString());
    }
    
    public static boolean execute(String command) {
        try { 
            String[] cmd = { "cmd.exe", "/C", command };
            System.out.println("Execing " + cmd[0] + " " + cmd[1]  + " " + cmd[2]);
            Process proc = Runtime.getRuntime().exec(cmd);
            StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "error");
            StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "output");
            errorGobbler.start();
            outputGobbler.start();
            int exitVal = proc.waitFor();
            System.out.println("ExitValue: " + exitVal);
            if (exitVal != 0) {
                return false;
            }
        } catch (Throwable t) {
            t.printStackTrace();
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), t);
            return false;
        }
        return true;
    }
}
