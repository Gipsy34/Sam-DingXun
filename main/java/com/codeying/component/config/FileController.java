package com.codeying.component.config;

import com.codeying.App;
import com.codeying.utils.component.*;
import com.codeying.utils.*;
import com.codeying.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletOutputStream;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
@RequestMapping("/file")
public class FileController extends BaseController {

    /**
     * 文件上传
     */
    @PostMapping("/upload")
    @ResponseBody
    public ApiResult upload(@RequestParam("file") MultipartFile file){
        String fileid = CommonUtils.upload(file);
        return ApiResult.successData(fileid);
    }

    /**
     * 根据文件名下载文件
     *
     * @param fileid 文件名
     */
    @GetMapping("/downloadById")
    public void downloadById(String fileid) throws IOException {
        // 文件编码 处理文件名中的 '+'、' ' 特殊字符
        String encoderName = URLEncoder.encode(fileid, "UTF-8")
                .replace("+", "%20")
                .replace("%2B", "+");
        resp.addHeader("Content-Disposition", "attachment; filename=" + fileid);
        resp.setContentType("multipart/form-data" + ";charset=UTF-8");
        resp.addHeader("Accept-Ranges", "bytes");
        String path = App.fileUploadPath + fileid;
        System.out.println("文件被申请下载"+path);
        File file = new File(path);
        InputStream fileInputStream;
        if(!file.exists() || file.isDirectory()){
            fileInputStream =getClass().getClassLoader().getResourceAsStream("assets/notfund.png");
        }else{
            fileInputStream = Files.newInputStream(Paths.get(path));
        }
        int len = 0;
        byte[] bytes = new byte[1024];
        ServletOutputStream servletOutputStream = resp.getOutputStream();
        while ((len = fileInputStream.read(bytes)) > 0) {
            servletOutputStream.write(bytes, 0, len);
        }
        //关闭资源
        servletOutputStream.close();
        fileInputStream.close();
    }

}
