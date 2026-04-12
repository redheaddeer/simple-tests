@json
Feature: example

  Scenario: parsing with nodes
    * Prepare parsed json
      | field                  | value            |
      | data[0].email          | test@email.com   |
      | data[0].phone          | +0123456789      |
      | data[0].other[0].field | facebook         |
      | data[0].other[0].value | dragonage        |
      | data[0].other[1].field | instagram        |
      | data[0].other[1].value | dragonage_photos |