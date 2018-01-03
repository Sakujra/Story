package com.vincentlaf.story.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPOutputStream;

/**
 * Created by Johnson on 2018/1/3.
 */

public class StringUtil {

    public static String file2String(File file) throws IOException {

        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream((int)file.length());
        BufferedInputStream bufferedInputStream=new BufferedInputStream(new FileInputStream(file));
        byte[] temp=new byte[1024];
        int len;
        while((len=bufferedInputStream.read(temp,0,temp.length))!=-1){
            byteArrayOutputStream.write(temp,0,len);
        }
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        bufferedInputStream.close();
        return byteArrayOutputStream.toString("ISO-8859-1");
    }

    public static void string2File(String string,String filepath,String filename) throws IOException {
        byte[] data=string.getBytes("ISO-8859-1");
        FileOutputStream fout=new FileOutputStream(new File(filepath,filename));
        fout.write(data);
        fout.flush();
        fout.close();
    }
}
