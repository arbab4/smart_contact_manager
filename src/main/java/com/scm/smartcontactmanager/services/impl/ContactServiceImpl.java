package com.scm.smartcontactmanager.services.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import com.scm.smartcontactmanager.helper.ResourceNotFoundException;
import com.scm.smartcontactmanager.model.Contact;
import com.scm.smartcontactmanager.model.User;
import com.scm.smartcontactmanager.repositories.ContactRepo;
import com.scm.smartcontactmanager.services.ContactService;
import jakarta.transaction.Transactional;

@Component
public class ContactServiceImpl implements ContactService {

    @Autowired
     private ContactRepo contactRepo;

    @Override
    public List<Contact> getAll() {
        return contactRepo.findAll();
    }

    @Override
    public Contact getById(String id) {
        return contactRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Contact not found with given Id"));
    }

    @Override
    public List<Contact> getByUserId(String userId) {
       return contactRepo.findbyUserId(userId);
    }

    @Override
    public Contact saveContact(Contact contact) {
        String contactId = UUID.randomUUID().toString();
        contact.setId(contactId);
        return contactRepo.save(contact); 
    }

    @Override
    public Contact update(Contact contact) {
        var contactOld  = contactRepo.findById(contact.getId()).orElseThrow(()-> new ResourceNotFoundException("Contact not found"));
        contactOld.setName(contact.getName());
        contactOld.setEmail(contact.getEmail());
        contactOld.setPhoneNumber(contact.getPhoneNumber());
        contactOld.setAddress(contact.getAddress());
        contactOld.setDescription(contact.getDescription());
        contactOld.setContactPicLink(contact.getContactPicLink());
        contactOld.setFavourite(contact.isFavourite());
        contactOld.setLinkedinLink(contact.getLinkedinLink());
        contactOld.setFacebookLink(contact.getFacebookLink());
        
        return contactRepo.save(contactOld);
    }

    @Override
    public Page<Contact> getByUser(User user,int page, int size, String sortBy, String direction) {      
        Sort sort = direction.equals("desc")? Sort.by(sortBy).descending(): Sort.by(sortBy);
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUser(user, pageable);
    }

    @Override
    public Page<Contact> searchByEmail(String keyword, int size, int page, String sortBy, String order, User user) {
        Sort sort = order.equals("desc")?Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page,size,sort);
        return contactRepo.findByUserAndEmailContaining(user, keyword, pageable);
    }

    @Override
    public Page<Contact> searchByName(String keyword, int size, int page, String sortBy, String order, User user) {
        Sort sort = order.equals("desc")?Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page,size,sort);
        return contactRepo.findByUserAndNameContaining(user,keyword, pageable);
    }

    @Override
    public Page<Contact> searchByPhoneNumber(String keyword,int size, int page,  String sortBy, String order, User user) {
        Sort sort = order.equals("desc")?Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page,size,sort);
        return contactRepo.findByUserAndPhoneNumberContaining(user,keyword, pageable);
    }

    @Override
    @Transactional
    public void deleteByCid(String id) {
        contactRepo.deleteByContactId(id);
    }

    @Override
    public Page<Contact> getFavouriteByUser(User user, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equals("desc")? Sort.by(sortBy).descending(): Sort.by(sortBy);
        var pageable = PageRequest.of(page, size, sort);

        return contactRepo.findByUserAndFavourite(user,true, pageable);
    }

    @Override
    public Page<Contact> getFavouriteFromContacts(List<Contact> contacts) {
        List<Contact> favContacts = new LinkedList<>();
        for (Contact contact : contacts) {
            if(contact.isFavourite()){
                favContacts.add(contact);
            }
        }
        Page<Contact> pageContacts = new PageImpl<>(favContacts);
        return pageContacts;
    }

   
}
