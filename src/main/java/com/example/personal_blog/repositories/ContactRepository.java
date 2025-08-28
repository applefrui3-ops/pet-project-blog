package com.example.personal_blog.repositories;

import com.example.personal_blog.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
