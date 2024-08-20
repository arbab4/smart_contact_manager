package com.scm.smartcontactmanager.controller;

import com.scm.smartcontactmanager.model.Contact;
import com.scm.smartcontactmanager.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//This is for viewing the particular contact
@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ContactService contactService;

    //Get contact
    @GetMapping("contacts/{contactId}")
    public Contact getContact(@PathVariable String contactId){
        return contactService.getById(contactId);
    }
}
