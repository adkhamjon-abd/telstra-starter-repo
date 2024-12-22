package au.com.telstra.simcardactivator.contoller;
import au.com.telstra.simcardactivator.dto.SimActivationRequest;
import au.com.telstra.simcardactivator.model.SimActivationRecord;
import au.com.telstra.simcardactivator.service.SimActivationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sim")
public class Controller {

    @Autowired
    private SimActivationService simActivationService;
    @PostMapping("/activate")
    public ResponseEntity<String> activateSim(@RequestBody SimActivationRequest request){
        boolean isActivated =simActivationService.activateSim(request);

        if (isActivated){
            System.out.println("Successful activation");
            return ResponseEntity.ok("Activation was successful");
        } else {
            System.out.println("Activation failed");
            return ResponseEntity.status(500).body("Activation failed");
        }
    }

    @GetMapping("/activate/{simCardId}")
    public ResponseEntity<SimActivationRecord> getSimActivationRecord (@PathVariable long simCardId){
        SimActivationRecord simActivationRecord =  simActivationService.getSimActivationRecordById(simCardId);
        if (simActivationRecord == null){
            return ResponseEntity.notFound().build();
        }
        System.out.println("The record is found");
        return ResponseEntity.ok(simActivationRecord);
    }
}
