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
@Table(name = "Session")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long adminId;
    @OneToMany
    private List<User> membersList;
    @OneToMany
    private List<PayFact> payFact;
    @OneToMany
    private List<Check> checkList;
    private boolean isClosed;

}
