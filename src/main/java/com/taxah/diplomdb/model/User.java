package com.taxah.diplomdb.model;

import com.taxah.diplomdb.model.abstractClasses.Account;
import jakarta.persistence.*;
import lombok.*;


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
