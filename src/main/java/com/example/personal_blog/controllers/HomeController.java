package com.example.personal_blog.controllers;

import com.example.personal_blog.services.PostService;
import com.example.personal_blog.services.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private PostService postService;
    private TagService tagService;

    public HomeController(PostService postService, TagService tagService) {
        this.postService = postService;
        this.tagService = tagService;
    }

    @GetMapping("/")
    public String home(Model model){
        return "index";
    }

}
