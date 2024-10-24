Feature: Fuel Emergency Simulation

  Scenario: Fuel emergency where the fuel in a wing tank is rapidly consumed and switches immediately to the center tank
    Given the airplane has a left and a right wing tank with a capacity of 37000 each and a center tank with a capacity of 64000 (FuelEmergency)
    And both engines get started (FuelEmergency)
    And the speed is increased to 600 (FuelEmergency)
    And the airplane has been flying for 4 hour (FuelEmergency)
    When the fuel in the left wing tank is consumed rapidly
    And the airplane is still flying for 1 hour (FuelEmergency)
    Then the airplane should switch to the center tank