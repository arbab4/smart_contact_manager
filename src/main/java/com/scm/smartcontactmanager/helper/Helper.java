package com.scm.smartcontactmanager.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {
    public static String getEmailOfLoggedInUser(Authentication authentication) {

        if (authentication instanceof OAuth2AuthenticationToken) {
            var oAuth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;
            var clientId =  oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
            var oauth2User = (OAuth2User)authentication.getPrincipal();

            String username = "";
            if(clientId.equalsIgnoreCase("google")){
            // if logged in with google
           username =  oauth2User.getAttribute("email").toString();
            
            }else if(clientId.equalsIgnoreCase("github")){
            // if logged in with github
            username = oauth2User.getAttribute("email") != null ? oauth2User.getAttribute("email").toString()
            : oauth2User.getAttribute("login").toString() + "github.com";
            }
            return username;
        }else{
            return authentication.getName();
        }
        
    }

    public static String getLinkForEmailVerification(String emailToken){
        String link = "http://localhost:8080/auth/verify-email?token=" +emailToken;
        return link;
    }
    public static String getLinkForForgotPassword(String email){
        String link = "http://localhost:8080/auth/create-password?email=" +email;
        return link;
    }
}