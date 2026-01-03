package com.shoppinglist.ShoppingList.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shopping_lists")
public class ShoppingList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(name =  "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    void onCreate(){
        this.createdAt = Instant.now();
    }


    public ShoppingList(){}
    public ShoppingList(@NotBlank @Size(min = 2, max = 120) String name){this.name = name;}
    @OneToMany(mappedBy = "shoppingList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items = new ArrayList<>();

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
}
