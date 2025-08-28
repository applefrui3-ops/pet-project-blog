package com.example.personal_blog.services;

import com.example.personal_blog.models.Contact;
import com.example.personal_blog.repositories.ContactRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContactService {

    private ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact>  findAll() {
        return contactRepository.findAll();
    }

    public Page<Contact> findAll(Pageable pageable) {
        return contactRepository.findAll(pageable);
    }

    public Contact findById(Long id) {
        return contactRepository.findById(id).orElse(null);
    }

    public Contact save(Contact contact) {
        return contactRepository.save(contact);
    }

    public void deleteById(Long id) {
        contactRepository.deleteById(id);
    }

    public void setAsRead(Contact contact) {
        contact.setReadAt(LocalDateTime.now());
        contactRepository.save(contact);
    }
}
