//TODO доделать чтобы можно было считать
package com.taxah.diplomdb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
