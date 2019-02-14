package com.kingyon.chengxin.product.web.controller;

import com.kingyon.chengxin.framework.Response;
import com.kingyon.chengxin.framework.exception.WebException;
import com.kingyon.chengxin.product.ProductErrorCode;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


@Controller
@RequestMapping(value = "/boss/upload")
public class FileuploadController {


    @Value("${apps.common.attachment.path}")
    private String path;
    @Value("${apps.common.attachment.url}")
    private String url;

    @ApiOperation(value = "上传单个临时文件", httpMethod = "POST")
    @RequestMapping(value = {"/single"}, method = RequestMethod.POST)
    @ResponseBody
    public Response upload(HttpServletRequest req, @RequestParam(required = false, value = "file") MultipartFile files) throws IOException {
        Response response = new Response();
        // 设置允许上传的图片类型
        final String allowFileSuffix = "jpg,jpeg,png,gif,JPG,JPEG,PNG,GIF,pdf,PDF";
        // 获取上传文件的原始名称
         String oldFileName = files.getOriginalFilename();
        // 文件扩展名
        String extName = oldFileName.substring(oldFileName.lastIndexOf(".") + 1);

        if (allowFileSuffix.indexOf(extName) == -1) {
            throw new WebException(ProductErrorCode.FAIL_UPLOAD);
        }
        String str = UUID.randomUUID().toString().replace("-", "");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy"+File.separator+"MM"+File.separator+"dd");
        String fileName = str +"."+extName;
        String datePath = File.separator +sdf.format(new Date());
        String savePath = path+ datePath;
        File targetFile1 = new File(savePath, fileName);
        if (!targetFile1.getParentFile().exists()) {
            targetFile1.getParentFile().mkdirs();
        }
        try {
            files.transferTo(targetFile1);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        response.message=(datePath +File.separator+ fileName);
        response.data = (url+datePath +File.separator+ fileName);
        return response;
    }






}
