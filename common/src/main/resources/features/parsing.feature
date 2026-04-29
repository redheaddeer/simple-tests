@parsing
Feature: example

  @json
  Scenario: parsing with nodes
    * Prepare parsed json
      | field                  | value            |
      | data[0].email          | test@email.com   |
      | data[0].phone          | +0123456789      |
      | data[0].other[0].field | facebook         |
      | data[0].other[0].value | dragonage        |
      | data[0].other[1].field | instagram        |
      | data[0].other[1].value | dragonage_photos |

  @xml
  Scenario: parsing with nodes
    * Prepare parsed xml
      | field         | value          |
      | Data.Email[0] | test@email.com |
      | Data.Email[1] | test@email.com |
      | Data.Phone    | +0123456789    |