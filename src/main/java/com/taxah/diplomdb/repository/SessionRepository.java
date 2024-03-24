package com.taxah.diplomdb.repository;


import com.taxah.diplomdb.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Interface for working with the sessions table in the database.
 */
public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByNameContainingIgnoreCase(String name);
}
