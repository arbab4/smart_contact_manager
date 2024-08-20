package com.scm.smartcontactmanager.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String uploadImage(MultipartFile contactPic);

    String getUrlFromPublicId(String publicId);
}
