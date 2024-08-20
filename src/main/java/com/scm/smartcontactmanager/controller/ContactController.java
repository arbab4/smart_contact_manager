package com.scm.smartcontactmanager.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.scm.smartcontactmanager.forms.ContactForm;
import com.scm.smartcontactmanager.helper.AppConstants;
import com.scm.smartcontactmanager.helper.Helper;
import com.scm.smartcontactmanager.helper.Message;
import com.scm.smartcontactmanager.helper.MessageType;
import com.scm.smartcontactmanager.model.Contact;
import com.scm.smartcontactmanager.model.User;
import com.scm.smartcontactmanager.repositories.ContactRepo;
import com.scm.smartcontactmanager.services.ContactService;
import com.scm.smartcontactmanager.services.ImageService;
import com.scm.smartcontactmanager.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ImageService imageService;
    @Autowired
    private ContactService contactService;
    @Autowired
    private ContactRepo contactRepo;
    @Autowired
    private UserService userService;

    //add contact page handler
    @GetMapping("/add")
    public String addContactView(Model model){
        ContactForm contactForm = new ContactForm();
        model.addAttribute("contactForm", contactForm);

        return "user/add_contact";
    }
   
    //Processing add contact
    @PostMapping("/add")
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult bindingResult, Authentication authentication, HttpSession session){
        if(bindingResult.hasErrors()){
            session.setAttribute("message",new Message("Please correct the following errors", MessageType.red));
            return "user/add_contact";  
        }
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);

        //processing the image
        String fileUrl = "";
        if(contactForm.getContactPic().isEmpty()){
            fileUrl = "/images/user-gray.png";
        }else{
            fileUrl = imageService.uploadImage(contactForm.getContactPic());
        }
        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setFavourite(contactForm.isFavourite());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setLinkedinLink(contactForm.getLinkedinLink());
        contact.setFacebookLink(contactForm.getFacebookLink());
        contact.setUser(user);
        contact.setContactPicLink(fileUrl);
        contactService.saveContact(contact);

        session.setAttribute("message",new Message("You have successfully added a new contact", MessageType.green));
        return "user/add_contact";
    }

    //view contacts
    @GetMapping
    public String viewContacts(@RequestParam(value="page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue=AppConstants.PAGE_SIZE + "") int size, @RequestParam(value ="sortBy", defaultValue="name") String sortBy, @RequestParam(value = "direction", defaultValue="asc") String direction, Model model, Authentication authentication){
        //load all the users contacts
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user= userService.getUserByEmail(username);
        Page<Contact> pageContacts =contactService.getByUser(user, page,size, sortBy, direction);
        
        model.addAttribute("pageContacts", pageContacts);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        return "user/contacts";
    }

    //Search Handler
    @GetMapping("/search")
    public String searchHandler(@RequestParam("searchBy") String searchBy,
     @RequestParam("keyword") String keyword,
      @RequestParam(value = "size", defaultValue=AppConstants.PAGE_SIZE+"") int size,
       @RequestParam(value = "page", defaultValue = "0") int page,
       @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
       @RequestParam(value ="direction", defaultValue = "asc")String direction,
       @RequestParam(value="favourite", defaultValue="false") boolean favourite,
        Model model, Authentication authentication){ 
        
        User user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));

        Page<Contact> pageContacts =  null;
        if(searchBy.equalsIgnoreCase("name")){
           pageContacts  = contactService.searchByName(keyword,  size, page, sortBy, direction, user);
        }else if(searchBy.equalsIgnoreCase("email")){
           pageContacts =  contactService.searchByEmail(keyword,  size, page, sortBy, direction, user);
        }else if(searchBy.equalsIgnoreCase("phoneNumber")){
           pageContacts = contactService.searchByPhoneNumber(keyword,  size, page, sortBy, direction, user);
        }

        List<Contact> contacts = pageContacts.toList();
        if(favourite){
            pageContacts = contactService.getFavouriteFromContacts(contacts);
        }

        model.addAttribute("pageContacts", pageContacts);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("keyword", keyword);
        model.addAttribute("searchBy", searchBy);
        model.addAttribute("isFavourite", favourite);

        return "user/search";
    }

    //delete contact
    @RequestMapping("/delete/{id}")
    public String deleteContact(@PathVariable("id") String id, HttpSession session){
        contactService.deleteByCid(id);

        session.setAttribute("message", new Message("Contact deleted succesfully", MessageType.green));
        return "redirect:/user/contacts";
    }

    //update contact
    @RequestMapping("/view/{contactId}")
    public String updateContactFormView(@PathVariable("contactId") String contactId,Model model){
        var contact = contactService.getById(contactId);
        ContactForm contactForm = new ContactForm();
        model.addAttribute(contactId, model);

        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setFavourite(contact.isFavourite());
        contactForm.setName(contact.getName());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setLinkedinLink(contact.getLinkedinLink());
        contactForm.setFacebookLink(contact.getFacebookLink());
        contactForm.setContactPicLink(contact.getContactPicLink());
        System.out.println("contactForm: "+contactForm.getContactPicLink());
        

        model.addAttribute("contactForm", contactForm); 
        model.addAttribute("contactId", contactId);
        return "user/update_contact";
    }

    @PostMapping("/update/{contactId}")
    public String updateContact(HttpSession session, @PathVariable("contactId") String contactId,@Valid @ModelAttribute ContactForm contactForm,BindingResult bindingResult, Model model){
        // update the contact
        if(bindingResult.hasErrors()){
            return "user/update_contact";
        }
        Contact contact = new Contact();
        
        contact.setId(contactId);
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setContactPicLink(contactForm.getContactPicLink());
        contact.setFavourite(contactForm.isFavourite());
        contact.setLinkedinLink(contactForm.getLinkedinLink());
        contact.setFacebookLink(contactForm.getFacebookLink());
 
        if(!contactForm.getContactPic().isEmpty()){
            String fileUrl = imageService.uploadImage(contactForm.getContactPic());
            contact.setContactPicLink(fileUrl);
        }
        
        
        contactService.update(contact);
        session.setAttribute("message", new Message("Your contact has been updated", MessageType.green));
        
        return "redirect:/user/contacts/view/"+contactId;
    }
    @GetMapping("/favourite")
    public String favouriteContacts(@RequestParam(value="page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue=AppConstants.PAGE_SIZE + "") int size, @RequestParam(value ="sortBy", defaultValue="name") String sortBy, @RequestParam(value = "direction", defaultValue="asc") String direction, Model model, Authentication authentication){

        String username = Helper.getEmailOfLoggedInUser(authentication);
        
        User user= userService.getUserByEmail(username);

        Page<Contact> pageContacts = contactService.getFavouriteByUser(user, page, size, sortBy, direction);
        
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("pageContacts", pageContacts);
        return "user/favourite-contacts.html";
    }
}
