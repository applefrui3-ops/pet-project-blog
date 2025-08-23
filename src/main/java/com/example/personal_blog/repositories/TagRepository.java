package com.example.personal_blog.repositories;

import com.example.personal_blog.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface TagRepository extends JpaRepository<Tag, Long> {
    public Set<Tag> findTagsByIdIn(Set<Long> ids);

    @Query("SELECT t FROM Tag t LEFT JOIN FETCH t.posts")
    public Set<Tag> findAllWithPosts();

    @Query("SELECT t FROM Tag t LEFT JOIN FETCH t.posts")
    public Tag findTagByIdWithPosts(Long id);
}
