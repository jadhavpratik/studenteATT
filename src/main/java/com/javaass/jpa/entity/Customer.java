package com.javaass.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Added explicit strategy for better database compatibility
    private Integer id;

    private String name;
    private String email;
    private String gender;

    @OneToMany(targetEntity = Product.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private List<Product> products;
}






