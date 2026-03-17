package com.crowdfund.backend.campaign.dto;

public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;

    // ✅ MAIN constructor
    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // ✅ FIXED: response constructor
    public ApiResponse(boolean success, T data) {
        this.success = success;
        this.message = "Request successful";
        this.data = data;
    }

    // ✅ FIXED: message constructor
    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.data = null;
    }

    // =========================
    // STATIC METHODS
    // =========================
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "Request successful", data);
    }

    public static <T> ApiResponse<T> failure(String message) {
        return new ApiResponse<>(false, message, null);
    }

    // =========================
    // GETTERS
    // =========================
    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}