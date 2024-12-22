package au.com.telstra.simcardactivator.service;


import au.com.telstra.simcardactivator.dto.ActuatorResponse;
import au.com.telstra.simcardactivator.dto.SimActivationRequest;
import au.com.telstra.simcardactivator.model.SimActivationRecord;
import au.com.telstra.simcardactivator.repo.SimActivationRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class SimActivationService {
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String ACTUATOR_URL = "http://localhost:8444/actuate";
    @Autowired
    private SimActivationRecordRepository repo;

    public boolean activateSim(SimActivationRequest request) {
        var actuatorPayload = Map.of("iccid", request.getIccid());

        ActuatorResponse actuatorResponse = restTemplate.postForObject(
                ACTUATOR_URL, actuatorPayload, ActuatorResponse.class);

        boolean isActive = actuatorResponse != null && actuatorResponse.isSuccess();
        SimActivationRecord record = new SimActivationRecord();
        record.setIccid(request.getIccid());
        record.setCustomerEmail(request.getCustomerEmail());
        record.setActive(isActive);
        repo.save(record);
        return isActive;
    }
}
