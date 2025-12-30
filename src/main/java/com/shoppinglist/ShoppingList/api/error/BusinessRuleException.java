package com.shoppinglist.ShoppingList.api.error;

public class BusinessRuleException extends RuntimeException {
    public BusinessRuleException(String message){
        super(message);
    }
}
