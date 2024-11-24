package com.example.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.example.model.*;

import java.nio.file.Path;

@Service
public class ContactService {

    private final String dataDir;

    public ContactService(@Value("${data.dir}") String dataDir) {
        this.dataDir = dataDir;
    }

    public String createContact(Contacts contact) {
        try {
            // generate the file and get the file path
            Path contactFilePath = createContactFile();

            // write contact data to the file
            writeContactToFile(contactFilePath, contact);
            return contactFilePath.getFileName().toString(); // Return the contact ID (file name)

        } catch (IOException e) {
            // Log and handle errors appropriately
            throw new RuntimeException("Error creating contact file: " + e.getMessage(), e);
        }

    }

    public Contacts getContactById(String id) throws IOException {
        Path contactFilePath = Paths.get(dataDir, id);

        if (!Files.exists(contactFilePath)) {
            throw new IOException("Contact file not found for id: " + id);
        }

        // need to read the file?
        try (BufferedReader reader = Files.newBufferedReader(contactFilePath, StandardCharsets.UTF_8)) {
            String line = null;
            String name = null;
            String phoneNumber = null;
            String email = null;
            LocalDate dob = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Name:")) {
                    name = line.substring(5).trim();
                }
                if (line.startsWith("Phone Number:")) {
                    phoneNumber = line.substring(13).trim();
                }
                if (line.startsWith("Email:")) {
                    email = line.substring(6).trim();
                }
                if (line.startsWith("Date of Birth:")) {
                    String dateOfBirth = line.substring(14).trim();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
                    dob = LocalDate.parse(dateOfBirth, formatter);
                }

            }

            if (name == null || email == null || dob == null) {
                throw new IOException("Incomplete contact data in file for id: " + id);
            }

            return new Contacts(name, email, phoneNumber, dob);

        }

    }

    public List<Path> listAllContactFilePath() throws IOException {

        Path directoryPath = Paths.get(dataDir);
        List<Path> pathList = new ArrayList<>(); // if pathList is null, Thymeleaf will throw a parsing error

        // instead of looping through file paths,loop through the users?
        
        // ensure that service method listAllContactFilePath
        // correctly initializes the list even if the directory is empty
        if (Files.exists(directoryPath)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(directoryPath)) {
                for (Path file : stream) {
                    pathList.add(file);
                }
            }
        }
        

        return pathList;

    }

    // need to create the file
    private Path createContactFile() throws IOException {

        // need to create id -> Randomly generate an 8 character long hex string (eg
        // abcd1234); this hex
        // string will be used as the id for the data
        RandomDataGenerator randomDataGenerator = new RandomDataGenerator(); // is this a good idea?
        String contactId = randomDataGenerator.nextHexString(8);
        Path contactFilePath = Paths.get(dataDir, contactId);

        // check if file already exists, if not, create it
        if (!Files.exists(contactFilePath)) {
            Files.createFile(contactFilePath);
        }

        return contactFilePath;
    }

    private void writeContactToFile(Path contactFilePath, Contacts contact) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(contactFilePath, StandardCharsets.UTF_8)) {
            writer.write("Name: " + contact.getName());
            writer.newLine();
            writer.write("Phone Number: " + contact.getPhoneNumber());
            writer.newLine();
            writer.write("Email: " + contact.getEmail());
            writer.newLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String formattedDate = contact.getDateOfBirth().format(formatter);
            writer.write("Date of Birth: " + formattedDate);
        }
    }

}
