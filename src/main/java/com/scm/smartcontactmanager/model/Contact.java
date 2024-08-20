package com.scm.smartcontactmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "contacts")
public class Contact {
    @Id
    private String id;
    @Column(nullable = false)
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String contactPicLink;
    @Column(length = 1000)
    private String description;
    public boolean favourite = false;
    @ManyToOne
    @JsonIgnore
    private User user;
    
    private String linkedinLink;
    private String facebookLink;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getContactPicLink() {
        return contactPicLink;
    }
    public void setContactPicLink(String contactPicLink) {
        this.contactPicLink = contactPicLink;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public boolean isFavourite() {
        return favourite;
    }
    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getLinkedinLink() {
        return linkedinLink;
    }
    public void setLinkedinLink(String linkedinLink) {
        this.linkedinLink = linkedinLink;
    }
    public String getFacebookLink() {
        return facebookLink;
    }
    public void setFacebookLink(String facebookLink) {
        this.facebookLink = facebookLink;
    }
    public Contact(String id, String name, String email, String phoneNumber, String address, String contactPicLink,
            String description, boolean favourite, User user, String linkedinLink, String facebookLink) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.contactPicLink = contactPicLink;
        this.description = description;
        this.favourite = favourite;
        this.user = user;
        this.linkedinLink = linkedinLink;
        this.facebookLink = facebookLink;
    }
    public Contact() {
    }


}