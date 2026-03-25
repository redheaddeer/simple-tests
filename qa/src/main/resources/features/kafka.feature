@kafka
Feature: Kafka

  Scenario Template: send <comment> request
    * Kafka request
      | rqTopic | test.rq   |
      | rsTopic | test.rs   |
      | success | <success> |

    Examples:
      | comment | success |
      | success | true    |
      | failure | false   |