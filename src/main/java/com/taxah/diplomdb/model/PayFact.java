package com.taxah.diplomdb.model;


import com.fasterxml.jackson.annotation.JsonIdentityReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pay_fact")
public class PayFact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "session_id")
    @JsonIdentityReference(alwaysAsId = true)
    @ToString.Exclude
    private Session session;

    @ManyToOne
    @JoinColumn(name = "user_data")
    @JsonIdentityReference(alwaysAsId = true)
    private TempUser tempUser;

    private double amount;
}
