package com.scm.smartcontactmanager.services.impl;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scm.smartcontactmanager.helper.AppConstants;
import com.scm.smartcontactmanager.services.ImageService;
@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private Cloudinary cloudinary;
    

    @Override
    public String uploadImage(MultipartFile pic) {
        String filename = UUID.randomUUID().toString();
        System.out.println("22222");
        try {
            byte[] data = new byte[pic.getInputStream().available()];
            pic.getInputStream().read(data);
            cloudinary.uploader().upload(data, ObjectUtils.asMap(
                    "public_id", filename));
                    System.out.println("3333333");
            return this.getUrlFromPublicId(filename);
        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public String getUrlFromPublicId(String publicId) {
        System.out.println(publicId);
        return cloudinary.url().transformation(new Transformation<>().width(AppConstants.IMAGE_WIDTH).height(AppConstants.IMAGE_HEIGHT).crop(AppConstants.IMAGE_CROP)).generate(publicId);
    }
}
