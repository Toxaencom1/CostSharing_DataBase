package com.taxah.diplomdb.repository;

import com.taxah.diplomdb.model.Check;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface for working with the check table in the database.
 */
public interface CheckRepository extends JpaRepository<Check, Long>{
}
