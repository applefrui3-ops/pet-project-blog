package com.example.personal_blog.controllers;

import com.example.personal_blog.dto.ContactDto;
import com.example.personal_blog.dto.mappers.ContactMapper;
import com.example.personal_blog.models.Post;
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

@Controller
public class MainController {

    private PostService postService;
    private TagService tagService;
    private ContactService contactService;

    public MainController(PostService postService, TagService tagService, ContactService contactService) {
        this.postService = postService;
        this.tagService = tagService;
        this.contactService = contactService;
    }

    @GetMapping("/")
    public String home(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        model.addAttribute("breadcrumbs",
                BreadcrumbUtil.createBreadcrumbs("breadcrumb.home", "#"));
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
        model.addAttribute("breadcrumbs",
                BreadcrumbUtil.createBreadcrumbs("breadcrumb.main", "/", "breadcrumb.post", "#"));
        model.addAttribute("post", postService.getPostByIdWithTags(id));
        return "post/detail";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("breadcrumbs",
                BreadcrumbUtil.createBreadcrumbs("breadcrumb.main", "/", "breadcrumb.about", "#"));
        model.addAttribute("postsAmount", postService.postsCount());
        return "about";
    }

    @GetMapping("/contacts")
    public String contacts(Model model){
        model.addAttribute("breadcrumbs",
                BreadcrumbUtil.createBreadcrumbs("breadcrumb.main", "/", "breadcrumb.contacts", "#"));
        model.addAttribute("contactDto",  new ContactDto());
        return "contacts";
    }

    @PostMapping("/contacts")
    public String contacts(
            Model model,
            @Valid @ModelAttribute ContactDto contactDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        model.addAttribute("breadcrumbs",
                BreadcrumbUtil.createBreadcrumbs("breadcrumb.main", "/", "breadcrumb.contacts", "#"));
        if(bindingResult.hasErrors()){
            model.addAttribute("contactDto",  contactDto);
            model.addAttribute("errors", "\"Failed to send message. Please try again.\"");
            return "contacts";
        }
        try{
            contactService.save(new ContactMapper().toEntity(contactDto));
            model.addAttribute("contactDto",  new ContactDto());
            redirectAttributes.addFlashAttribute("successMessage", "contacts.connect.form.success1");
            return "redirect:/contacts?success";
        }catch (Exception e){
            model.addAttribute("contactDto",  contactDto);
            model.addAttribute("errors", "\"Failed to send message. Please try again.\"");
            return "contacts";
        }

    }
}
