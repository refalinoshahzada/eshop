# Advanced Programming Course

## Table of Contents
1. Module

## Module 1 - Coding Standard

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

I've properly implemented error handling in this project through the use of the validation dependencies to ensure no shrewd or suspicious inputs.
Here is a snippet of my code to show:

```Java
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
```

# Reflection 2: Unit Testing

**Unit Testing Principles**

1. Feelings about Unit Testing

After writing unit testing, I have come to realize its importance in code quality and preventing attacks.
At first, writing unit tests seem very challenging and redundant, but after finishing it I feel more confident in my code as unit testing helps me identify bugs very early into development.
As for the **quantity of a unit test**,I think here shouldn't be a specific or minimum quantity for unit testing, but instead quality and what functionality they provide. 
Every test written should cover **expected behavior**, **edge cases**, **error conditions** and **integration with other components**.
For **code coverage**, I think its a pretty useful way to measure but doesnt determine everything from a test quality.
This is because there are things that might slip by undetected by the coverages. To name a few, there are cases where tests might not cover all possible input combinations, logic errors can exist even with full path coverage, edge cases might be missed despite covering all lines.
I think quality tests should focus on ensuring error handling, validating integration points, testing business logic and verifying edge cases.

2. Creating a new function test

Regarding **Functional Test Code Cleanliness Issues**, there could be code duplication where there could be repeated test classes. Why is this an issue? because it violates DRY (Don't Repeat Yourself) principle, makes maintenance more difficult, increases chance of inconsistencies and decreases code reability.
To improve this, a few things I would do is **implement page object pattern**, **create a base test class** and **create test helper methods**.
With these improvements it would provide a sustainable and consistent test architecture that prevents inconsistencies.