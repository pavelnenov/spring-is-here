package com.pavel.spring.controller;

import com.pavel.spring.dto.DoctorDTO;
import com.pavel.spring.entity.Doctor;
import com.pavel.spring.service.DoctorSearchCriteria;
import com.pavel.spring.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorsController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping
    public ResponseEntity<List<DoctorDTO>> findDoctors(
            @RequestParam(value = "email", required = false) @Nullable String email,
            @RequestParam(value = "specialization", required = false) @Nullable String specialization,
            @RequestParam(value = "hospitalId", required = false) @Nullable Integer hospitalId,
            @RequestParam(value = "lastName", required = false) @Nullable String lastName
    ) {
        var searchCriteria = new DoctorSearchCriteria(lastName, email, specialization, hospitalId);
        if (searchCriteria.isEmpty()) {
            List<DoctorDTO> response = doctorService.findAll().stream().map(DoctorDTO::fromEntity).toList();
            return ResponseEntity.ok(response);
        }

        List<Doctor> doctors = doctorService.findByCriteria(searchCriteria);
        if (doctors.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(doctors.stream().map(DoctorDTO::fromEntity).toList());
    }
}
