package com.example.eshop.controller;

import com.example.eshop.model.Product;
import com.example.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private Model model;

    private Product validProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validProduct = new Product();
        validProduct.setProductId("test-id");
        validProduct.setProductName("Test Product");
        validProduct.setProductQuantity(10);
    }

    @Test
    void testCreateProductPage() {
        String viewName = productController.createProductPage(model);
        assertEquals("createProduct", viewName, "Should return createProduct view");
        verify(model).addAttribute(eq("product"), any(Product.class));
    }

    @Test
    void testCreateProductPostWithBindingErrors() {
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = productController.createProductPost(validProduct, bindingResult, model);
        assertEquals("createProduct", viewName, "Should return to createProduct if there are validation errors");
        verify(productService, never()).create(any(Product.class));
    }

    @Test
    void testCreateProductPostNoBindingErrors() {
        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = productController.createProductPost(validProduct, bindingResult, model);
        assertEquals("redirect:list", viewName, "Should redirect to list when no validation errors");
        verify(productService).create(validProduct);
    }

    @Test
    void testProductListPage() {
        List<Product> productList = new ArrayList<>();
        productList.add(validProduct);
        when(productService.findAll()).thenReturn(productList);

        String viewName = productController.productListPage(model);
        assertEquals("productList", viewName, "Should display productList page");
        verify(productService).findAll();
        verify(model).addAttribute(eq("products"), eq(productList));
    }

    @Test
    void testEditProductPageProductFound() {
        when(productService.findById("test-id")).thenReturn(validProduct);
        String viewName = productController.editProductPage("test-id", model);
        assertEquals("editProduct", viewName, "Should return editProduct page if the product is found");
        verify(model).addAttribute(eq("product"), eq(validProduct));
    }

    @Test
    void testEditProductPageProductNotFound() {
        when(productService.findById("missing-id")).thenReturn(null);
        String viewName = productController.editProductPage("missing-id", model);
        assertEquals("redirect:/product/list", viewName, "Should redirect to product list if product not found");
    }

    @Test
    void testEditProductPostWithErrors() {
        when(bindingResult.hasErrors()).thenReturn(true);
        String viewName = productController.editProductPost(validProduct, bindingResult);
        assertEquals("editProduct", viewName, "Should stay on editProduct page if validation fails");
        verify(productService, never()).update(any(Product.class));
    }

    @Test
    void testEditProductPostNoErrors() {
        when(bindingResult.hasErrors()).thenReturn(false);
        String viewName = productController.editProductPost(validProduct, bindingResult);
        assertEquals("redirect:/product/list", viewName, "Should redirect if edit is successful");
        verify(productService).update(validProduct);
    }

    @Test
    void testDeleteProduct() {
        String viewName = productController.deleteProduct("test-id");
        assertEquals("redirect:/product/list", viewName, "Should always redirect after deletion");
        verify(productService).deleteById("test-id");
    }
}