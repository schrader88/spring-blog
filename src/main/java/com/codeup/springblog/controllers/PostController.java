package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PostController {

    @GetMapping("/posts")
    @ResponseBody
    public String showPosts() {
        return "Showing all posts:";
    }

    @GetMapping("/posts/{id}")
    @ResponseBody
    public String showSinglePost(@PathVariable long id) {
        return "Showing all posts: " + id;
    }

    @GetMapping("/posts/create")
    @ResponseBody
    public String postForm() {
        return "Here is a form to create a post: ";
    }

    @PostMapping("/posts/create")
    @ResponseBody
    public String createPost() {
        return "Creating a new post...";
    }
}
