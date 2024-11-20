package com.example.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.model.Contacts;
import com.example.service.ContactService;

import jakarta.validation.Valid;

@Controller
@RequestMapping(path = "/contact")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public String getRegistration(Model model) {
        model.addAttribute("registration", new Contacts());
        return "registration";
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Contacts> createContact(@ModelAttribute @Valid Contacts contact, Model model,
            BindingResult binding) {
        if (binding.hasErrors()) {
            return ResponseEntity.badRequest().body(contact); // Return bad request if validation fails
        }

        // Delegate file creation to the service
        contactService.createContact(contact);

        // Return created status with the contact data
        return ResponseEntity.status(HttpStatus.CREATED).body(contact);

    }

}
