package zzj.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.fileupload.util.Streams;

public class FileOperator {

    private File file = null;
    private long fileSize = 0;
    private FileInputStream fileInputStream = null;
    private byte[] bytesArray = null;
    
    public FileOperator(String filePath) {
        this.file = new File(filePath);
        try {
            this.fileInputStream = new FileInputStream(this.file);
            this.fileSize = this.file.length();  
            if (this.fileSize < Integer.MAX_VALUE) {  
                ByteArrayOutputStream bos = new ByteArrayOutputStream((int)this.fileSize);
                int buf_size = 1024;
                byte[] buffer = new byte[buf_size];  
                int len = 0;  
                while (-1 != (len = this.fileInputStream.read(buffer, 0, buf_size))) {  
                    bos.write(buffer, 0, len);  
                }  
                this.bytesArray = bos.toByteArray();
                bos.flush();
                bos.close();
            } 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public byte[] getFileBytesData() {
        return this.bytesArray;
    }
    
    public int getFileSize() {
        return (int) this.fileSize;
    }
    
    public String getFileMD5() {
        String result = "";
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(this.bytesArray);
            BigInteger bigInt = new BigInteger(1, messageDigest.digest());
            result = bigInt.toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * @param src
     * @param des
     * @param overlay true:overwrite permit
     * @return
     */
    public static boolean copyFile(String src, String des, boolean overlay) {
        /* 一時的なフォルダから目標フォルダにコピーする */
        File srcFile = new File(src);
        if (!srcFile.exists()) {    
            return false;  
        } else if (!srcFile.isFile()) {   
            return false;  
        }
        
        File destFile = new File(des);
        if (destFile.exists()) {    
            /* ファイルが存在する場合 */
            if (overlay) {   
                new File(des).delete();  
            }  
        }
        else {  
            /* フォルダが存在しない場合、作成する  */
            if (!destFile.getParentFile().exists()) {  
                if (!destFile.getParentFile().mkdirs()) {
                    return false;
                }
            }  
        }
        
        InputStream in = null;  
        OutputStream out = null;  
  
        try {  
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            in.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static void delFolder(String folderPath) {
        File file = new File(folderPath);
        if(file.exists()) {
            delFile(file);
        }
    }
    
    public static void delFile(File file) {
        if(file.isDirectory()) {
            for (File fi : file.listFiles()) {
                delFile(fi);
            }
            file.delete();
        }
        else {
            file.delete();
        }
    }
    
    public static void delFile(String filePath) {
        File file = new File(filePath);
        if(file.exists()) {
            delFile(file);
        }
    }
    
    /**
     * @param src
     * @param des
     * @param overlay true:overwrite permit
     * @return
     */
    public static boolean writeByteToFile(byte[] b, String des, boolean overlay) {        
        File destFile = new File(des);
        if (destFile.exists()) {    
            /* ファイルが存在する場合 */
            if (overlay) {   
                new File(des).delete();  
            }  
        }
        else {  
            /* フォルダが存在しない場合、作成する  */
            if (!destFile.getParentFile().exists()) {  
                if (!destFile.getParentFile().mkdirs()) {
                    return false;
                }
            }  
        } 
        try {
            FileOutputStream fos = new FileOutputStream(destFile);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bos.write(b);
            bos.flush();
            fos.flush();
            bos.close();
            fos.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
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
    
    public static boolean writeStreamToFile(BufferedInputStream bis, String des, boolean overlay) {
        File destFile = new File(des);
        if (destFile.exists()) {    
            /* ファイルが存在する場合 */
            if (overlay) {   
                new File(des).delete();  
            }  
        }
        else {  
            /* フォルダが存在しない場合、作成する  */
            if (!destFile.getParentFile().exists()) {  
                if (!destFile.getParentFile().mkdirs()) {
                    return false;
                }
            }  
        } 
        try {
            FileOutputStream fos = new FileOutputStream(new File(des));
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            Streams.copy(bis, bos, true);
            bos.flush();
            bos.close();
            fos.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
