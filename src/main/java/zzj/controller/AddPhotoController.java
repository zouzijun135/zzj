package zzj.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;

import zzj.service.IPhotoService;
import zzj.common.FileOperator;

@Controller
public class AddPhotoController {

    @Resource
    private IPhotoService photoService;
    
    @RequestMapping(value = "/uploadImage", method =RequestMethod.POST)
    public void uploadImage(@RequestParam("file")MultipartFile file, 
                            HttpServletRequest request, 
                            HttpServletResponse response) {
        byte[] res = null;
        /* 服务器本地servlet地址 */
        String servletPath = request.getSession().getServletContext().getRealPath("");
        try {
            res = this.photoService.saveTemporaryImg(file, servletPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        response.setContentType("image/png");  
        response.setCharacterEncoding("utf-8");
        try {
            OutputStream outputStream = response.getOutputStream();
            FileOperator.writeByteToStream(res, outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @RequestMapping(value = "/confirmUploadImage", method =RequestMethod.POST)
    @ResponseBody
    public String confirmUploadImage(@RequestBody Map<String, String> data, HttpServletRequest request) {
        
        String fileName = "";
        String description = "";
        /* 服务器本地servlet地址 */
        String servletPath = request.getSession().getServletContext().getRealPath("");
        if (data != null && data.containsKey("fileName") && data.containsKey("description")) {
            fileName = data.get("fileName");
            description = data.get("description");
        }
        Map<String, String> map = new LinkedHashMap<String, String>();
        String id = photoService.saveImgToDB(servletPath, fileName, description);
        map.put("id", id);
        String json = JSONArray.toJSONString(map);
        return json;
    }
}
