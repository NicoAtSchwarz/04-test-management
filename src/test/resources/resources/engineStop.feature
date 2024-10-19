Feature: Engine Stop
    Scenario: Starting both engines and verifying fuel consumption of working engine after stopping one engine
      Given the airplane has a left and a right wing tank with a capacity of 37000 each and a center tank with a capacity of 64000 (EngineStop)
      And both engines are started
      And the speed is increased to 600 (EngineStop)
      And the airplane has been flying for 6 hours (EngineStop)

      When the pilot stops one of the engines
      Then the running engine should consume fuel