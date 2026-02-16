package com.crowdfund.backend.user.domain;

public enum Role {
    USER,
    ADMIN
}









/*
 * Role represents the authorization level of a user.
 * Instead of creating separate tables like AdminUser, DonorUser,
 * we use role-based access control (RBAC).
 *
 * This allows a single identity table (User) to behave differently
 * depending on role.
 */

/*
 Summary:
 This enum supports RBAC.
 It helps Spring Security later to restrict endpoints.
 */