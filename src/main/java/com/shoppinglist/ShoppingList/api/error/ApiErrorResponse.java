package com.shoppinglist.ShoppingList.api.error;

import java.time.Instant;
import java.util.List;

public record ApiErrorResponse (
        Instant timestap,
        int status,
        String error,
        String message,
        String path,
        List<FieldViolation> violations
){
    public record  FieldViolation(String failed, String message){}
}
