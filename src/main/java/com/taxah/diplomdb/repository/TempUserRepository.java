package com.taxah.diplomdb.repository;

import com.taxah.diplomdb.model.TempUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface for working with the temp_users table in the database.
 */
public interface TempUserRepository extends JpaRepository<TempUser,Long> {
}
