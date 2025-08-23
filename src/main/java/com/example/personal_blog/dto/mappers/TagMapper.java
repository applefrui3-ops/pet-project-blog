package com.example.personal_blog.dto.mappers;

import com.example.personal_blog.dto.TagDto;
import com.example.personal_blog.models.Post;
import com.example.personal_blog.models.Tag;
import com.example.personal_blog.services.PostService;

import java.util.HashSet;
import java.util.Set;

public class TagMapper{


public TagDto toDto(Tag tag){
    TagDto dto = new TagDto();
    dto.setId(tag.getId());
    dto.setName(tag.getName());
    Set<Long>  ids = new HashSet<>();
    for(Post post : tag.getPosts()){
        ids.add(post.getId());
    }
    dto.setIds(ids);
    return dto;
}

public Tag toEntity(TagDto dto){
    Tag tag = new Tag();
    tag.setId(dto.getId());
    tag.setName(dto.getName());
    return tag;
}

public Tag toEntity(TagDto dto, Set<Post> posts){
    Tag tag = new Tag();
    tag.setId(dto.getId());
    tag.setName(dto.getName());
    tag.setPosts(posts);
    return tag;
}

}
