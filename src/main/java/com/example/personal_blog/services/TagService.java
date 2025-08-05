package com.example.personal_blog.services;

import com.example.personal_blog.models.Tag;
import com.example.personal_blog.repositories.TagRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TagService {
    private TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag getTagById(Long id) {
        return tagRepository.findById(id).orElse(null);
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public void deleteTag(Tag tag) {
        tagRepository.delete(tag);
    }

    public void deleteTagById(Long id) {
        tagRepository.deleteById(id);
    }

    public Set<Tag> saveTags(List<String> tags) {
        Set<Tag> newTags = new HashSet<>();
        for(String tagName : tags) {
            Tag tag = new Tag(tagName);
            newTags.add(tag);
        }
        return new HashSet<>(tagRepository.saveAll(new ArrayList<>(newTags)));
//        return tagRepository.saveTagsByNameIn(tags);
    }
}
