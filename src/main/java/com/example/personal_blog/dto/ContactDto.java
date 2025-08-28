package com.example.personal_blog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ContactDto {
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 255, message = "Name must be between 2 and 50 characters")
    private String name;
    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Size(max = 50, message = "Email must be less than 50 characters")
    private String email;
    @NotBlank(message = "Subject is required")
    @Size(min = 5, max = 100, message = "Subject must be between 5 and 100 characters")
    private String subject;
    @NotBlank(message = "Message is required")
    @Size(min = 5, max = 1000, message = "Message must be between 5 and 10000 characters")
    private String message;

    public ContactDto() {}

    public ContactDto(String name, String email, String subject, String message) {}

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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
