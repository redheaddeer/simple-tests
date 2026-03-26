@kafka
Feature: Kafka

  Scenario Template: send <comment> request
    * Kafka request
      | kafka   | localhost:9092 |
      | rqTopic | test.rq        |
      | rsTopic | test.rs        |
      | success | <success>      |

    Examples:
      | comment | success |
      | success | true    |
      | failure | false   |