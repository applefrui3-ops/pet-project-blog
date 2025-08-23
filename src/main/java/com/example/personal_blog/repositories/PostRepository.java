package com.example.personal_blog.repositories;

import com.example.personal_blog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Set;

public interface PostRepository extends JpaRepository<Post,Long> {

    @Query("SELECT DISTINCT p FROM Post p LEFT JOIN FETCH p.tags ORDER BY p.id ASC")
    public Set<Post> findAllWithTags();

    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.tags WHERE p.id = :id ORDER BY p.id ASC")
    public Post findPostByIdWithTags(@Param("id") Long id);

    @Query("SELECT p.createdAt FROM Post p WHERE p.id = :id")
    public LocalDateTime getCreatedAt(@Param("id") Long id);
}
