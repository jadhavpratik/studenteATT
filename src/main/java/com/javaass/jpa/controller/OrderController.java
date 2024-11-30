package com.javaass.jpa.controller;

import com.javaass.jpa.dto.OrderRequest;
import com.javaass.jpa.dto.OrderResponse;
import com.javaass.jpa.entity.Customer;
import com.javaass.jpa.entity.Product;
import com.javaass.jpa.repository.CustomerRepository;
import com.javaass.jpa.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;





    @PostMapping("/place-order")
    public ResponseEntity<Customer> placeOrder(@RequestBody OrderRequest request) {
        try {
            if (request == null || request.getCustomer() == null) {
                throw new IllegalArgumentException("OrderRequest or Customer cannot be null.");
            }

            Customer customer = request.getCustomer();

            // Ensure products are linked to the customer
            if (customer.getProducts() != null) {
                for (Product product : customer.getProducts()) {
                    product.setCustomer(customer);
                }
            }

            // Save the customer and cascade to products
            Customer savedCustomer = customerRepository.save(customer);

            return ResponseEntity.ok(savedCustomer);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // 400 Bad Request for validation errors
        } catch (Exception e) {
            e.printStackTrace(); // Log the error for debugging
            return ResponseEntity.status(500).body(null); // 500 Internal Server Error for other exceptions
        }
    }


    @GetMapping("/find-all-orders")
    public ResponseEntity<List<Customer>> findAllOrders() {
        try {
            List<Customer> customers = customerRepository.findAll();
            return ResponseEntity.ok(customers);
        } catch (Exception e) {
            e.printStackTrace(); // Log the error for debugging
            return ResponseEntity.status(500).body(null); // 500 Internal Server Error
        }
    }




    @GetMapping("/get-info")
    public ResponseEntity<List<OrderResponse>> getJoinInformation() {
        try {
            List<OrderResponse> orderResponses = customerRepository.getJoinInformation();
            return ResponseEntity.ok(orderResponses);
        } catch (Exception e) {
            e.printStackTrace(); // Log the error for debugging
            return ResponseEntity.status(500).body(null); // 500 Internal Server Error
        }
    }





    @PutMapping("/update-product/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Integer id, @RequestBody Product updatedProduct) {
        try {
            // Log the ID for debugging
            System.out.println("Looking for product with ID: " + id);

            // Find the product by ID
            Product existingProduct = productRepository.findById(id).orElse(null);

            if (existingProduct == null) {
                // If product is not found, return 404 Not Found
                return ResponseEntity.status(404).body("Product not found with ID: " + id);
            }

            // Update fields of the existing product
            existingProduct.setProductName(updatedProduct.getProductName());
            existingProduct.setQty(updatedProduct.getQty());
            existingProduct.setPrice(updatedProduct.getPrice());

            // Save the updated product to the database
            Product savedProduct = productRepository.save(existingProduct);

            // Return success response with updated product
            return ResponseEntity.ok(savedProduct);

        } catch (Exception e) {
            // Log unexpected errors
            e.printStackTrace();

            // Return 500 Internal Server Error with a generic message
            return ResponseEntity.status(500).body("An unexpected error occurred while updating the product.");
        }
    }








    // Delete customer by ID
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<String> deleteCustomerById(@PathVariable Integer id) {
        // Check if customer exists
        if (customerRepository.existsById(id)) {
            // Delete the customer
            customerRepository.deleteById(id);
            return ResponseEntity.ok("Customer with ID " + id + " deleted successfully.");
        } else {
            // If customer not found, return 404
            return ResponseEntity.status(404).body("Customer with ID " + id + " not found.");
        }
    }




    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Integer id) {
        // Check if product exists
        if (productRepository.existsById(id)) {
            // Delete the product
            productRepository.deleteById(id);
            return ResponseEntity.ok("Product with ID " + id + " deleted successfully.");
        } else {
            // If product not found, return 404
            return ResponseEntity.status(404).body("Product with ID " + id + " not found.");
        }
    }

}





