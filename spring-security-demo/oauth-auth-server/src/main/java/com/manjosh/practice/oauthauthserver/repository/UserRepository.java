package com.manjosh.practice.oauthauthserver.repository;


import com.manjosh.practice.oauthauthserver.entity.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<CustomUser,Long> {
    CustomUser findByEmail(String email);
}
