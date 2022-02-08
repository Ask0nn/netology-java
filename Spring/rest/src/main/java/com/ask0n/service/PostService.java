package com.ask0n.service;

import com.ask0n.exception.NotFoundException;
import com.ask0n.model.Post;
import com.ask0n.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public List<Post> all() {
        return repository.all().stream()
                .filter(p -> !p.isRemoved())
                .collect(Collectors.toList());
    }

    public Post getById(long id) {
        var post = repository.getById(id).orElseThrow(NotFoundException::new);
        if (post.isRemoved()) throw new NotFoundException();
        return post;
    }

    public Post save(Post post) {
        if (post.getId() != 0) getById(post.getId());
        return repository.save(post);
    }

    public void removeById(long id) {
        repository.removeById(id);
    }
}
