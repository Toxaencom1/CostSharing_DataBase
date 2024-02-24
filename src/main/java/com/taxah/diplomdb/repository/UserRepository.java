package com.taxah.diplomdb.repository;

import com.taxah.diplomdb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
