package com.example.personal_blog.controllers;


import com.example.personal_blog.dto.TagDto;
import com.example.personal_blog.models.Tag;
import com.example.personal_blog.services.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/tags")
public class TagRestController {
    private TagService tagService;

    public TagRestController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<List<TagDto>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTagsDto());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addTags(@RequestBody Map<String, List<String>> request) {
        List<String> tagNames = request.get("tags");
        Set<Tag> savedTags = tagService.saveTags(tagNames);
        return ResponseEntity.ok(savedTags);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTagById(id);
        return ResponseEntity.noContent().build();
    }
}
