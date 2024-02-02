package com.manjosh.practice.mutipleauthenticationproviders.controllers;

import com.manjosh.practice.mutipleauthenticationproviders.model.Post;
import com.manjosh.practice.mutipleauthenticationproviders.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostRepository repository;

    @GetMapping
    private List<Post> findAll(){
        return repository.findAll();
    }
}
