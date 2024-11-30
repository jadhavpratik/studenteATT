package com.javaass.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products") // Explicit table name for clarity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment primary key
    private Integer pid;

    private String productName;

    private Integer qty;

    private Integer price;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}





