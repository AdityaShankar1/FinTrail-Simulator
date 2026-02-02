package com.example.fintrail.repositories;

import com.example.fintrail.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Spring automatically gives us findById, save, delete, etc.
}