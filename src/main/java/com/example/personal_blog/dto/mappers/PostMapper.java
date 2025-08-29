package com.example.personal_blog.dto.mappers;

import com.example.personal_blog.dto.PostDto;
import com.example.personal_blog.models.Post;
import com.example.personal_blog.models.Tag;
import com.example.personal_blog.services.TagService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PostMapper{

    public PostDto toDto(Post post){
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setSummary(post.getSummary());
        dto.setImageSrc(post.getImageSrc());
        dto.setImageAlt(post.getImageAlt());
        dto.setTags(post.getTags());
        return  dto;
    }

    public Post toEntity(PostDto dto){
        Post post = new Post();
        post.setId(dto.getId());
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setSummary(dto.getSummary());
        post.setImageSrc(dto.getImageSrc());
        post.setImageAlt(dto.getImageAlt());
        post.setTags(dto.getTags());
        return post;
    }

    public Post toEntity(PostDto dto, LocalDateTime createdAt){
        Post post = new Post();
        post.setId(dto.getId());
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setSummary(dto.getSummary());
        post.setImageSrc(dto.getImageSrc());
        post.setImageAlt(dto.getImageAlt());
        post.setCreatedAt(createdAt);
        post.setTags(dto.getTags());
        return post;
    }
}
