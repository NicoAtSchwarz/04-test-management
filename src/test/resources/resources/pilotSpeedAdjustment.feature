Feature: Speed Adjustment and Tank Switch During Flight

  Scenario: Check if the pilot can correctly adjust speed during flight and if the tank switch happens correctly as the airplane changes speeds
    Given the airplane has two wing tanks with a capacity of 37000 and a center tank with a capacity of 64000
    And both engines get started
    And the airplane is flying at an initial speed of 600

    When the pilot flies for 3 hours at 600 by turning the speed knob
    Then the fuel consumption should be 6600 per hour
    And the airplane should fly at 600

    When the pilot flies for 2 hours at 550 by turning the speed knob
    Then the fuel consumption should be 6050 per hour
    And the airplane should fly at 550

    When the pilot flies for 1 hours at 575 by turning the speed knob
    Then the fuel consumption should be 6325 per hour
    And the airplane should fly at 575
    And the center tank should be used
