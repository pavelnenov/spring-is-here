package com.pavel.spring.service;

public record DoctorSearchCriteria(String lastName, String email, String specialization, Integer hospitalId) {
    public boolean isEmpty() {
        return lastName == null && email == null && specialization == null && hospitalId == null;
    }
}
