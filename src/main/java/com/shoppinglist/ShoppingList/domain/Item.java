package com.shoppinglist.ShoppingList.domain;


import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private boolean bought;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "list_id", nullable = false)
    private ShoppingList shoppingList;

    protected Item(){}


    public Item(ShoppingList shoppingList, String name, int quantity){
        this.shoppingList = shoppingList;
        this.name = name;
        this.quantity = quantity;
        this.bought = false;
    }

    @PrePersist
    void onCreate(){
        this.createdAt = Instant.now();
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getQuantity() {
        return quantity;
    }
    public boolean isBought() {
        return bought;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public ShoppingList getShoppingList() {
        return shoppingList;
    }

    public void renameTo(String newName){this.name = newName;}
    public void changeQuantityTo(int newQuantity){this.quantity = newQuantity;}
    public void setBought(boolean value){this.bought = value;}

    //__________
    public void toggleBought(){
        this.bought = !this.bought;
    }

}


