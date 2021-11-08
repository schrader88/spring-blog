package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.models.PostImage;
import com.codeup.springblog.models.User;
import com.codeup.springblog.repositories.PostRepository;
import com.codeup.springblog.repositories.UserRepository;
import com.codeup.springblog.services.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public PostController(PostRepository postRepository, UserRepository userRepository, EmailService emailService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @GetMapping("/posts")
    public String index(Model model) {
        // fetch all posts with postRepository
        List<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "posts/index";

    }

    @GetMapping("/posts/create")
    public String postForm(Model model) {
        model.addAttribute("post", new Post());
        return "posts/create";
    }

    @PostMapping("/posts/create")
    public String createPost(@ModelAttribute Post post, @RequestParam List<String> urls) {
        List<PostImage> images = new ArrayList<>();

        User user = userRepository.getById(1L);

        for (String url : urls) {
            PostImage postImage = new PostImage(url);
            postImage.setPost(post);
            images.add(postImage);
        }

        post.setImages(images);

        post.setUser(user);

        user.addPost(post);

        userRepository.save(user);

        emailService.prepareAndSend(post, "You created " + post.getTitle(), post.getBody());
        return "redirect:/posts";
    }

    @GetMapping("/posts/{id}/edit")
    public String returnEditView(@PathVariable long id, Model model) {
        model.addAttribute("post", postRepository.getById(id));
        return "posts/edit";
    }

    @PostMapping("/posts/{id}/edit")
    public String updatePost(@ModelAttribute("post") Post post) {

        Post editedPost = postRepository.getById(post.getId());

        editedPost.setTitle(post.getTitle());
        editedPost.setBody(post.getBody());

        postRepository.save(editedPost);
        return "redirect:/posts";
    }

    @PostMapping("posts/{id}/delete")
    public String deletePost(@PathVariable long id) {
        postRepository.deleteById(id);
        return "redirect:/posts";
    }
}
