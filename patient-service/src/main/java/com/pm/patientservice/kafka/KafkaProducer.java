package com.pm.patientservice.kafka;


import com.pm.patientservice.model.Patient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaProducer {
    private final KafkaTemplate<String, byte[]> kafkaTemplate; // shows that we send data with key string and value byte[]

    public void sendEvent(Patient patient) {
        PatientEvent event = PatientEvent.newBuilder()
                .setPatientId(patient.getId().toString())
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .setEventType("PATIENT_CREATED")
                .build();
        try {
            kafkaTemplate.send("patient", event.toByteArray()); // which topic and what data we're sending
        } catch (Exception e) {
            log.error("Error sending PatientCreated event: {}", event);
        }
    }

}
