package zzj.common;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

public class ImageOperator {

    private BufferedImage src = null;
    private int width = 0;
    private int height = 0;
    private int type = 0;
    
    public ImageOperator(String filePath) {
        try {
            src = ImageIO.read(new File(filePath));
            width = src.getWidth();
            height = src.getHeight();
            type = src.getType(); 
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public ImageOperator(byte[] imageBytes) {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(imageBytes);
            src = ImageIO.read(in);
            width = src.getWidth();
            height = src.getHeight();
            type = src.getType(); 
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public byte[] makeThumbnailInner(int w, int h) {
        byte[] b = {};
        BufferedImage outputImage = null;
        if ((src != null) && (width != 0) && (height != 0) && (w != 0) && (h != 0)) {
            double ratioW = (double)w / width;
            double ratioH = (double)h / height;
            /* 按宽缩放,高有盈余 */
            if (ratioW > ratioH) {
                /* 计算新高 */
                int newH = (int) (height * ratioW);
                /* 按宽缩放 */
                Image image = src.getScaledInstance(w, newH, Image.SCALE_SMOOTH);
                BufferedImage scaledImage = new BufferedImage(w, newH, type);
                Graphics g = scaledImage.getGraphics();
                g.drawImage(image, 0, 0, null);
                g.dispose();
                /* 计算高的上部盈余 */
                int surplusTop = (newH - h) / 2;
                /* 切割图片 */
                outputImage = scaledImage.getSubimage(0, surplusTop, w, h);
            }
            /* 按高缩放,宽有盈余 */
            else {
                /* 计算新宽 */
                int newW = (int) (width * ratioH);
                /* 按高缩放 */
                Image image = src.getScaledInstance(newW, h, Image.SCALE_SMOOTH);
                BufferedImage scaledImage = new BufferedImage(newW, h, type);
                Graphics g = scaledImage.getGraphics();
                g.drawImage(image, 0, 0, null);
                g.dispose();
                /* 计算宽的左部盈余 */
                int surplusLeft = (newW - w) / 2;
                /* 切割图片 */
                outputImage = scaledImage.getSubimage(surplusLeft, 0, w, h);
            }
        }
        if (outputImage != null) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                ImageIO.write(outputImage, "PNG", out);
                b = out.toByteArray();
                out.flush();
                out.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return b;
    }
    
    public byte[] makeThumbnailOuter(int w, int h) {
        byte[] b = {};
        BufferedImage outputImage = null;
        if ((src != null) && (width != 0) && (height != 0) && (w != 0) && (h != 0)) {
            double ratioW = (double)w / width;
            double ratioH = (double)h / height;
            /* 按高缩放,宽有不足 */
            if (ratioW > ratioH) {
                /* 计算新宽 */
                int newW = (int) (width * ratioH);
                /* 左边盈余 */
                int offsetX = (w - newW) / 2;
                /* 按高缩放 */
                Image image = src.getScaledInstance(newW, h, Image.SCALE_SMOOTH);
                /* 生成一个标准图 */
                outputImage = new BufferedImage(w, h, type);
                Graphics g = outputImage.getGraphics();
                /* 设置背景色（白色） */
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, w, h);
                /* 拼合图像 */
                g.drawImage(image, offsetX, 0, newW, h, null);
                g.dispose();
            }
            /* 按宽缩放,高有不足 */
            else {
                /* 计算新高 */
                int newH = (int) (height * ratioW);
                /* 上边盈余 */
                int offsetY = (h - newH) / 2;
                /* 按宽缩放 */
                Image image = src.getScaledInstance(w, newH, Image.SCALE_SMOOTH);
                /* 生成一个标准图 */
                outputImage = new BufferedImage(w, h, type);
                Graphics g = outputImage.getGraphics();
                /* 设置背景色（白色） */
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, w, h);
                /* 拼合图像 */
                g.drawImage(image, 0, offsetY, w, newH, null);
                g.dispose();
            }
        }
        if (outputImage != null) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                ImageIO.write(outputImage, "PNG", out);
                b = out.toByteArray();
                out.flush();
                out.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return b;
    }
    
    public byte[] makeThumbnailBaseOnFirmWidth(int w) {
        byte[] b = {};
        BufferedImage outputImage = null;
        if ((src != null) && (width != 0) && (height != 0) && (w != 0)) {
            double ratioW = (double)w / width;
            /* 计算新高 */
            int newH = (int) (height * ratioW);
            /* 按宽缩放 */
            Image image = src.getScaledInstance(w, newH, Image.SCALE_SMOOTH);
            /* 生成一个标准图 */
            outputImage = new BufferedImage(w, newH, type);
            Graphics g = outputImage.getGraphics();
            /* 拼合图像 */
            g.drawImage(image, 0, 0, null);
            g.dispose();
        }
        if (outputImage != null) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                ImageIO.write(outputImage, "PNG", out);
                b = out.toByteArray();
                out.flush();
                out.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return b;
    }
    
    public byte[] makeThumbnailBaseOnFirmHeight(int h) {
        byte[] b = {};
        BufferedImage outputImage = null;
        if ((src != null) && (width != 0) && (height != 0) && (h != 0)) {
            double ratioH = (double)h / height;
            /* 计算新宽 */
            int newW = (int) (width * ratioH);
            /* 按高缩放 */
            Image image = src.getScaledInstance(newW, h, Image.SCALE_SMOOTH);
            /* 生成一个标准图 */
            outputImage = new BufferedImage(newW, h, type);
            Graphics g = outputImage.getGraphics();
            /* 拼合图像 */
            g.drawImage(image, 0, 0, null);
            g.dispose();
        }
        if (outputImage != null) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                ImageIO.write(outputImage, "PNG", out);
                b = out.toByteArray();
                out.flush();
                out.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return b;
    }
    
    public byte[] imageToBytes(String format) {
        byte[] b = {};
        if (src != null) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                ImageIO.write(src, format, out);
                b = out.toByteArray();
                out.flush();
                out.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return b;
    }
    
    public static final boolean checkImageFormat(String format) {
        boolean res = false;
        if ((format != null) && (!format.isEmpty())) {
            String str = format.toLowerCase();
            if (str.equals("png") ||  
                str.equals("jpg") ||  
                str.equals("jpeg") || 
                str.equals("gif")) {
                res = true;
            }
        }
        return res;
    }
    
    public static final boolean checkImageName(String name) {
        boolean res = false;
        if ((name != null) && (!name.isEmpty())) {
            String str = name.toLowerCase();
            Pattern reg = Pattern.compile("[.]jpg|png|jpeg|gif$");
            Matcher matcher = reg.matcher(str);
            if(matcher.find()) {
                res = true;
            }
        }
        return res;
    }
    
    /**
     * @param des
     * @param overlay true:overwrite permit
     * @return
     */
    public boolean writeImageToFile(String des, String format, boolean overlay) {        
        if (src != null) {
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
                ImageIO.write(src, format, bos);
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
        else {
            return false;
        }
    }
    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getType() {
        return type;
    }
}
