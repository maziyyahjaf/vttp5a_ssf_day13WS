package com.example.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public class Contacts {

    @NotNull(message="Name cannot be null")
    @Size(min=3, max=64, message="Name must be between 3 and 64 characters")
    private String name;

    @NotNull(message="Email cannot be null")
    @Email(message="Must be a valid email")
    private String email;

    @NotNull(message="Phone number cannot be null")
    @Size(min=7)
    private String phoneNumber;

    @Past
    @NotNull(message="You must set your DOB")
    @DateTimeFormat(pattern="dd-MM-yyyy")
    private LocalDate dateOfBirth;


    public Contacts() {
    }

    
    public Contacts(
            @NotNull(message = "Name cannot be null") @Size(min = 3, max = 64, message = "Name must be between 3 and 64 characters") String name,
            @NotNull(message = "Email cannot be null") @Email(message = "Must be a valid email") String email,
            @NotNull(message = "Phone number cannot be null") @Size(min = 7) String phoneNumber,
            @Past @NotNull(message = "You must set your DOB")  @DateTimeFormat(pattern="dd-MM-yyyy") LocalDate dateOfBirth) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    
    
}
