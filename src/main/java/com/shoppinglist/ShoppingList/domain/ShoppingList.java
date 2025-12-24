package com.shoppinglist.ShoppingList.domain;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "shopping_lists")
public class ShoppingList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name =  "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();


    protected ShoppingList(){}

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
