package com.example.personal_blog.services;

import com.example.personal_blog.models.Post;
import com.example.personal_blog.repositories.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Service
public class PostService {
    private PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post getPostByIdWithTags(Long id) {
        return postRepository.findPostByIdWithTags(id);
    }

    public Set<Post> findAllWithTags() {
        return postRepository.findAllWithTags();
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }

    public void deletePost(Post post){
        postRepository.delete(post);
    }

    public void deletePostById(Long id){
        postRepository.deleteById(id);
    }

    public LocalDateTime getCreatedAt(Long id) {
        return postRepository.getCreatedAt(id);
    }

    public Page<Post> getPostsOrderByCreatedDescTitleAsc(Pageable pageable) {
        return postRepository.findAllByOrderByCreatedAtDescTitleAsc(pageable);
    }

    public Page<Post> getPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public long postsCount() {
        return postRepository.count();
    }
}
