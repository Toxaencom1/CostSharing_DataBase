package com.taxah.diplomdb.model.abstractClasses;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Abstract class that represents an account.
 * <p>
 * Fields:
 * - id - account id
 * - firstname - account firstname
 * - lastname - account lastname
 * <p>
 * Constructors:
 * - Account(String firstname, String lastname) - creates an account with a firstname and lastname
 * <p>
 * Getters and setters for all fields.
 */
@Data
@NoArgsConstructor
@MappedSuperclass
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;

    protected Account(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", ";
    }
}
