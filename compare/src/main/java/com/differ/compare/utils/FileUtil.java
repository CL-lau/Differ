package com.differ.compare.utils;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/7 14:53
 */

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileUtil {

    public static void uploadFile(MultipartFile file, String destinationPath) throws IOException {
        File destination = new File(destinationPath);
        file.transferTo(destination);
    }

    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

    public static byte[] readFileBytes(String filePath) throws IOException {
        return FileUtils.readFileToByteArray(new File(filePath));
    }

    public static void copyFile(String sourcePath, String destinationPath) throws IOException {
        FileUtils.copyFile(new File(sourcePath), new File(destinationPath));
    }

    public static void moveFile(String sourcePath, String destinationPath) throws IOException {
        FileUtils.moveFile(new File(sourcePath), new File(destinationPath));
    }
}

