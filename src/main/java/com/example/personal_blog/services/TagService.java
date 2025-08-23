package com.example.personal_blog.services;

import com.example.personal_blog.dto.TagDto;
import com.example.personal_blog.dto.mappers.TagMapper;
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

    public Set<Tag> getAllTags() {
        return new HashSet<>(tagRepository.findAll());
    }

    public List<TagDto>  getAllTagsDto() {
        List<TagDto> tagsDto = new ArrayList<>();
        for (Tag tag : tagRepository.findAll()) {
            tagsDto.add(new TagMapper().toDto(tag));
        }
        return tagsDto;
    }

    public Set<Tag> getTagsById(Set<Long> ids) {
        return tagRepository.findTagsByIdIn(ids);
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
