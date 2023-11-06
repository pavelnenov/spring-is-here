package com.pavel.spring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "doctors")
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "doctor_id")
        Long doctorId;
        @Column(name = "first_name")
        String firstName;
        @Column(name = "last_name")
        String lastName;
        @Column(name = "specialization")
        String specialization;
        @Column(name = "hospital_id")
        Integer hospitalId;
        @Column(name = "email")
        String email;
        @Column(name = "phone")
        String phone;
}
