package com.scm.smartcontactmanager.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.scm.smartcontactmanager.helper.AppConstants;
import com.scm.smartcontactmanager.helper.ResourceNotFoundException;
import com.scm.smartcontactmanager.model.User;
import com.scm.smartcontactmanager.repositories.UserRepo;
import com.scm.smartcontactmanager.services.EmailService;
import com.scm.smartcontactmanager.services.UserService;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    public EmailService emailService;
   
    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        String userId = UUID.randomUUID().toString();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        user.setId(userId);
        User savedUser = userRepo.save(user);
        return savedUser;
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
        User user2 = userRepo.findById(user.getId()).orElseThrow(() -> new ResourceNotFoundException());
        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setAbout(user.getAbout());
        user2.setProfilePicLink(user.getProfilePicLink());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setEnabled(user.isEnabled());
        user2.setEmailVerified(user.isEmailVerified());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setProvider(user.getProvider());
        user2.setProviderId(user2.getProviderId());
       
        User save = userRepo.save(user2);
        return Optional.ofNullable(save);
    }

    public void deleteUser(User user) {
        User u = userRepo.findById(user.getId()).orElseThrow(() -> new ResourceNotFoundException());
        userRepo.delete(u);
    }

    public boolean isUserExists(String userId) {
        User user = userRepo.findById(userId).orElse(null);
        if (user == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean isUserExistsByEmail(String email) {
        User user = userRepo.findByEmail(email).orElse(null);
        if (user == null) {
            return false;
        } else {
            return true;
        }
    }
}