Feature: Airplane Fuel Consumption
    Scenario: Flying for 10 hours at various speeds and verifying fuel consumption and tank switch
      Given the airplane has a left and a right wing tank with a capacity of 37000 each and a center tank with a capacity of 64000 (AirplaneFuelConsumption)
      And each wing tank belows to one jet engine
      And the airplane is flying for 10 hours

      When the speed for hour 1 is 559 mph
      Then the fuel consumption for hour 1 should be 6149

      When the speed for hour 2 is 562 mph
      Then the fuel consumption for hour 2 should be 6182

      When the speed for hour 3 is 562 mph
      Then the fuel consumption for hour 3 should be 6182

      When the speed for hour 4 is 560 mph
      Then the fuel consumption for hour 4 should be 6160

      When the speed for hour 5 is 562 mph
      Then the fuel consumption for hour 5 should be 6182

      When the speed for hour 6 is 565 mph
      Then the fuel consumption for hour 6 should be 6215

      When the speed for hour 7 is 570 mph
      Then the fuel consumption for hour 7 should be 6270

      When the speed for hour 8 is 575 mph
      Then the fuel consumption for hour 8 should be 6325

      When the speed for hour 9 is 550 mph
      Then the fuel consumption for hour 9 should be 6050

      When the speed for hour 10 is 540 mph
      Then the fuel consumption for hour 10 should be 5940

      Then the fuel in the center tank should be less than at departure