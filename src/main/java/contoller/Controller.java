package contoller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sim-card")
public class Controller {

    @Autowired
    private SimActivationService simActivationService;
    @PostMapping("/activate")
    public ResponseEntity<String> activateSim(@RequestBody SimActivationRequest request){
        boolean isActivated =simActivationService.activateSim(request);

        if (isActivated){
            System.out.println("Successful activation");
            return ResponseEntity.ok("Activation was successful")
        } else {
            System.out.println("Activation failed");
            return ResponseEntity.status(500).body("Activation failed");
        }
    }
}
