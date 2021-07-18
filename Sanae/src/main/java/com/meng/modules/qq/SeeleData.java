package com.meng.modules.qq;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.io.File;

public class SeeleData {
    
    public List<String> whiteText = Collections.unmodifiableList(new ArrayList<String>(){
            {
             //   add("");
            } 
        });
        
    public List<String> mixText = Collections.unmodifiableList(new ArrayList<String>(){
            {
            //    add("");
            } 
        });
        
    public List<String> blackText = Collections.unmodifiableList(new ArrayList<String>(){
            {
                add("我就是希儿呦，希儿认证的希儿~");
            } 
        });
        
    public List<File> whiteVoice = Collections.unmodifiableList(new ArrayList<File>(){
            {
             //   add("");
            } 
        });

    public List<File> mixVoice = Collections.unmodifiableList(new ArrayList<File>(){
            {
           //     add("");
            } 
        });

    public List<File> blackVoice = Collections.unmodifiableList(new ArrayList<File>(){
            {
            //    add("我就是希儿呦，希儿认证的希儿~");
            } 
        }); 
        
}
