package com.meng.modules;

import com.meng.tools.ExceptionCatcher;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class CmdExecuter {

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

    public static class StreamGobbler extends Thread {
        private InputStream is;
        private String type;
        private OutputStream os;

        public StreamGobbler(InputStream is, String type) {
            this(is, type, null);
        }

        public StreamGobbler(InputStream is, String type, OutputStream redirect) {
            this.is = is;
            this.type = type;
            this.os = redirect;
        }

        public void run() {
            try {
                //   try(PrintWriter pw = new PrintWriter(os)){

                //     }
                PrintWriter pw = null;
                if (os != null) {
                    pw = new PrintWriter(os);
                }
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line = null;
                while ((line = br.readLine()) != null) {
                    if (pw != null) {
                        pw.println(line);
                    }
                    System.out.println(type + ">" + line); 
                }
                if (pw != null) {
                    pw.flush();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace(); 
            }
        }
    }
}
