package com.taxah.diplomdb.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a session.
 * <p>
 * Fields:
 * - id - session id
 * - name - session name
 * - date - session date
 * - adminId - admin id
 * - membersList - list of members of the session
 * - checkList - list of checks in the session
 * - isClosed - indicates whether the session is closed
 * <p>
 * Constructors:
 * - Session(String name, Long adminId) - creates a session with the specified name and admin id
 * <p>
 * Getters and setters for all fields.

 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "my_sessions")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "date")
    private LocalDate date = LocalDate.now();
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
