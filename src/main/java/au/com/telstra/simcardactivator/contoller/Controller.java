package au.com.telstra.simcardactivator.contoller;
import au.com.telstra.simcardactivator.dto.SimActivationRequest;
import au.com.telstra.simcardactivator.model.SimActivationRecord;
import au.com.telstra.simcardactivator.service.SimActivationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/sim")
public class Controller {
    Logger logger = Logger.getLogger(getClass().getName());

    private final SimActivationService simActivationService;

    @Autowired
    public Controller(SimActivationService simActivationService){
        this.simActivationService = simActivationService;
    }
    @PostMapping("/activate")
    public ResponseEntity<String> activateSim(@RequestBody SimActivationRequest request){
        boolean isActivated =simActivationService.activateSim(request);

        if (isActivated){
            logger.info("Successful activation");
            return ResponseEntity.ok("Activation was successful");
        } else {
            logger.info("Activation failed");
            return ResponseEntity.status(500).body("Activation failed");
        }
    }

    @GetMapping("/activate/{simCardId}")
    public ResponseEntity<SimActivationRecord> getSimActivationRecord (@PathVariable long simCardId){
        SimActivationRecord simActivationRecord =  simActivationService.getSimActivationRecordById(simCardId);
        if (simActivationRecord == null){
            return ResponseEntity.notFound().build();
        }
        logger.info("The record is found");
        return ResponseEntity.ok(simActivationRecord);
    }
}
