package com.taxah.diplomdb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Session")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int adminId;
    @OneToMany
    private List<User> membersList;
    @OneToMany
    private List<PayFact> payFact;
    @OneToMany
    private List<Check> checkList;
    private boolean isClosed;

}
