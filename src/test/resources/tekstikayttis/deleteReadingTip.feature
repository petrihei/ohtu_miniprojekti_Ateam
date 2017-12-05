Feature: A reading tip can be deleted

    Scenario: A reading tip with correct id is deleted
    Given command delete tip is selected
    When existing tip is deleted
    Then system will respond with message "Haluatko varmasti poistaa seuraavan vinkin?(1: Kyllä)"
    And system will respond with message "Vinkki poistettu."

    Scenario: A reading tip with incorrect id is not deleted
    Given command delete tip is selected
    When non-existing tip is deleted
    Then system will respond with message "Väärä ID"