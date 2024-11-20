package com.example.controllers;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.model.Contacts;
import com.example.service.ContactService;

@Controller
@RequestMapping("/contact")
public class ContactFileController {

    private final ContactService contactService;

    public ContactFileController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/{id}")
    public String getContactFile(@PathVariable String id, Model model) throws IOException {

        Contacts contact = contactService.getContactById(id);
        // Format the dateOfBirth to dd-MM-yyyy format before adding it to the model
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDob = contact.getDateOfBirth().format(formatter);

        // Add the formatted date as a string
        model.addAttribute("formattedDob", formattedDob);
        
        model.addAttribute("contact", contact);
        return "contactDetails";

    }

    @GetMapping("/links")
    public String getContactFileLinks(Model model) throws IOException {

        var pathList = contactService.listAllContactFilePath();
        // convert Path objects to List<Strings> file names
        List<String> fileNames = pathList.stream()
                                        .map(path -> path.getFileName().toString())
                                        .collect(Collectors.toList());
       
        model.addAttribute("fileNames", fileNames);

        return "contacts";
    }

}
