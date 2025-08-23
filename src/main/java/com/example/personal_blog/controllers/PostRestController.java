package com.example.personal_blog.controllers;

import com.example.personal_blog.services.PostService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts/")
public class PostRestController {
    private PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }
}
