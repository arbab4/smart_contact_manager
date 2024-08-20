package com.scm.smartcontactmanager.forms;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ContactForm {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Email is required")
    @Email(message="Invalid Email Address")
    private String email;
    @NotBlank(message = "Phone Number is required")
    @Pattern(regexp = "^[0-9]{10}$", message="Invalid Phone Number")
    private String phoneNumber;
    @NotBlank(message = "Address is required")
    private String address;
    private String description;
    private boolean favourite;
    private String facebookLink;
    private String linkedinLink;

    //create an annotation that will validate the file.
    //validating size and resolution
    private MultipartFile contactPic;

    private String contactPicLink;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public String getFacebookLink() {
        return facebookLink;
    }

    public void setFacebookLink(String facebookLink) {
        this.facebookLink = facebookLink;
    }

    public String getLinkedinLink() {
        return linkedinLink;
    }

    public void setLinkedinLink(String linkedinLink) {
        this.linkedinLink = linkedinLink;
    }

    public MultipartFile getContactPic() {
        return contactPic;
    }

    public void setContactPic(MultipartFile contactPic) {
        this.contactPic = contactPic;
    }

    public String getContactPicLink() {
        return contactPicLink;
    }

    public void setContactPicLink(String contactPicLink) {
        this.contactPicLink = contactPicLink;
    }

    public ContactForm(@NotBlank(message = "Name is required") String name,
            @NotBlank(message = "Email is required") @Email(message = "Invalid Email Address") String email,
            @NotBlank(message = "Phone Number is required") @Pattern(regexp = "^[0-9]{10}$", message = "Invalid Phone Number") String phoneNumber,
            @NotBlank(message = "Address is required") String address, String description, boolean favourite,
            String facebookLink, String linkedinLink, MultipartFile contactPic, String contactPicLink) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.description = description;
        this.favourite = favourite;
        this.facebookLink = facebookLink;
        this.linkedinLink = linkedinLink;
        this.contactPic = contactPic;
        this.contactPicLink = contactPicLink;
    }

    public ContactForm() {
    }

    
}
