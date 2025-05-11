package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.exception.EmailAlreadyExistsException;
import com.pm.patientservice.exception.PatientNotFoundException;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream()
                .map(PatientMapper::toDTO).toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO request) {
        if (patientRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("A patient with this email " +
                    "already exists " + request.getEmail());
        }

        Patient newPatient = patientRepository.save(
                PatientMapper.toModel(request));
        return PatientMapper.toDTO(newPatient);
    }

    public PatientResponseDTO updatePatient(
            UUID id, PatientRequestDTO request) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(
                        () -> new PatientNotFoundException("Patient not found with ID: " + id));

        if (patientRepository.existsByEmailAndIdNot(request.getEmail(), id)) {
            throw new EmailAlreadyExistsException(
                    "A patient with this email already exists "
                            + request.getEmail());
        }

        patient.setName(request.getName());
        patient.setAddress(request.getAddress());
        patient.setEmail(request.getEmail());
        patient.setDateOfBirth(LocalDate.parse(request.getDateOfBirth()));
        return PatientMapper.toDTO(patientRepository.save(patient));
    }

    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }

}
