package com.pavel.spring.dto;

import com.pavel.spring.entity.Doctor;

public record DoctorDTO(
        Long doctorId,
        String firstName,
        String lastName,
        String specialization,
        Integer hospitalId,
        String email,
        String phone
) {

    public static DoctorDTO fromEntity(Doctor doctor) {
        return new DoctorDTO(
                doctor.getDoctorId(),
                doctor.getFirstName(),
                doctor.getLastName(),
                doctor.getSpecialization(),
                doctor.getHospitalId(),
                doctor.getEmail(),
                doctor.getPhone()
        );
    }
}
