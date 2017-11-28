Feature: A new reading tip can be saved

    Scenario: Saving a reading tip is successful
    Given command save tip is selected
    And type book is selected
    When title "testikirja" and description "testikuvaus" are and isbn "0123456789" and author "kirjailija" and tags "kirja, tag" are entered
    Then system will respond with message "Seuraavat tiedot tallennettu:"
