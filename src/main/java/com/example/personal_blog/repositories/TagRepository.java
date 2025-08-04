package com.example.personal_blog.repositories;

import com.example.personal_blog.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashSet;
import java.util.Set;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
