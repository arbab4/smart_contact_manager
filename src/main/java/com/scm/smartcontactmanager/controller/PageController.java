package com.scm.smartcontactmanager.controller;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.scm.smartcontactmanager.forms.UserForm;
import com.scm.smartcontactmanager.helper.Helper;
import com.scm.smartcontactmanager.helper.Message;
import com.scm.smartcontactmanager.helper.MessageType;
import com.scm.smartcontactmanager.model.User;
import com.scm.smartcontactmanager.services.EmailService;
import com.scm.smartcontactmanager.services.ImageService;
import com.scm.smartcontactmanager.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageController {

    @Autowired
    private ImageService imageService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/about")
    public String about(Model m) {
        return "about";
    }

    @GetMapping("/service")
    public String service(Model m) {
        return "service";
    }

    @GetMapping("/contact-us")
    public String contactUs() {
        return "contactUs";
    }
  
    @GetMapping("/register")
    public String register(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Processing the register
    @PostMapping("/do-register")
    public String processingRegister(@Valid @ModelAttribute UserForm userForm, BindingResult bindingResult) {
       
        if (bindingResult.hasErrors()) {
            return "register";
        }
        //processing the image
        String fileUrl = "";
        if(userForm.getProfilePic().isEmpty()){
            fileUrl = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fsinpo.id%2Fstorage%2Fgambar%2Ffoto%2Fwartawan%2Fdefault_photo.jpg&f=1&nofb=1&ipt=12bccf2d8d91e30ade196ae7965f9752d013828590d956615f1a1247b0040946&ipo=images";
        }else{
            fileUrl = imageService.uploadImage(userForm.getProfilePic());
        }

        User user = new User();
        user.setName(userForm.getName());
        user.setAbout(userForm.getAbout());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setEnabled(false);
        String emailToken = UUID.randomUUID().toString();
        user.setEmailToken(emailToken);

        user.setProfilePicLink(fileUrl);

        User saveUser = userService.saveUser(user);
        System.out.println(saveUser);

        String email = user.getEmail();
        return "redirect:/verify/"+email+"/"+emailToken;
    }

    @GetMapping("/verify/{email}/{emailToken}")
    public String sendEmail(@PathVariable("email") String email, @PathVariable("emailToken") String emailToken,  HttpSession session){

        // email verification link sent message
        Message msg = new Message("Please verify your email by the link we have sent to your email", MessageType.green);
        session.setAttribute("message", msg);

        String emailLink = Helper.getLinkForEmailVerification(emailToken);
        User user = userService.getUserByEmail(email);
        emailService.sendEmail(user.getEmail(), "Email Verification for Smart Contact Manager", "Dear "+user.getName()+", you can verify your email by clicking the link:  "+emailLink);
        return "redirect:/login";
    }

    @GetMapping("/send-verification-link")
    public String sendVerificationLink(){
        return "sendverificationlink";
    }
    @PostMapping("/processing-send-verification-link")
    public String processingVerification(@RequestParam("email") String email){
        String emailToken = userService.getUserByEmail(email).getEmailToken();
        return "redirect:/verify/"+email+"/"+emailToken;
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(){
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPasswordProcessing(@RequestParam("email") String email, HttpSession session){
        // Password reset link sent message
        Message msg = new Message("Password reset link has been sent to your email.", MessageType.green);
        session.setAttribute("message", msg);
        User user = userService.getUserByEmail(email);

        //link to send
        String link = Helper.getLinkForForgotPassword(email);
        emailService.sendPasswordResetEmail(email, "Reset Password for Smart Contact Manager", "Dear "+user.getName()+", you can reset your password by clicking the link:  "+link);
        return "forgot-password";
    }
}