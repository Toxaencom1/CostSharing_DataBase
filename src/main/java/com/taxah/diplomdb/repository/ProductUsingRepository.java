package com.taxah.diplomdb.repository;

import com.taxah.diplomdb.model.ProductUsing;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface for working with the product_usings table in the database.
 */
public interface ProductUsingRepository extends JpaRepository<ProductUsing,Long> {
}
