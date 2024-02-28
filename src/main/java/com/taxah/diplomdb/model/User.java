package com.taxah.diplomdb.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.taxah.diplomdb.model.abstractClasses.Account;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "my_user")
public class User extends Account {
    public User(String firstname, String lastname) {
        super(firstname,lastname);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
