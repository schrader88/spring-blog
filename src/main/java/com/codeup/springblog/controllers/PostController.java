package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.repositories.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PostController {
    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/posts")
    public String index(Model model) {
        // fetch all posts with postRepository
        List<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "posts/index";

    }

    @GetMapping("/posts/create")
    @ResponseBody
    public String postForm() {
        return "Post Creation Form";
    }

    @PostMapping("/posts/create")
    @ResponseBody
    public String createPost(@RequestBody Post newPost) {
        postRepository.save(newPost);
        return String.format("Ad created with an ID of: %s", newPost.getId());
    }

    @GetMapping("/posts/{id}/edit")
    public String returnEditView(@PathVariable long id, Model model) {
        model.addAttribute("post", postRepository.getById(id));
        return "posts/edit";
    }

    @PostMapping("/posts/{id}/edit")
    public String updatePost(@PathVariable long id, @RequestParam String title, @RequestParam String body) {
        // use the form inputs to update the existing post in the db
        // pull the existing post object from the db
        Post post = postRepository.getById(id);
        // set the title and body to the request param values
        post.setTitle(title);
        post.setBody(body);
        // persist the change in the db with the postRepository
        postRepository.save(post);
        return "redirect:/posts";
    }

    @PostMapping("posts/{id}/delete")
    public String deletePost(@PathVariable long id) {
        postRepository.deleteById(id);
        return "redirect:/posts";
    }
}
