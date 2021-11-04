package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.repositories.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {
    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

//    @GetMapping("/posts")
//    public String showPosts(Model model) {
//        Post postOne = new Post("My First Blog", "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquid animi autem consectetur culpa cumque doloribus eaque esse facilis, fugit ipsum laudantium minima mollitia, possimus quidem rem repellendus soluta tenetur voluptatem!");
//        Post postTwo = new Post("My Second Blog", "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquid animi autem consectetur culpa cumque doloribus eaque esse facilis, fugit ipsum laudantium minima mollitia, possimus quidem rem repellendus soluta tenetur voluptatem!");
//
//        List<Post> posts = new ArrayList<>();
//
//        posts.add(postOne);
//        posts.add(postTwo);
//
//        model.addAttribute("posts", posts);
//
//        return "posts/index";
//    }

    @GetMapping("/posts")
    @ResponseBody
    public List<Post> showPosts(Model model) {
        return postRepository.findAll();
    }

//    @GetMapping("/posts/{id}")
//    @ResponseBody
//    public String showSinglePost(@PathVariable long id, Model model) {
//        Post post = new Post("My First Blog", "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquid animi autem consectetur culpa cumque doloribus eaque esse facilis, fugit ipsum laudantium minima mollitia, possimus quidem rem repellendus soluta tenetur voluptatem!");
//
//        model.addAttribute("post", post);
//        return "posts/show";
//    }

    @GetMapping("/posts/create")
    public String postForm() {
        return "posts/form";
    }

    @PostMapping("/posts/create")
    @ResponseBody
    public String createPost(@RequestBody Post newPost) {
        postRepository.save(newPost);
        return String.format("Ad created with an ID of: %s", newPost.getId());
    }
}
