package com.pavel.spring.service;

import com.pavel.spring.entity.Doctor;
import com.pavel.spring.repository.DoctorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private EntityManager em;

    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public Doctor findByEmail(String email) {
        return doctorRepository.findByEmail(email);
    }

    public List<Doctor> findByCriteria(DoctorSearchCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Doctor> cq = cb.createQuery(Doctor.class);

        Root<Doctor> doctorRoot = cq.from(Doctor.class);
        List<Predicate> predicates = new ArrayList<>();

        if (criteria.email() != null) {
            predicates.add(cb.equal(doctorRoot.get("email"), criteria.email()));
        }
        if (criteria.specialization() != null) {
            predicates.add(cb.equal(doctorRoot.get("specialization"), criteria.specialization()));
        }
        if (criteria.hospitalId() != null) {
            predicates.add(cb.equal(doctorRoot.get("hospitalId"), criteria.hospitalId()));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Doctor> query = em.createQuery(cq);
        return query.getResultList();
    }
}
