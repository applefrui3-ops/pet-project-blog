package com.example.personal_blog.controllers;

import com.example.personal_blog.dto.PostDto;
import com.example.personal_blog.dto.mappers.PostMapper;
import com.example.personal_blog.models.Contact;
import com.example.personal_blog.models.Post;
import com.example.personal_blog.models.Tag;
import com.example.personal_blog.services.ContactService;
import com.example.personal_blog.services.PostService;
import com.example.personal_blog.services.TagService;
import com.example.personal_blog.util.BreadcrumbUtil;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class AdminController {

    private PostService postService;
    private TagService tagService;
    private ContactService contactService;

    public AdminController(PostService postService, TagService tagService, ContactService contactService) {
        this.postService = postService;
        this.tagService = tagService;
        this.contactService = contactService;
    }

    @GetMapping("/admin")
    public String admin(Model model,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "15") int size) {
        model.addAttribute("breadcrumbs",
                BreadcrumbUtil.createBreadcrumbs("Main", "/", "Admin", "#"));

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
        model.addAttribute("breadcrumbs",
                BreadcrumbUtil.createBreadcrumbs("Main", "/", "Admin", "/admin", "New Post", "#"));
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
        model.addAttribute("breadcrumbs",
                BreadcrumbUtil.createBreadcrumbs("Main", "/", "Admin", "/admin", "Edit Post", "#"));
        model.addAttribute("postDto", new PostMapper().toDto(postService.getPostByIdWithTags(id)));
        model.addAttribute("tags", tagService.getAllTags());
        return "admin/edit-post";
    }

    @PostMapping("/admin/post/edit/{id}")
    public String editPost(@PathVariable Long id,
                           @Valid @ModelAttribute("postDto") PostDto postDto,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           Model model) {
        System.out.println("\n----------------------------------\n");
        System.out.println(postDto);
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
        model.addAttribute("breadcrumbs",
                BreadcrumbUtil.createBreadcrumbs("Main", "/", "Admin", "/admin", "New Tag", "#"));
        model.addAttribute("tag", new Tag());
        model.addAttribute("tags", tagService.getAllTags());
        return "admin/create-tag";
    }

    @PostMapping("/admin/tag/new")
    public String addTags(@Valid @ModelAttribute("tag") Tag tag) {
        tagService.saveTag(tag);
        return "admin/create-tag";
    }

    @GetMapping("/admin/messages")
    public String showMessages(Model model,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size
    ) {
        model.addAttribute("breadcrumbs",
                BreadcrumbUtil.createBreadcrumbs("Main", "/", "Admin", "/admin", "Messages", "#"));
        Pageable pageable = PageRequest.of(page, size);
        Page<Contact> contactPage = contactService.findAll(pageable);
        model.addAttribute("contacts", contactPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", contactPage.getTotalPages());
        model.addAttribute("totalElements", contactPage.getTotalElements());
        model.addAttribute("size", size);
        return "admin/messages/list";
    }

    @PostMapping("/admin/messages/{id}")
    public String deleteMessages(@PathVariable Long id,
                                 RedirectAttributes redirectAttributes) {
        try{
            contactService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Message deleted successfully");
            return "redirect:/admin/messages";
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("error", "Error deleting Message: " + e.getMessage());
            return "redirect:/admin/messages";
        }
    }

    @GetMapping("/admin/messages/{id}")
    public String detailMessage(@PathVariable long id,
                                Model model) {
        model.addAttribute("breadcrumbs",
                BreadcrumbUtil.createBreadcrumbs("Main", "/", "Admin", "/admin",
                        "Messages", "/admin/messages", String.valueOf(id), "#"));
        try{
            Contact contact = contactService.findById(id);
            contactService.setAsRead(contact);
            model.addAttribute("message", contact);
            return "admin/messages/detail";
        }catch (Exception e){
            return "error/404";
        }
    }


}
