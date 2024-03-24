package com.taxah.diplomdb.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * Class that represents a product using.
 * <p>
 * Fields:
 * - id - product using id
 * - check - check to which the product belongs
 * - productName - product name
 * - cost - product cost
 * - users - list of users who used the product
 * <p>
 * Constructors:
 * - ProductUsing(Check check, String productName, Double cost, List<TempUser> users) - creates a product using with the specified check, product name, cost, and list of users
 * <p>
 * Methods:
 * - addTempUser(TempUser tempUser) - add user to the list of users
 * <p>
 * Getters and setters for all fields.

 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_usings")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class ProductUsing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name = "check_id")
    @JsonIdentityReference(alwaysAsId = true)
    @ToString.Exclude
    private Check check;

    private String productName;

    private Double cost;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinTable(
            name = "Product_Using_User",
            joinColumns = @JoinColumn(name = "product_using_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<TempUser> users;

    /**
     * Adds a user to the list of users.
     *
     * @param tempUser - user to add
     */
    public void addTempUser(TempUser tempUser){
        users.add(tempUser);
    }
}