package com.pm.patientservice.mapper;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.model.Patient;

import java.time.LocalDate;

public class PatientMapper {
    public static PatientResponseDTO toDTO(Patient patient) {
        return new PatientResponseDTO(
                patient.getId().toString(),
                patient.getName(),
                patient.getAddress(),
                patient.getEmail(),
                patient.getDateOfBirth().toString());
    }

    public static Patient toModel(PatientRequestDTO patientRequestDTO) {
        return new Patient(
                patientRequestDTO.getName(),
                patientRequestDTO.getEmail(),
                patientRequestDTO.getAddress(),
                LocalDate.parse(patientRequestDTO.getDateOfBirth()),
                LocalDate.parse(patientRequestDTO.getRegisteredDate()));
    }

}
