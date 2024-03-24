package com.taxah.diplomdb.model;

import com.taxah.diplomdb.model.abstractClasses.Account;
import jakarta.persistence.*;
import lombok.*;

/**
 * Class that represents a user for future app updates.
 * <p>
 * Fields:
 * - id - user id
 * - firstname - user firstname
 * - lastname - user lastname
 * <p>
 * Constructors:
 * - User(String firstname, String lastname) - creates a user with the specified firstname and lastname
 * <p>
 * Getters and setters for all fields.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "my_users")
public class User extends Account {
    public User(String firstname, String lastname) {
        super(firstname,lastname);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
