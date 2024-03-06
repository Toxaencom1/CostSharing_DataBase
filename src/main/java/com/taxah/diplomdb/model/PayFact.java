package com.taxah.diplomdb.model;


import com.fasterxml.jackson.annotation.JsonIdentityReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pay_fact")
public class PayFact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "payFact")
    @JsonIdentityReference(alwaysAsId = true)
    private Check check;

    @ManyToOne
    @JoinColumn(name = "user_data")
    @JsonIdentityReference(alwaysAsId = true)
    private TempUser tempUser;

    private double amount;
}
