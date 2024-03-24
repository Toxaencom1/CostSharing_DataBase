package com.taxah.diplomdb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Class that represents a composite primary key for ProductUsingUser.
 * <p>
 * Fields:
 * - userId - user id
 * - productUsingId - product using id
 * <p>
 * Constructors:
 * - ProductUsingUserId(Long userId, Long productUsingId) - creates a composite primary key with the specified user id and product using id
 * <p>
 * Getters and setters for all fields.
 */
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
