package com.crowdfund.backend.user.repository;

import com.crowdfund.backend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


/*
 * JpaRepository provides:
 * - CRUD
 * - Pagination
 * - Sorting
 */
public interface UserRepository extends JpaRepository<User, UUID> {

    // Spring auto-generates query from method name.
    Optional<User> findByEmail(String email);

}







/*
-------------------------------------------------------
SUMMARY:
Data access layer for User.
Used for authentication & ownership lookup.
-------------------------------------------------------
*/