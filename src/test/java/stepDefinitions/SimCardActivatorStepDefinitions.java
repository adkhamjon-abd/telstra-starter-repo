package stepDefinitions;

import au.com.telstra.simcardactivator.SimCardActivator;
import au.com.telstra.simcardactivator.model.SimActivationRecord;
import io.cucumber.java.en.*;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ContextConfiguration;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = SimCardActivator.class, loader = SpringBootContextLoader.class)
public class SimCardActivatorStepDefinitions {
    @Autowired
    private TestRestTemplate restTemplate;

    private String iccid;
    private String customerEmail;
    private boolean activationResult;

    @Given("a SIM card with ICCID {string} and customer email {string}")
    public void givenSimCardWithIccidAndCustomerEmail(String iccid, String email) {
        this.iccid = iccid;
        this.customerEmail = email;
    }

    @When("the activation request is submitted")
    public void whenActivationRequestIsSubmitted() {
        var requestPayload = Map.of("iccid", iccid, "customerEmail", customerEmail);
        var response = restTemplate.postForObject("http://localhost:8080/sim/activate", requestPayload, String.class);
        activationResult = response.contains("successful");
    }
    @Then("the activation should {string}")
    public void thenActivationShouldSucceedOrFail(String expectedResult) {
        if (expectedResult.equals("succeed")) {
            assertTrue(activationResult, "Activation should succeed");
        } else if (expectedResult.equals("fail")) {
            assertFalse(activationResult, "Activation should fail");
        }
    }

    @Then("the database should have a record with ICCID {string}, customer email {string}, and active status {string}")
    public void thenDatabaseShouldHaveRecord(String expectedIccid, String expectedEmail, String expectedStatus) {
        long id = expectedIccid.equals("1255789453849037777") ? 1 : 2;
        var record = restTemplate.getForObject("http://localhost:8080/sim/activation/" + id, SimActivationRecord.class);

        assertNotNull(record, "Record should exist in the database");
        assertEquals(expectedIccid, record.getIccid());
        assertEquals(expectedEmail, record.getCustomerEmail());
        assertEquals(Boolean.parseBoolean(expectedStatus), record.isActive());
    }
}