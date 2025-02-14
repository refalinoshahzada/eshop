# Advanced Programming Course

## Table of Contents
1. Module

## Module 1- Coding Standard

# Reflection 1: Clean Code and Secure Coding Practices

**Clean Code Principles**

1. Meaningful Names

Using meaningful names for methods, variables, and classes improve code readability. This is an example of my code:

```Java
    public Product findById(String productId) {
        for (Product product : productData) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }
```
`findByid` is a method name that clearly states and explains what the method does, 
`productId` avoids naming ambiguity while keeping a simple, the same also goes for `productData`.

2. Functions Should Do One Thing

Each and every function/method should be perform one single task. This is an example of my code:
```Java
    public void deleteById(String productId) {
        productData.removeIf(product -> product.getProductId().equals(productId));
    }
```

3. Use of Objects and Data Structures

I've properly implemented the use of Data Structures and Objects in this project while also making sure there are no unnecessary independencies between variables.
The main example of a data structure usage in this project is `ArrayList`.

```Java
    private List<Product> productData = new ArrayList<>();
```

4. Error Handling
