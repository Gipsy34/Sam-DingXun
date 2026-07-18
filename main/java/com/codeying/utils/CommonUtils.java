package com.codeying.utils;

import com.codeying.App;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 常用工具类
 */
public class CommonUtils {

    public static final String fileUploadPath = App.fileUploadPath;

    static {
        //创建上传文件的文件夹
        System.out.println(fileUploadPath);
        File file = new File(fileUploadPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 上传文件到本地
     *
     * @param myfileFile
     * @return
     */
    public static String upload(MultipartFile myfileFile) {
        String fileName = handleFileName(myfileFile.getOriginalFilename());
        //处理文件名
        try {
            FileUtils.writeByteArrayToFile(new File(fileUploadPath + fileName), myfileFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    private static int n = 10000000;

    /**
     * 获取随机id，根据时间和五位随机数
     *
     * @return
     */
    public static String getRandomIdByTime() {
        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String str = simpleDateFormat.format(date);
        n++;
        if (n > (1 << 26)) {
            n = 10000000;
        }
        return str + n;
    }

    //获取Id
    public static String newId() {
        return getRandomIdByTime();
    }

    /**
     * 替换文件名的非法字符
     *
     * @param fileName
     * @return
     */
    public static String handleFileName(String fileName) {
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        fileName = fileName.substring(0, fileName.lastIndexOf("."));
        String illegalChar = "!@#%&-=;':\"`~,/<>";
        for (int i = 0; i < illegalChar.length(); i++) {
            fileName = fileName.replaceAll(String.valueOf(illegalChar.charAt(i)), "");
        }
        fileName = fileName.replaceAll("\\(", "");
        fileName = fileName.replaceAll("\\$", "");
        fileName = fileName.replaceAll("\\)", "");
        fileName = fileName.replaceAll("\\.", "");
        fileName = fileName.replaceAll("\\*", "");
        fileName = fileName.replaceAll("\\^", "");
        fileName = fileName.replaceAll("\\+", "");
        fileName = fileName.replaceAll("\\{", "");
        fileName = fileName.replaceAll("\\}", "");
        fileName = fileName.replaceAll("\\[", "");
        fileName = fileName.replaceAll("\\]", "");
        fileName = fileName.replaceAll("\\?", "");
        if (fileName.equals("")) {
            fileName = "picture";
        }
        return fileName + fileType;
    }

}

