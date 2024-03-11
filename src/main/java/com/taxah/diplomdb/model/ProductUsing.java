package com.taxah.diplomdb.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_using")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class ProductUsing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name = "check_id")
    @JsonIdentityReference(alwaysAsId = true)
    @ToString.Exclude
    private Check check;

    private String productName;

    private Double cost;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinTable(
            name = "Product_Using_User",
            joinColumns = @JoinColumn(name = "product_using_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<TempUser> users;

    public void addTempUser(TempUser tempUser){
        users.add(tempUser);
    }
}