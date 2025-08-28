package com.example.personal_blog.dto.mappers;

import com.example.personal_blog.dto.ContactDto;
import com.example.personal_blog.models.Contact;

public class ContactMapper {
    public ContactDto toDto(Contact contact) {
        ContactDto contactDto = new ContactDto();
        contactDto.setName(contact.getName());
        contactDto.setEmail(contact.getEmail());
        contactDto.setSubject(contact.getSubject());
        contactDto.setMessage(contact.getMessage());
        return contactDto;
    }

    public Contact toEntity(ContactDto contactDto) {
        Contact contact = new Contact();
        contact.setName(contactDto.getName());
        contact.setEmail(contactDto.getEmail());
        contact.setSubject(contactDto.getSubject());
        contact.setMessage(contactDto.getMessage());
        return contact;
    }
}
