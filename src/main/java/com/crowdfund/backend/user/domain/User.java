package com.crowdfund.backend.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity    // @Entity tells JPA that this class should map to a database table.
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_user_email", columnNames = "email")
        },
        indexes = {
                @Index(name = "idx_user_role", columnList = "role"),
                @Index(name = "idx_user_at", columnList = "created_at")
        }
)
public class User {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;

    @Column(nullable = false)   // Email is unique because login is based on email.
    private String email;

    @JsonIgnore
    private String passwordHash;   //never store raw password only hashed password

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User(){

    }

    @PrePersist      // Automatically set timestamp before insert.
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate          // Automatically update timestamp before update.
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}






/*
 --------------------------------------------------------
 CLASS SUMMARY:
 --------------------------------------------------------
 Represents system identity.
 Supports:
- Campaign creation
- Donation ownership
- Role-based access

UUID ensures safe exposure in APIs.

 Indexed for:
 - Login (email unique)
 - Role filtering (admin queries)
 --------------------------------------------------------
*/


/*
 * UUID used instead of Long for:
 * - Global uniqueness
 * - API safety
 * - Future microservice compatibility


 EnumType.STRING stores readable values like 'ADMIN'
 instead of numeric ordinal values
 */

