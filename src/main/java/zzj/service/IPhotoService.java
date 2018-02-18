package zzj.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface IPhotoService {
    
    List<String> getPhotoIdList(String keyword, String startDate, String endDate);
    
    byte[] getThumbData(String strId);
    
    byte[] getRawImageData(String strId);
    
    Map<String, String> getDateAndDecription(String strId);
    
    byte[] saveTemporaryImg(MultipartFile imgFile, String servletRealPath) throws IOException;
    
    String saveImgToDB(String servletRealPath, String fileName, String description);
    
    String updateDescription(String strId, String description);
    
    String deletePhoto(String strId);

}
