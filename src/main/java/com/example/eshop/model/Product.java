package com.example.eshop.model;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;

@Getter @Setter
public class Product {
    private String productId;

    @NotBlank(message = "Fill in the product name!")
    private String productName;

    @Min(value = 0, message = "Product quantity must be 0 or more!")
    private int productQuantity;
}