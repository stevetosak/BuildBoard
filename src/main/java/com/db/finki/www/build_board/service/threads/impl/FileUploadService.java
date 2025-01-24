package com.db.finki.www.build_board.service.threads.impl;


import jakarta.annotation.PostConstruct;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileUploadService {

    public static String USER_AVATAR_DIR = System.getProperty("user.dir") + File.separator + "/uploads/user-avatars";
    public static long MAX_FILE_SIZE = 5 * 1024 * 1024;

    public void uploadAvatar(MultipartFile file, long userId) throws IOException {
        System.out.println(USER_AVATAR_DIR);

        if(file.isEmpty()){
            throw new FileUploadException("File is empty");
        }

        String contentType = file.getContentType();
        if (!contentType.startsWith("image/")) {
            throw new IOException("Only image files are allowed.");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IOException("File size exceeds the limit.");
        }


        String fileName = "avatar-" + userId;
        File saveFile = new File(USER_AVATAR_DIR + File.separator + fileName);

        if (saveFile.exists()) {
            saveFile.delete();
        }

        file.transferTo(saveFile);
    }

    @PostConstruct
    public void initDirectories(){
        File directory = new File(USER_AVATAR_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

}
