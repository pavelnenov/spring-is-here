package com.pavel.spring.entity;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "doctors")
@NoArgsConstructor(force = true)
public record Doctor(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "doctor_id")
        Long doctorId,
        @Column(name = "first_name")
        String firstName,
        @Column(name = "last_name")
        String lastName,
        @Column(name = "specialization")
        String specialization,
        @Column(name = "hospital_id")
        Integer hospitalId,
        @Column(name = "email")
        String email,
        @Column(name = "phone")
        String phone

) {}
