package zzj.service.impl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import zzj.common.Constants;
import zzj.common.FileOperator;
import zzj.common.ImageOperator;
import zzj.bean.Image;
import zzj.dao.IImageDao;
import zzj.service.IPhotoService;
import zzj.common.CommonFunc;

@Service("photoService")
public class PhotoServiceImpl implements IPhotoService {
    
    @Resource
    private IImageDao imageDao;

    @Override
    public List<String> getPhotoIdList(String keyword, String startDate, String endDate) {
        List<String> lst = new ArrayList<String>();
        Map<Integer,Integer> map = new LinkedHashMap<Integer,Integer>();
        int conditionCount = 0;
        if (keyword != null && !keyword.equals("")) {
            conditionCount += 1;
            String stdKeyword = keyword.replace("，",",");
            String[] keywordArray = stdKeyword.split(",");
            for (int i = 0; i < keywordArray.length; i++) {
                String oneWord = keywordArray[i];
                List<Integer> lstId = imageDao.findIdListByKeyword(oneWord);
                if (lstId != null && !lstId.isEmpty()) {
                    for (Integer id : lstId) {
                        if (!map.containsKey(id)) {
                            map.put(id, 1);
                        }
                    }
                }
            }
        }
        if ((startDate != null && !startDate.equals("")) || (endDate != null && !endDate.equals(""))) {
            conditionCount += 1;
            Date sd = null;
            Date ed = null;
            if (startDate != null && !startDate.equals("")) {
                sd = Date.valueOf(startDate);
            }
            if (endDate != null && !endDate.equals("")) {
                ed = Date.valueOf(endDate);
            }
            List<Integer> lstId = imageDao.findIdListByDate(sd, ed);
            if (lstId != null && !lstId.isEmpty()) {
                for (Integer id : lstId) {
                    if (!map.containsKey(id)) {
                        map.put(id, 1);
                    }
                    else {
                        map.put(id, 2);
                    }
                }
            }
        }
        if (conditionCount == 0) {
            List<Integer> lstId = imageDao.findIdList();
            if (lstId != null && !lstId.isEmpty()) {
                for (Integer id : lstId) {
                    lst.add(id.toString());
                }
            }
        }
        else {
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                if (entry.getValue().intValue() == conditionCount) {
                    lst.add(entry.getKey().toString());
                }
            }
        }
        return lst;
    }

    @Override
    public byte[] getThumbData(String strId) {
        byte[] data = null;
        if (CommonFunc.isInteger(strId)) {
            int id = Integer.parseInt(strId);
            Image img = imageDao.findById(id);
            if (img != null) {
                data = img.getImageThumbData();
            }
        }
        return data;
    }

    @Override
    public byte[] getRawImageData(String strId) {
        byte[] data = null;
        if (CommonFunc.isInteger(strId)) {
            int id = Integer.parseInt(strId);
            Image img = imageDao.findById(id);
            if (img != null) {
                data = img.getImageRawData();
            }
        }
        return data;
    }

    @Override
    public Map<String, String> getDateAndDecription(String strId) {
        Map<String, String> map = new LinkedHashMap<String, String>();
        if (CommonFunc.isInteger(strId)) {
            int id = Integer.parseInt(strId);
            Image img = imageDao.findById(id);
            if (img != null) {
                map.put("date", img.getImageUploadDate().toString());
                map.put("description", img.getImagedescription());
                map.put("dataLength", String.valueOf(img.getImageRawData().length));
            }
        }
        return map;
    }
    
    @Override
    public byte[] saveTemporaryImg(MultipartFile imgFile, String servletRealPath) throws IOException {
        // TODO Auto-generated method stub
        byte[] b = {};
        
        String imgTempForder = servletRealPath + "/" + Constants.TEMP_FOLDER + "/";
        String orgFileName = imgFile.getOriginalFilename();
        
        /* 从AJAX获得文件输入流 */
        BufferedInputStream bis = new BufferedInputStream(imgFile.getInputStream());
        if(ImageOperator.checkImageName(orgFileName)) {
            /* 保存原始图片 */
            String entireFilePath = imgTempForder + Constants.TEMP_PHOTO_NAME;
            FileOperator.writeStreamToFile(bis, entireFilePath, true);
            /* 生成缩略图 */
            ImageOperator thumbImg = new ImageOperator(entireFilePath);
            b = thumbImg.makeThumbnailOuter(Constants.IMG_UPLOAD_THUMB_W, Constants.IMG_UPLOAD_THUMB_H);
        }
        return b;
    }

    @Override
    public String saveImgToDB(String servletRealPath, String fileName, String description) {
        // TODO Auto-generated method stub
        String id = "";
        if (ImageOperator.checkImageName(fileName)) {
            String imageName = fileName.substring(0, fileName.lastIndexOf("."));
            String imageRawFormat = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            ImageOperator rawImg = new ImageOperator(servletRealPath + "/" + Constants.TEMP_FOLDER + "/" + Constants.TEMP_PHOTO_NAME);
            byte[] imgRawData = rawImg.imageToBytes(imageRawFormat.toUpperCase());
            byte[] imgThumbData = rawImg.makeThumbnailBaseOnFirmWidth(Constants.WALL_THUMB_W);
            java.util.Date today = new java.util.Date();
            java.sql.Date date = new java.sql.Date(today.getTime());
            Image image = new Image();
            image.setImageName(imageName);
            image.setImageRawFormat(imageRawFormat);
            image.setImageRawData(imgRawData);
            image.setImageThumbData(imgThumbData);
            image.setImagedescription(description);
            image.setImageUploadDate(date);
            this.imageDao.insertRecord(image);
            id = String.valueOf(image.getImageId());
        }
        return id;
    }
}
