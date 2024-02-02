package com.manjosh.practice.client.repository;

import com.manjosh.practice.client.entity.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<CustomUser,Long> {
    CustomUser findByEmail(String email);
}
