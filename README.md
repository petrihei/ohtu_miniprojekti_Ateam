# ohtu_miniprojekti_Ateam

## Backlogit
https://docs.google.com/spreadsheets/d/1919yz47HJkCDnhW_d3NNH1e0Amzv3YS6PuW-Fr3gnwA/edit#gid=1

## Definition of Done

* Task on valmis ('done'), kun
  * sen kuvaama toiminnallisuus on toteutettu
  * testikattavuus on riittävä (toistaiseksi riittävä testikattavuus on > 80%)
  * koko ohjelma toimii, kun ajetaan 'gradle run' ennen pull requestin tekemistä
  * toinen ryhmän jäsen on suorittanut koodin katselmoinnin
  * koodi on kirjoitettu niin selkeästi, että muutkin sitä ymmärtävät ja tarvittaessa kommentoitua.
* User story on valmis, kun
  * kaikki siihen liittyvät taskit ovat valmiita
  * toteutus täyttää hyväksymisehdot (Acceptance criteria).



## Travis
[![Build status](https://travis-ci.org/petrihei/ohtu_miniprojekti_Ateam.svg?branch=master)](https://travis-ci.org/petrihei/ohtu_miniprojekti_Ateam)

## Testikattavuusraportti
http://htmlpreview.github.io/?https://github.com/petrihei/ohtu_miniprojekti_Ateam/blob/testauskattavuusraportti/testauskattavuusraportti/index.html

<!--
## jacoco

[![codecov](https://codecov.io/gh/petrihei/ohtu_miniprojekti_Ateam/branch/master/graph/badge.svg)](https://codecov.io/gh/petrihei/ohtu_miniprojekti_Ateam)
-->


## Ohjelman asennus- ja käyttöohje
* lataa jar-tiedosto uusimmasta releasesta https://github.com/petrihei/ohtu_miniprojekti_Ateam/releases/tag/v0.2
* lataa myös pakattu lähdekoodi (sisältää tietokannan)
* aja tietokannan (tietokanta.db) sisältävässä kansiossa java -jar <jar-tiedoston-nimi>
