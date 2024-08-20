package com.scm.smartcontactmanager.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scm.smartcontactmanager.model.Contact;
import com.scm.smartcontactmanager.model.User;

import java.util.List;

@Repository
public interface ContactRepo extends JpaRepository<Contact, String> {
    //find the contact by user
    Page<Contact> findByUser(User user, Pageable pageable);

    @Query("SELECT c FROM Contact c WHERE c.user.id = :userId")
    List<Contact>findbyUserId(@Param("userId") String userId);
    Page<Contact> findByUserAndNameContaining(User user, String keyword, Pageable pageable);
    Page<Contact> findByUserAndEmailContaining(User user, String keyword, Pageable pageable);
    Page<Contact> findByUserAndPhoneNumberContaining(User user, String keyword, Pageable pageable);
    Page<Contact> findByUserAndFavourite(User user, boolean isFavourite, Pageable pageable);
    
    @Query("DELETE FROM Contact c WHERE c.id = :contactId")
    @Modifying
    void deleteByContactId(@Param("contactId") String contactId);
    
}
