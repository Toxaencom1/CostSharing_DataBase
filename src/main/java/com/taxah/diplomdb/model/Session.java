package com.taxah.diplomdb.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.taxah.diplomdb.model.abstractClasses.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "my_session")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "admin_id")
    private Long adminId;
    @OneToMany
    @JoinColumn(name = "session_id")
    private List<TempUser> membersList = new ArrayList<>();
    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL)
    private List<Check> checkList = new ArrayList<>();
    @Column(name = "is_closed")
    private boolean isClosed;

}
