package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class PostRepositoryImpl implements PostRepository {
    private final List<Post> posts = new ArrayList(Arrays.asList(
            new Post(1, "test1"),
            new Post(2, "test2"),
            new Post(3, "test3")));

    public synchronized List<Post> all() {
        return posts;
    }

    public synchronized Optional<Post> getById(long id) {
        return posts.stream().filter(p -> p.getId() == id).findAny();
    }

    public synchronized Post save(Post post) {
        if (post.getId() == 0) {
            if (posts.isEmpty())
                post.setId(1);
            else
                post.setId(posts.get(posts.size() - 1).getId() + 1);
            posts.add(post);
        } else {
            final var oldPost = getById(post.getId());
            if (oldPost.isPresent()) {
                posts.set(posts.indexOf(oldPost.get()), post);
            } else
                throw new NotFoundException("Post with id " + post.getId() + " not found");
        }

        return post;
    }

    public synchronized void removeById(long id) {
        posts.stream().filter(p -> p.getId() == id).findAny().ifPresentOrElse(posts::remove, () -> { throw new NotFoundException(); });
    }
}
