package com.hsnay.crowd.util;

import com.aliyun.api.gateway.demo.util.HttpUtils;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.common.utils.HttpUtil;
import com.aliyun.oss.model.PutObjectResult;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CrowdUtil {
    /**
     * 字符串md5加密
     *
     * @param source 传入的明文字符串
     * @return 加密结果
     */
    public static String md5(String source) {
        if (source == null || source.length() == 0) {
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }
        String algorithm = "md5";
        try {

            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            byte[] input = source.getBytes();
            byte[] output = messageDigest.digest(input);
            int signum = 1;
            int radix = 16;
            BigInteger bigInteger = new BigInteger(signum, output);
            String encoded = bigInteger.toString(radix).toUpperCase();
            return encoded;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 判断当前请求是不是Ajax请求
     *
     * @param request
     * @return
     */
    //Accept: application/json  X-Requested-With: XMLHttpRequest
    public static boolean judgeRequestType(HttpServletRequest request) {
        String accept = request.getHeader("Accept");
        String requested = request.getHeader("X-Requested-With");
        return (accept != null && accept.contains("application/json"))
                ||
                (requested != null && requested.equals("XMLHttpRequest"));
    }


    /**
     * @param host     请求的地址
     * @param path     请求的后缀
     * @param appCode  购入的api的appCode
     * @param phoneNum 发送验证码的目的号码
     * @return 发送成功则返回发送的验证码，放在ResultEntity中，失败则返回失败的ResultEntity
     */
    public static ResultEntity<String> sendShortMessage(
            String host, String path, String method,
            String phoneNum, String appCode) {
        //生成验证码
        StringBuffer buffer = new StringBuffer(6);
        for (int i = 0; i < 6; i++) {
            int random = (int) (Math.random() * 10);
            buffer.append(random);
        }

        String code = buffer.toString();
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appCode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("receive", phoneNum);
        querys.put("tag", code);
        querys.put("templateId", "M09DD535F4");
        Map<String, String> bodys = new HashMap<String, String>();


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            HttpEntity entity = response.getEntity();
            System.out.println(EntityUtils.toString(entity));
            return ResultEntity.successWithData(code);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }


    }
    public static ResultEntity<String> uploadFileToOSS(
            String endPoint,
            String accessKeyId,
            String accessKeySecret,
            InputStream inputStream,
            String bucketName,
            String bucketDomain,
            String originalName ){

        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endPoint,accessKeyId,accessKeySecret);

        // 生成上传文件的目录，按照日期来划分目录
        String folderName = new SimpleDateFormat("yyyyMMdd").format(new Date());

        // 生成上传文件在OSS服务器上保存的文件名,通过uuid生成随机uuid，将其中的“-”删去（替换成空字符串）
        String fileMainName = UUID.randomUUID().toString().replace("-", "");

        System.out.println(originalName);
        // 从原始文件名中获取文件扩展名
        String extensionName = originalName.substring(originalName.lastIndexOf("."));

        // 使用目录、文件主体名称、文件扩展名拼接得到对象名称
        String objectName = folderName + "/" + fileMainName + extensionName;


        try {
            // 调用OSS客户端对象的方法上传文件并获取响应结果数据
            PutObjectResult putObjectResult = ossClient.putObject(bucketName,objectName,inputStream);

            // 从响应结果中获取具体的响应消息
            ResponseMessage responseMessage = putObjectResult.getResponse();

            // 根据响应状态判断是否成功
            if (responseMessage == null) {
                // 拼接访问刚刚上传的文件的路径
                String ossFileAccessPath = bucketDomain + "/" + objectName;

                // 返回成功，并带上访问路径
                return ResultEntity.successWithData(ossFileAccessPath);
            }else {
                // 获取响应状态码
                int statusCode = responseMessage.getStatusCode();
                // 没有成功 获取错误消息
                String errorMessage = responseMessage.getErrorResponseAsString();

                return ResultEntity.failed("当前响应状态码=" + statusCode + " 错误消息=" + errorMessage);
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        } finally {
            // 关闭OSSClient
            ossClient.shutdown();
        }

    }


}