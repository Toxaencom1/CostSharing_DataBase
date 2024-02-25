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
@Table(name = "my_session")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "admin_id")
    private Long adminId;
    @OneToMany
    private List<User> membersList;
    @OneToMany
    private List<PayFact> payFact;
    @OneToMany
    private List<Check> checkList;
    @Column(name = "is_closed")
    private boolean isClosed;
}
