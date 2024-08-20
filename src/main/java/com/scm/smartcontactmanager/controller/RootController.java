package com.scm.smartcontactmanager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.smartcontactmanager.helper.Helper;
import com.scm.smartcontactmanager.model.User;
import com.scm.smartcontactmanager.services.UserService;

@ControllerAdvice
public class RootController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;
    @ModelAttribute
    public void addLoggedInUserInformation(Model model, Authentication authentication){
        if(authentication==null){
            return;
        }
        String username = Helper.getEmailOfLoggedInUser(authentication);
        logger.info("User logged in: {}", username); 

        User user = userService.getUserByEmail(username);
        model.addAttribute("user", user);
    }
}
