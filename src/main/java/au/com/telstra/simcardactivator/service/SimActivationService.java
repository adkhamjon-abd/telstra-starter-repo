package au.com.telstra.simcardactivator.service;


import au.com.telstra.simcardactivator.dto.ActuatorResponse;
import au.com.telstra.simcardactivator.dto.SimActivationRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class SimActivationService {
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String ACTUATOR_URL = "http://localhost:8444/actuate";

    public boolean activateSim(SimActivationRequest request) {
        var actuatorPayload = Map.of("iccid", request.getIccid());

        ActuatorResponse actuatorResponse = restTemplate.postForObject(
                ACTUATOR_URL, actuatorPayload, ActuatorResponse.class);

        return actuatorResponse != null && actuatorResponse.isSuccess();
    }
}
