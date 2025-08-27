package com.example.personal_blog.controllers;

import com.example.personal_blog.dto.mappers.PostMapper;
import com.example.personal_blog.models.Post;
import com.example.personal_blog.services.PostService;
import com.example.personal_blog.services.TagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private PostService postService;
    private TagService tagService;

    public HomeController(PostService postService, TagService tagService) {
        this.postService = postService;
        this.tagService = tagService;
    }

    @GetMapping("/")
    public String home(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postPage;
        postPage = postService.getPostsOrderByCreatedDescTitleAsc(pageable);

        model.addAttribute("posts", postPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postPage.getTotalPages());
        model.addAttribute("totalElements", postPage.getTotalElements());
        model.addAttribute("size", size);

        return "index";
    }

    @GetMapping("/post/{id}")
    public String getPost(Model model, @PathVariable("id") Long id) {
        model.addAttribute("post", postService.getPostByIdWithTags(id));
        return "post/detail";
    }

}
