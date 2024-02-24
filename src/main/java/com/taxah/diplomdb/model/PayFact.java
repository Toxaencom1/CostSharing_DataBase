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
@Table(name = "pay_fact")
public class PayFact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "session_id")
//    private Session session;

    @OneToMany
    private List<User> users;

    private double amount;
}
