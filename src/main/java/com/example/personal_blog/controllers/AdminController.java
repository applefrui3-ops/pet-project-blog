package com.example.personal_blog.controllers;

import com.example.personal_blog.dto.PostDto;
import com.example.personal_blog.dto.mappers.PostMapper;
import com.example.personal_blog.models.Post;
import com.example.personal_blog.models.Tag;
import com.example.personal_blog.services.PostService;
import com.example.personal_blog.services.TagService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.HashSet;

@Controller
public class AdminController {

    private PostService postService;
    private TagService tagService;

    public AdminController(PostService postService, TagService tagService) {
        this.postService = postService;
        this.tagService = tagService;
    }

    @GetMapping("/admin")
    public String admin(Model model,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "15") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postPage = postService.getPosts(pageable);
        model.addAttribute("posts", postPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postPage.getTotalPages());
        model.addAttribute("totalElements", postPage.getTotalElements());
        model.addAttribute("size", size);
        return "admin/admin";
    }

    @GetMapping("/admin/post/new")
    public String createPost(Model model) {
        model.addAttribute("postDto", new PostDto());
        model.addAttribute("tags", tagService.getAllTags());
        return "admin/create-post";
    }

    @PostMapping("/admin/post/new")
    public String createPost(@Valid @ModelAttribute("postDto") PostDto postDto,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("postDto", postDto);
            model.addAttribute("tags", tagService.getAllTags());
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "admin/create-post";
        }
            postService.save(new PostMapper().toEntity(postDto));
            redirectAttributes.addFlashAttribute("successMessage", "Post successfully created!");
            return "redirect:/admin";
    }

    @GetMapping("/admin/post/edit/{id}")
    public String editPost(Model model,
                           @PathVariable Long id,
                           @ModelAttribute("postDto") @Valid PostDto postDto,
                           BindingResult bindingResult) {
        model.addAttribute("postDto", new PostMapper().toDto(postService.getPostByIdWithTags(id)));
        model.addAttribute("tags", tagService.getAllTags());
        return "admin/edit-post";
    }

    @PostMapping("/admin/post/edit/{id}")
    public String editPost(@PathVariable Long id,
                           @Valid @ModelAttribute("post") PostDto postDto,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("postDto", postDto);
            model.addAttribute("tags", tagService.getAllTags());
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "admin/edit-post";
        }
        try{
            postService.save(new PostMapper().toEntity(postDto, postService.getCreatedAt(id)));
            redirectAttributes.addFlashAttribute("successMessage", "Post successfully edited!");
            return "redirect:/admin";
        }catch (Exception e){
            bindingResult.rejectValue("title", "title.duplicate", e.getMessage());
            model.addAttribute("postDto", postDto);
            model.addAttribute("tags", tagService.getAllTags());
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "admin/edit-post";
        }
    }

    @GetMapping("/admin/post/delete/{id}")
    public String deletePost(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes
    ) {
        try {
            postService.deletePostById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Post deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting post: " + e.getMessage());
        }
        return "redirect:/admin";
    }


    @GetMapping("/admin/tag/new")
    public String addTags(Model model) {
        model.addAttribute("tag", new Tag());
        model.addAttribute("tags", tagService.getAllTags());
        return "admin/create-tag";
    }

    @PostMapping("/admin/tag/new")
    public String addTags(@Valid @ModelAttribute("tag") Tag tag) {
        tagService.saveTag(tag);
        return "admin/create-tag";
    }


}
