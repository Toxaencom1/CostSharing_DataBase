package com.taxah.diplomdb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUsingUserId implements Serializable {

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @Column(name = "product_using_id", insertable = false, updatable = false)
    private Long productUsingId;
}
