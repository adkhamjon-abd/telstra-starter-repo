package au.com.telstra.simcardactivator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimActivationRequest {
    private String iccid;
    private String customerEmail;
}
