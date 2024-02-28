package com.taxah.diplomdb.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.taxah.diplomdb.model.abstractClasses.Account;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "temp_user")
public class TempUser extends Account {
    @Column(name = "session_id")
    private Long sessionId;

    @ToString.Exclude
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinTable(
            name = "Product_Using_User",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_using_id")
    )
    private List<ProductUsing> productUsingList;


    public TempUser(Long sessionId, String firstName, String lastName) {
        super(firstName,lastName);
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return super.toString()+
                "sessionId=" + sessionId +
                "}";
    }

//    public String getFirstname() {
//        return super.getFirstname();
//    }
//
//    public void setFirstname(String firstname) {
//        super.setFirstname(firstname);
//    }
//    public String getLastname() {
//        return super.getLastname();
//    }
//
//    public void setLastname(String firstname) {
//        super.setLastname(firstname);
//    }
}
