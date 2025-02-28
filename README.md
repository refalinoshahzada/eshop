# Advanced Programming Course

## Table of Contents
1. Module

# Module 1 - Coding Standard

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

# Module 2 - CI/CD & DevOps

# Reflection 1

During this exercise, I found few issues in my code quality, a few that I found and fixed were:

1. Using wrong naming conventions, where the most obvious example is the use of mixed naming format. I used both snake and camel case in one function name. This can cause naming redundancy and ambiguity when adding code related to those function names.
2. The use of wildcard imports. Before I optimized my code, I used wildcard (*) imports instead of importing each and every needed individual import. Why it's important to fix this is because wildcard imports can cause potential naming conflicts and ambiguity.
3. The use of unnecessary `public` modifiers in interface classes. This is unnecessary because variables that are declared inside an interface have a `public` modifier by default.

# Reflection 2
I think my code has met the definitions of Continuous Integration and Continuous Deployment. Workflow triggers automatically whenever there is a push request into a branch. This shows that CI has been implemented properly into my project. Project gets automatically deployed to my deploying platform (Koyeb) everytime there is a push request to a branch. This shows that CD has been implemented properly into my project.

# Module 3 

# Reflection

### **SOLID Principles Applied in My Project**

In this project, I incorporated three SOLID principles—Single Responsibility Principle (SRP), Open/Closed Principle (OCP), and Liskov Substitution Principle (LSP)—to enhance maintainability and deepen my understanding of development best practices.

#### **Single Responsibility Principle (SRP)**
The SRP states that a class or module should have only one reason to change. From the start, our project followed this principle by separating concerns—service classes like `CarServiceImpl` and `ProductServiceImpl` handle business logic, while `CarRepository` and `ProductRepository` focus on data persistence. I further reinforced SRP by refactoring `ProductController.java` in the Controller module. Initially, `CarController` was part of `ProductController` as an extended class, but I moved it to a separate file, `CarController.java`. This ensures that both `CarController` and `ProductController` handle only their respective objects, improving code organization and readability.

#### **Open/Closed Principle (OCP)**
The OCP states that software entities should be open for extension but closed for modification. Previously, adding a new repository required modifying existing service classes, which violated this principle. To resolve this, I introduced two interface files—`ProductRepositoryInterface.java` and `CarRepositoryInterface.java`—which act as abstractions for their respective repositories. Now, `CarRepository.java` and `ProductRepository.java` both implement their corresponding interfaces. With this approach, new repositories can extend existing interfaces without modifying existing code. 

#### **Liskov Substitution Principle (LSP)**
The LSP states that objects of a superclass should be replaceable with objects of its subclasses without affecting the correctness of the program. To support this principle, I introduced a new interface, `RepositoryInterface.java`, as a common base for `CarRepositoryInterface` and `ProductRepositoryInterface`. This ensures that all future repositories can seamlessly integrate without breaking existing functionality. I applied this structure across `CarREpositoryInterface.java`, `ProductRepositporyInterface.java`, `CarRepository.java`, and `ProductRepository.java`.

By applying these SOLID principles, I improved the project's modularity, scalability, and maintainability while aligning with industry best practices.

**Advantages of Applying SOLID principles**

1. Easier to scale and modify
```java
1. @Repository
public class ProductRepository implements ProductRepositoryInterface {

    private final List<Product> productData = new ArrayList<>();
    ...
```
Since the following code implements an Interface, it will be much easier to modify and edit. This is because
After applying SOLID Principles, if we need to add new functionality, OCP ensures that we don't have to modify existing classes.
SRP makes each class easier to understand and update without unintended side effects.

2. More Flexible Code Structure

```java
package com.example.eshop.repository;

import java.util.List;

public interface RepositoryInterface<T> {
    T create(T item);
    List<T> findAll();
    T findById(String id);
    T update(T item);
    void deleteById(String id);
}
```
LSP ensures that all repositories behave consistently, allowing us to swap implementations seamlessly.

3. Improved Readability and Maintainability

```java
@Controller
@RequestMapping("/car")
public class CarController {

    private final CarService carservice;

    public CarController(CarService carService) {
        this.carservice = carService;
    }
    ...
```

SRP ensures that each class has a clear and well-defined responsibility, making the codebase easier to navigate

**Disadvantages of Not Applying SOLID Principles**

1. Harder to extend

This is an OCP violation. Without OCP, adding a new repository requires modifying existing service.

2. Inconsistent and Error-Prone Code

This is an LSP Violation. Lets say there was a function that returns different types across repositories, then the service layer must handle each case separately. This leads to a lot of bugs and inconsistencies.

3. Code becomes harder to maintain

If we wanted to add a new controller, it would be difficult as we dont extend it directly towards one controller.