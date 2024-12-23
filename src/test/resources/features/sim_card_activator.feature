Feature: SIM Card Activation
  As a user of the SIM Card Activation microservice,
  I want to activate a SIM card and query its status,
  So that I can ensure the activation process works as expected.

  Scenario: Successful SIM card activation
    Given the SIM card Actuator service is running
    And the SIM card ICCID is "1255789453849037777"
    When I submit an activation request
    Then the response status should be "SUCCESS"
    And the activation record in the database should have ID 1

  Scenario: Failed SIM card activation
    Given the SIM card Actuator service is running
    And the SIM card ICCID is "8944500102198304826"
    When I submit an activation request
    Then the response status should be "FAILURE"
    And the activation record in the database should have ID 2
