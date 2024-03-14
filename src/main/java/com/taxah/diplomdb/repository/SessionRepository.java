package com.taxah.diplomdb.repository;


import com.taxah.diplomdb.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByNameContainingIgnoreCase(String name);
}
