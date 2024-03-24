package com.taxah.diplomdb.repository;

import com.taxah.diplomdb.model.ProductUsingUser;
import com.taxah.diplomdb.model.ProductUsingUserId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface for working with the product_using_users table in the database.
 */
public interface ProductUsingUserRepository extends JpaRepository<ProductUsingUser, ProductUsingUserId> {

    /**
     * Deletes a record from the product_using_users table by the productUsingId and tempUserId fields.
     *
     * @param productUsingId - productUsingId field
     * @param tempUserId - tempUserId field
     */
    @Transactional
    void deleteByTempUserIdAndProductUsingId(Long productUsingId, Long tempUserId);
}
