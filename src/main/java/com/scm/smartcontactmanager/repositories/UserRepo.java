package com.scm.smartcontactmanager.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.scm.smartcontactmanager.model.User;


public interface UserRepo extends JpaRepository<User, String> {
    Optional<User>  findByEmail(String email);
    Optional<User> findByEmailAndPassword(String email, String password);
    Optional<User> findByEmailToken(String emailToken);
}