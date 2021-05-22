package com.meng.modules;

import com.meng.tools.ExceptionCatcher;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import com.meng.tools.SJFExecutors;

public class CmdExecuter implements AutoCloseable {

    private OnOutputListener listener;
    private Process process;
    private StreamGobbler sg0;
    private StreamGobbler sg1;

    private CmdExecuter(){
        
    }
    
    public void setProcess(Process process) {
        this.process = process;
    }

    private StreamGobbler createStreamGobbler(InputStream is, int i) {
        if (i == 0) {
            return sg0 = new StreamGobbler(is);
        }
        return sg1 = new StreamGobbler(is);
    }

    @Override
    public void close() throws Exception {
        process.destroy();
    }

    public Process getProcess() {
        return process;
    }

    public void setOnOutputListener(OnOutputListener listener) {
        this.listener = listener;
    }

    public interface OnOutputListener {
        public void onOutput(String output);
    }

    public void write(String s){
        try {
            process.getOutputStream().write(s.getBytes());
        } catch (IOException e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
        }
    }
    
    public static Process run(String[] commandString) {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(commandString);
        } catch (IOException e) {
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), e);
            process.destroy();
        }
        return process;
    }

    private class StreamGobbler implements Runnable {
        private InputStream is;

        private StreamGobbler(InputStream is) {
            this.is = is;
        }

        public void run() {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line = null;
                while ((line = br.readLine()) != null) {
                    if (listener != null) {
                        listener.onOutput(line);
                    }
                }
            } catch (IOException ioe) {
                ioe.printStackTrace(); 
            }
        }
    }

    public static CmdExecuter execute(String command, OnOutputListener listener) {
        try {
            CmdExecuter cmdExecuter = new CmdExecuter();
            cmdExecuter.setOnOutputListener(listener);
            
            String[] cmd = { "cmd.exe", "/C", command };
            System.out.println("Execing " + cmd[0] + " " + cmd[1]  + " " + cmd[2]);
            Process proc = Runtime.getRuntime().exec(cmd);
            cmdExecuter.setProcess(proc);
            SJFExecutors.execute(cmdExecuter.createStreamGobbler(proc.getErrorStream(), 0));
            SJFExecutors.execute(cmdExecuter.createStreamGobbler(proc.getInputStream(), 1));
            return cmdExecuter;
        } catch (Throwable t) {
            t.printStackTrace();
            ExceptionCatcher.getInstance().uncaughtException(Thread.currentThread(), t);
            return null;
        }
    }
}
