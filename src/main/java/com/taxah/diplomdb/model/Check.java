package com.taxah.diplomdb.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "my_check")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Check {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "session_id")
    @ToString.Exclude
    private Session session;

    private String name;

    @OneToMany(mappedBy = "check",cascade = CascadeType.ALL)
    private List<ProductUsing> productUsingList;

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Session getSession() {
//        return session;
//    }
//
//    public void setSession(Session session) {
//        this.session = session;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public List<ProductUsing> getProductUsingList() {
//        return productUsingList;
//    }
//
//    public void setProductUsingList(List<ProductUsing> productUsingList) {
//        this.productUsingList = productUsingList;
//    }
//
//    @Override
//    public String toString() {
//        return "Check{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", productUsingList=" + productUsingList +
//                '}';
//    }
}
