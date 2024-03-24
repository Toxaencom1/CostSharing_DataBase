package com.taxah.diplomdb.model;


import com.fasterxml.jackson.annotation.JsonIdentityReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * Class that represents a pay fact.
 * <p>
 * Fields:
 * - id - pay fact id
 * - check - check for which the pay fact is created
 * - tempUser - user who paid
 * - amount - amount of money
 * <p>
 * Constructors:
 * - PayFact(Check check, TempUser tempUser, double amount) - creates a pay fact with the specified check, user, and amount
 * <p>
 * Getters and setters for all fields.

 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pay_facts")
public class PayFact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "payFact")
    @JsonIdentityReference(alwaysAsId = true)
    @ToString.Exclude
    private Check check;

    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE,CascadeType.DETACH})
    @JoinColumn(name = "temp_user_id")
    @JsonIdentityReference(alwaysAsId = true)
    private TempUser tempUser;

    private double amount;
}
