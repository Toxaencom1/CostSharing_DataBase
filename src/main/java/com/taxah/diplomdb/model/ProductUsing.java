package com.taxah.diplomdb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_using")
public class ProductUsing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long checkId;

    private String productName;

    private Double cost;

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(
            name = "Product_Using_User",
            joinColumns = @JoinColumn(name = "product_using_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;
}
