package com.example.fintrail.repositories;

import com.example.fintrail.entities.VirtualAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<VirtualAccount, Long> {
}