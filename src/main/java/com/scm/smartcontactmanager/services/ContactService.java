package com.scm.smartcontactmanager.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.scm.smartcontactmanager.model.Contact;
import com.scm.smartcontactmanager.model.User;

public interface ContactService {

    Contact update(Contact contact);
    Contact saveContact(Contact contact);
    List <Contact> getAll();
    void deleteByCid(String id);
    Contact getById(String id);

    Page<Contact> searchByName(String keyword, int size, int page, String sortBy, String order, User user);
    Page<Contact> searchByEmail(String keyword, int size, int page, String sortBy, String order, User user);
    Page<Contact> searchByPhoneNumber(String keyword, int size, int page, String sortBy, String order, User user);
    
    List<Contact> getByUserId(String userId);

    Page<Contact> getByUser(User user, int page, int size, String sortBy, String direction);
    Page<Contact> getFavouriteByUser(User user, int page, int size, String sortBy, String direction);
    Page<Contact> getFavouriteFromContacts(List<Contact> contacts);

}
