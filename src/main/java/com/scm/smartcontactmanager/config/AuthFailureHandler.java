package com.scm.smartcontactmanager.config;

import java.io.IOException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import com.scm.smartcontactmanager.helper.Message;
import com.scm.smartcontactmanager.helper.MessageType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        
        if (exception instanceof DisabledException == false) {
            //if error is not of user disabled - that means it is invalid username and password
            response.sendRedirect("/login?error=true");
        } else {
            // user is disabled
            request.getSession().setAttribute("message",new Message("This account is disabled. Please verify the email", MessageType.red));
            
            response.sendRedirect("/login?disabled=true");
        }
    }
}
