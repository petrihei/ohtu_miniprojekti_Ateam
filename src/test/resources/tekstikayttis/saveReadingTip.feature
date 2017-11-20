Feature: A new reading tip can be saved

    Scenario: Saving a reading tip is successful
    Given command save tip is selected
    When title "testikirja" and description "teskikuvaus" are entered
    Then system will respond with message "Seuraavat tiedot tallennettu:"
