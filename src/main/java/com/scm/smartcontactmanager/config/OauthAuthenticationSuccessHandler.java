package com.scm.smartcontactmanager.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.scm.smartcontactmanager.helper.AppConstants;
import com.scm.smartcontactmanager.model.Provider;
import com.scm.smartcontactmanager.model.User;
import com.scm.smartcontactmanager.repositories.UserRepo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OauthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepo userRepo;
    Logger logger = LoggerFactory.getLogger(OauthAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,Authentication authentication) throws IOException, ServletException {
 
        var oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;

        //This will tell whether the account is logged in by google, github or by none.
        String authorizedClientRegistrationId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();

        // This will extract name,email and profile picture from google and github
        DefaultOAuth2User oAuthuser = (DefaultOAuth2User) authentication.getPrincipal();

        oAuthuser.getAttributes().forEach((key, value) -> {
            logger.info("{}=>{}", key, value);
        });

        // Create user and save in database
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setEnabled(true);
        user.setEmailVerified(true);
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        user.setPassword("dummyPassword");

        if (authorizedClientRegistrationId.equalsIgnoreCase("google")) {

            user.setName(oAuthuser.getAttribute("name").toString());
            user.setEmail(oAuthuser.getAttribute("email").toString());
            user.setProfilePicLink(oAuthuser.getAttribute("picture").toString());
            user.setProvider(Provider.GOOGLE);
            user.setProviderId(oAuthuser.getName());
            user.setAbout("This account is created using google");


        } else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {

            String email = oAuthuser.getAttribute("email") != null ? oAuthuser.getAttribute("email").toString()
                    : oAuthuser.getAttribute("login").toString() + "github.com";
            String picture = oAuthuser.getAttribute("avatar_url").toString();
            String name = oAuthuser.getAttribute("login");
            user.setProvider(Provider.GITHUB);
            user.setEmail(email);
            user.setProfilePicLink(picture);
            user.setName(name);
            user.setProviderId(oAuthuser.getName());
            user.setAbout("This account is created using github");


        }else {
            logger.info("OAuthAuthenticationSuccessHandler: Unknown Provider");
        }

        //check if this user is already saved or we need to save it in database
        User user1 = userRepo.findByEmail(user.getEmail()).orElse(null);
        if (user1 == null) {
            userRepo.save(user);
            logger.info("User saved");
        }else{
            logger.info("User is already saved in the database");
        }
        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }
}
