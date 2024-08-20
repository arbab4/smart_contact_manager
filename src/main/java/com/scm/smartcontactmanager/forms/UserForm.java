package com.scm.smartcontactmanager.forms;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserForm {
    @NotBlank(message = "Username is required")
    @Size(min = 3, message = "Name must be of atleast 3 characters")
    private String name;
    @Email(message =  "Invalid email address")
    @NotBlank(message = "Email is required")
    private String email;
    @Size(min = 6, message = "Password must be of atleast 6 characters")
    private String password;
    @Size(min = 8, max = 12, message = "Invalid phone number")
    private String phoneNumber;
    @NotBlank(message = "About is required")
    private String about;

    private String profilePicLink;

    private MultipartFile profilePic;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getProfilePicLink() {
        return profilePicLink;
    }

    public void setProfilePicLink(String profilePicLink) {
        this.profilePicLink = profilePicLink;
    }

    public MultipartFile getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(MultipartFile profilePic) {
        this.profilePic = profilePic;
    }

    public UserForm(
            @NotBlank(message = "Username is required") @Size(min = 3, message = "Name must be of atleast 3 characters") String name,
            @Email(message = "Invalid email address") @NotBlank(message = "Email is required") String email,
            @Size(min = 6, message = "Password must be of atleast 6 characters") String password,
            @Size(min = 8, max = 12, message = "Invalid phone number") String phoneNumber,
            @NotBlank(message = "About is required") String about, String profilePicLink, MultipartFile profilePic) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.about = about;
        this.profilePicLink = profilePicLink;
        this.profilePic = profilePic;
    }

    public UserForm() {
    }
    
}
