package com.shoppinglist.ShoppingList.dto;

import java.time.Instant;

public record ShoppingListResponse(
   Long id,
   String name,
   Instant createdAt
) {}
