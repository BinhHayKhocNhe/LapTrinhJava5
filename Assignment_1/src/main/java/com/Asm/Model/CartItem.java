package com.Asm.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private Products product;
    private int quantity;

  
    public CartItem(Products product) {
        this.product = product;
        this.quantity = 1; 
    }
}

   
