//TODO доделать чтобы можно было считать
package com.taxah.diplomdb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class that represents a many-to-many relation entity to ProductUsing - TempUser.
 * <p>
 * Fields:
 * - id - product using user id
 * - productUsing - product using
 * - tempUser - user who used the product
 * <p>
 * Constructors:
 * - ProductUsingUser(ProductUsingUserId id, ProductUsing productUsing, TempUser tempUser) - creates a product using user with the specified id, product using, and user
 * <p>
 * Getters and setters for all fields.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Product_Using_User")
public class ProductUsingUser {

    @EmbeddedId
    private ProductUsingUserId id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_using_id")
    private ProductUsing productUsing;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.DETACH,CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private TempUser tempUser;
}
