package com.manjosh.practice.mutipleauthenticationproviders.repository;

import com.manjosh.practice.mutipleauthenticationproviders.model.Post;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends ListCrudRepository<Post,Integer> {
}