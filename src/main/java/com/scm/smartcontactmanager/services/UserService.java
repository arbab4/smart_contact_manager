package com.scm.smartcontactmanager.services;

import java.util.Optional;
import com.scm.smartcontactmanager.model.User;

public interface UserService {
    User saveUser(User user);
    Optional<User> getUserById(String id);
    Optional<User> updateUser(User user);
    void deleteUser(User user);
    boolean isUserExists(String userId);
    boolean isUserExistsByEmail(String email);
    User getUserByEmail(String email);
    
}
