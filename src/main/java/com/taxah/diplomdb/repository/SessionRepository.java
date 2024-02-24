package com.taxah.diplomdb.repository;


import com.taxah.diplomdb.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Integer> {
}
