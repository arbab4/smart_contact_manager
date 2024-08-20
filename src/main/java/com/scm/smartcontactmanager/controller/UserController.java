package com.scm.smartcontactmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.smartcontactmanager.forms.UserForm;
import com.scm.smartcontactmanager.helper.AppConstants;
import com.scm.smartcontactmanager.helper.Helper;
import com.scm.smartcontactmanager.helper.Message;
import com.scm.smartcontactmanager.helper.MessageType;
import com.scm.smartcontactmanager.model.Contact;
import com.scm.smartcontactmanager.model.User;
import com.scm.smartcontactmanager.services.ContactService;
import com.scm.smartcontactmanager.services.ImageService;
import com.scm.smartcontactmanager.services.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


//Protected Urls
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ContactService contactService;
    @Autowired
    private UserService userService;
    @Autowired
    private ImageService imageService;

    //user dashboard
    @GetMapping("/dashboard")
    public String userDashboard(@RequestParam(value="page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue=AppConstants.PAGE_SIZE + "") int size, @RequestParam(value ="sortBy", defaultValue="name") String sortBy, @RequestParam(value = "direction", defaultValue="asc") String direction, Model model, Authentication authentication) {
        String username = Helper.getEmailOfLoggedInUser(authentication);
        
        User user= userService.getUserByEmail(username);

        Page<Contact> pageContacts = contactService.getFavouriteByUser(user, page, size, sortBy, direction);

        long noOfContacts = contactService.getByUser(user, page, size, sortBy, direction).getTotalElements();
        model.addAttribute("totalContacts", noOfContacts);
        model.addAttribute("pageContacts", pageContacts);
        
        return "user/dashboard";
    }
    
    //user profile page
    @GetMapping("/profile")
    public String userProfile() {
        return "user/profile";
    }
    @RequestMapping("/edit-profile")
    public String editProfile(Authentication authentication, Model model){
        String email = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(email);
        UserForm userForm = new UserForm();
        userForm.setName(user.getName());
        userForm.setEmail(user.getEmail());
        userForm.setPassword(user.getPassword());
        userForm.setPhoneNumber(user.getPhoneNumber());
        userForm.setAbout(user.getAbout());
        userForm.setProfilePicLink(user.getProfilePicLink());

        model.addAttribute("userForm", userForm);
        return "user/edit-profile";
    }

    @PostMapping("/edit-profile")
    public String editProfileProcessing(@ModelAttribute UserForm userForm, Authentication authentication,HttpSession session, Model model){
        String email = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(email);

        user.setEmail(userForm.getEmail());
        user.setName(userForm.getName());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setAbout(userForm.getAbout());

        
        if(!userForm.getPassword().isEmpty()){
            user.setPassword(passwordEncoder.encode(userForm.getPassword()));
        }


        if(!userForm.getProfilePic().isEmpty()){
            String fileUrl =  imageService.uploadImage(userForm.getProfilePic());
           user.setProfilePicLink(fileUrl);
        } 
        
        
        
        userService.updateUser(user);
        session.setAttribute("message", new Message("Your profile has been updated", MessageType.green));
        return "user/edit-profile";
    }
}