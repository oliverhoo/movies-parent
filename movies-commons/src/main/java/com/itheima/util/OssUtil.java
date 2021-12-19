package com.itheima.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

//阿里存储工具类
public class OssUtil {

    //==========================下面五项配置修改成自己的========================//
    //区域
    private static String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    //访问id
    private static String accessKeyId = "LTAIMOat5S7QCTgy";
    //访问秘钥
    private static String accessKeySecret = "NrH778cgJlSUYbe73R5QTOvP5NQCpT";
    //桶名称
    private static String bucketName = "tanhua-hzb";
    //访问URL
    private static String url = "https://tanhua-hzb.oss-cn-beijing.aliyuncs.com";


    //文件上传
    public static String upload(String fileName, InputStream inputStream) {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件流。
        // <yourObjectName>表示上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如 images/2020/11/11/asdf.jpg。
        String objectName = "images/" + new SimpleDateFormat("yyyy/MM/dd").format(new Date())
                + "/" + System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."));

        // meta设置请求头,解决访问图片地址直接下载
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentType(getContentType(fileName.substring(fileName.lastIndexOf("."))));
        ossClient.putObject(bucketName, objectName, inputStream, meta);

        // 关闭OSSClient。
        ossClient.shutdown();

        return url + "/" + objectName;
    }

    public static String getContentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpg";
        }
        return "image/jpg";
    }
}