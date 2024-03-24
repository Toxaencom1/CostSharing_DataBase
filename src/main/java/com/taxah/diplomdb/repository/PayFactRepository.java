package com.taxah.diplomdb.repository;

import com.taxah.diplomdb.model.PayFact;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface for working with the pay_facts table in the database.
 */
public interface PayFactRepository extends JpaRepository<PayFact, Long> {
}
