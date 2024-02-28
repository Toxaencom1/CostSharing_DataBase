package com.taxah.diplomdb.repository;

import com.taxah.diplomdb.model.TempUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempUserRepository extends JpaRepository<TempUser,Long> {
}
