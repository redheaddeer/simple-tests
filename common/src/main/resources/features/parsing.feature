@json
Feature: example

  Scenario: parsing with nodes
    * Prepare parsed json
      | field      | value          |
      | data.email | test@email.com |
      | data.phone | +0123456789    |