package zzj.common;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonFunc {
    
    public static final boolean isInteger(String str) {
        boolean res = false;
        if ((str != null) && (!str.isEmpty())) {
            Pattern pattern = Pattern.compile("^-?\\d+$");   
            Matcher isNum = pattern.matcher(str);  
            if(isNum.matches()) {
                res = true;
            }
        }
        return res;   
    }
    
    public static boolean writeByteToStream(byte[] b, OutputStream outputStream) {         
        try {
            BufferedOutputStream bos = new BufferedOutputStream(outputStream);
            bos.write(b);
            bos.flush();
            bos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
