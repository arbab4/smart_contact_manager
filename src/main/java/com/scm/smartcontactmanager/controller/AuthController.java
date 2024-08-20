package com.scm.smartcontactmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.smartcontactmanager.helper.Helper;
import com.scm.smartcontactmanager.helper.Message;
import com.scm.smartcontactmanager.helper.MessageType;
import com.scm.smartcontactmanager.model.User;
import com.scm.smartcontactmanager.repositories.UserRepo;
import com.scm.smartcontactmanager.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserService userService;

    //EMAIL VERIFICATION CODE
    @GetMapping("/verify-email")
    public String  verifyEmail(@RequestParam("token") String token, HttpSession session){

       User user = userRepo.findByEmailToken(token).orElse(null);
            
       if(user!=null && user.getEmailToken().equals(token)){
            user.setEmailVerified(true);
            user.setEnabled(true);
            userRepo.save(user);
            session.setAttribute("message", new Message("Email has been verified. Your account is now enabled", MessageType.green));
            return "success_page"; 
       }

       session.setAttribute("message", new Message("Something went wrong. Click on button below to resend the email verification link", MessageType.red));
       return "error_page";
    }

    //Forgot-Password Code
    @RequestMapping("/create-password")
    public String createPassword(@RequestParam("email") String email, HttpSession session, Model model){
        model.addAttribute("email", email);
        return "reset-password";
    }
    @PostMapping("/create-password")
    public String createPasswordProcessing(@RequestParam("password") String password, @RequestParam("email")String email, HttpSession session){
        
        User user = userService.getUserByEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepo.save(user);
        
        session.setAttribute("message", new Message("Your password has been changed. Enter new Password to login", MessageType.green));
       
        return "redirect:/login";
    }
}
