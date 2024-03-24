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
 * Class that represents a check.
 * <p>
 * Fields:
 * - id - check id
 * - session - session to which the check belongs
 * - payFact - pay fact for the check
 * - name - check name
 * - productUsingList - list of products in the check
 * <p>
 * Constructors:
 * - Check(Session session, PayFact payFact, String name, List<ProductUsing> productUsingList) - creates a check with the specified session, pay fact, name, and list of products
 * <p>
 * Methods:
 * - addProductUsing(ProductUsing pu) - add product to the check
 * <p>
 * Getters and setters for all fields.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "my_checks")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Check {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.REFRESH,CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE})
    @JoinColumn(name = "session_id")
    @JsonIdentityReference(alwaysAsId = true)
    @ToString.Exclude
    private Session session;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pay_fact")
    private PayFact payFact;

    private String name;

    @OneToMany(mappedBy = "check", cascade = CascadeType.ALL)
    private List<ProductUsing> productUsingList;

    /**
     * Add product to the check
     * @param pu - ProductUsing
     */
    public void addProductUsing(ProductUsing pu) {
        productUsingList.add(pu);
    }
}