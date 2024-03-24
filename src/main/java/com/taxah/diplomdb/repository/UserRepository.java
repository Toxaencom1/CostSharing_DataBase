package com.taxah.diplomdb.repository;

import com.taxah.diplomdb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface for working with the users table in the database.
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
