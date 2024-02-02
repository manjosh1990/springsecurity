package org.manjosh.practice.controller;

import lombok.RequiredArgsConstructor;
import org.manjosh.practice.dto.Product;
import org.manjosh.practice.entity.UserInfo;
import org.manjosh.practice.service.ProductService;
import org.manjosh.practice.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final UserService userService;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/register")
    public String addNewUser(@RequestBody UserInfo userInfo){
        return userService.addUser(userInfo);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Product> getAllTheProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_GUEST')")
    public Product getProductById(@PathVariable int id) {
        return productService.getProduct(id);
    }
}
