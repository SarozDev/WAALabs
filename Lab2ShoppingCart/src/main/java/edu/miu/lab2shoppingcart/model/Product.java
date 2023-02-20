package edu.miu.lab2shoppingcart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Product {

    @Size(min = 2,max = 5)
    @NotNull
    @NotBlank
    private String number;
    @Size(min = 2,max = 20)
    @NotNull
    @NotBlank
    private String name;

    private Double price;
}
