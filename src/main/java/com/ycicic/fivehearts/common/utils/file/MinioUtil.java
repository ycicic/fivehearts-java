package com.ycicic.fivehearts.common.utils.file;

import java.io.IOException;
import java.io.InputStream;

import com.ycicic.fivehearts.common.utils.ServletUtils;
import com.ycicic.fivehearts.common.utils.spring.SpringUtils;
import org.springframework.web.multipart.MultipartFile;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;

/**
 * Minio 文件存储工具类
 *
 * @author ruoyi
 */
public class MinioUtil {
    /**
     * 上传文件
     *
     * @param bucketName 桶名称
     * @param fileName
     * @throws IOException
     */
    public static String uploadFile(String bucketName, String fileName, MultipartFile multipartFile) throws IOException {
        String url = "";
        MinioClient minioClient = SpringUtils.getBean(MinioClient.class);
        try (InputStream inputStream = multipartFile.getInputStream()) {
            PutObjectArgs build = PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(inputStream, multipartFile.getSize(), -1)
                    .contentType(multipartFile.getContentType())
                    .build();
            minioClient.putObject(build);
            url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket(bucketName).object(fileName).method(Method.GET).build());
            url = url.substring(0, url.indexOf('?'));
            return ServletUtils.urlDecode(url);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }
}
