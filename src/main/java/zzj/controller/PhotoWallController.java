package zzj.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;

import zzj.service.IPhotoService;
import zzj.common.FileOperator;

@Controller
public class PhotoWallController {

    @Resource
    private IPhotoService photoService;
    
    @RequestMapping(value = "/getPhotoIdList", method =RequestMethod.POST)
    @ResponseBody
    public String getPhotoIdList(@RequestBody Map<String, String> data) {
        String keyword = "";
        String startDate = "";
        String endDate = "";
        if (data != null && data.containsKey("keyword") 
                && data.containsKey("startDate") && data.containsKey("endDate")) {
            keyword = data.get("keyword");
            startDate = data.get("startDate");
            endDate = data.get("endDate");
        }
        List<String> lst = photoService.getPhotoIdList(keyword, startDate, endDate);
        String json = JSONArray.toJSONString(lst);
        return json;
    }
    
    @RequestMapping(value = "/getThumb", method =RequestMethod.POST)
    public void getThumb(HttpServletRequest request, HttpServletResponse response) {
        
        String targetPhotoId = request.getParameter("id");
        byte[] data = photoService.getThumbData(targetPhotoId);
        response.setContentType("image/png");  
        response.setCharacterEncoding("utf-8");
        try {
            OutputStream outputStream = response.getOutputStream();
            FileOperator.writeByteToStream(data, outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @RequestMapping(value = "/getRawPhoto", method =RequestMethod.POST)
    public void getRawPhoto(HttpServletRequest request, HttpServletResponse response) {
        
        String targetPhotoId = request.getParameter("id");
        byte[] data = photoService.getRawImageData(targetPhotoId);
        response.setContentType("image/*");  
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content_Length", String.valueOf(data.length));
        try {
            OutputStream outputStream = response.getOutputStream();
            FileOperator.writeByteToStream(data, outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @RequestMapping(value = "/getPhotoDateDescription", method =RequestMethod.POST)
    @ResponseBody
    public String getPhotoDateDescription(@RequestBody Map<String, String> data, HttpServletRequest request) {
        
        String targetPhotoId = "";
        if (data != null && data.containsKey("id")) {
            targetPhotoId = data.get("id");
        }
        Map<String, String> map = photoService.getDateAndDecription(targetPhotoId);
        String json = JSONArray.toJSONString(map);
        return json;
    }
    
    @RequestMapping(value = "/updateDescription", method =RequestMethod.POST)
    @ResponseBody
    public String updateDescription(@RequestBody Map<String, String> data, HttpServletRequest request) {
        
        String targetPhotoId = "";
        String newDescription = "";
        if (data != null && data.containsKey("id") && data.containsKey("newtext")) {
            targetPhotoId = data.get("id");
            newDescription = data.get("newtext");
        }
        String strId = photoService.updateDescription(targetPhotoId, newDescription);
        return strId;
    }
    
    @RequestMapping(value = "/deletePhoto", method =RequestMethod.POST)
    @ResponseBody
    public String deletePhoto(HttpServletRequest request, HttpServletResponse response) {
        
        String targetPhotoId = request.getParameter("id");
        String strId = photoService.deletePhoto(targetPhotoId);
        return strId;
    }
    
    
}
