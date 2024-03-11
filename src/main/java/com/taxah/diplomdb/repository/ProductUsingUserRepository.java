package com.taxah.diplomdb.repository;

import com.taxah.diplomdb.model.ProductUsingUser;
import com.taxah.diplomdb.model.ProductUsingUserId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductUsingUserRepository extends JpaRepository<ProductUsingUser, ProductUsingUserId> {

    @Transactional
    void deleteByTempUserIdAndProductUsingId(Long productUsingId, Long tempUserId);
}
