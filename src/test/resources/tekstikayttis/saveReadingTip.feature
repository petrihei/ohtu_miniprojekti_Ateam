Feature: A new reading tip can be saved

    Scenario: Saving a reading tip of type book is successful
    Given command save tip is selected
    And type book is selected
    When title "testikirja" and description "testikuvaus" and isbn "ISBN 978-0-596-52068-7" and author "testi kirjailija" and tags "kirja, tag" are entered
    Then system will respond with message "Seuraavat tiedot tallennettu:"

    Scenario: Saving a reading tip of type book is not succesful with wrong isbn
    Given type book is selected
    When title "testikirja" and description "testikuvaus" and wrong isbn "0123456789" and author "testi kirjailija" and tags "kirja, tag" are entered
    Then system will respond with message "ISBN väärässä muodossa"
    And system will respond with message "Haluatko syöttää uuden? k/e"
    And system will respond with message "ISBN ei tallennettu"

    Scenario: Saving a reading tip of type book is not succesful with too short author
    Given type book is selected
    When title "testikirja" and description "testikuvaus" and wrong isbn "ISBN 978-0-596-52068-7" and author "t" and tags "kirja, tag" are entered
    Then system will respond with message "Kirjailija väärässä muodossa"
    And system will respond with message "Haluatko syöttää uuden? k/e"
    And system will respond with message "Kirjailija ei tallennettu"

    Scenario: Saving a reading tip of type video is successful
    Given command save tip is selected
    And type video is selected
    When title "testivideo" and description "testikuvaus" and creator "testitekijä" and url "www.video.com/watch" and date "2017-12-01" and tags "video, tag" are entered
    Then system will respond with message "Seuraavat tiedot tallennettu:"

    Scenario: Saving a reading tip of type blogpost is successful
    Given command save tip is selected
    And type blog is selected
    When title "testiblogi" and description "testikuvaus" and creator "testitekijä" and name "postaksen nimi" and url "www.blogger.com/blog" and date "2017-12-01" and tags "blog, tag" are entered
    Then system will respond with message "Seuraavat tiedot tallennettu:"

    Scenario: Saving a reading tip of type podcast is successful
    Given command save tip is selected
    And type podcast is selected
    When title "testicast" and description "testikuvaus" and creator "testitekijä" and name "podcastin nimi" and url "www.podcast.com/listen" and date "2017-12-01" and tags "podcast, tag" are entered
    Then system will respond with message "Seuraavat tiedot tallennettu:"