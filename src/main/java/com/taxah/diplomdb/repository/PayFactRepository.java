package com.taxah.diplomdb.repository;

import com.taxah.diplomdb.model.PayFact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayFactRepository extends JpaRepository<PayFact, Long> {
}
