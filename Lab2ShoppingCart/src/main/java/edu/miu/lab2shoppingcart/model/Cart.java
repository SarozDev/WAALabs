package edu.miu.lab2shoppingcart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class Cart {

    private int quantity;
    private Product product;

    public double getTotalPrice() {
        return quantity * (product == null ? 0.0 : product.getPrice());
    }
}
