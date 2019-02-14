package com.kingyon.chengxin.product.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

@Slf4j
public class HttpDownloader {


    public static String downloadPDF(String remoteFileUrl, String localFilePath) {
        try {
            URL url = new URL(remoteFileUrl);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5 * 1000); // 5000 毫秒内没有连接上 则放弃连接
            httpURLConnection.connect(); // 连接
            log.info("连接 URL 成功~");

            int fileLenght = httpURLConnection.getContentLength();
            log.info("文件大小：" + (fileLenght / 1024.0) + " KB");

            log.info("开始下载...");
            try (DataInputStream dis = new DataInputStream(httpURLConnection.getInputStream());
                 FileOutputStream fos = new FileOutputStream(localFilePath)) {
                byte[] buf = new byte[10240]; // 根据实际情况可以 增大 buf 大小
                for (int readSize; (readSize = dis.read(buf)) > 0; ) {
                    fos.write(buf, 0, readSize);
                }
                log.info("下载完毕~");
                httpURLConnection.disconnect();
                return localFilePath;
            } catch (IOException ex) {
                return "下载时出错";
            }
        } catch (IOException ex) {
            return "URL 不存在或者连接超时";
        }
    }
}