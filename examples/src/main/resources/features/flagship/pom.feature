Feature: Generate a maven pom.xml file using the flagship engine and several different file formats

  Background:
    Given the "flagship/pom" directory
    And the instructions "test-instructions.json"

  Scenario: pom is Generated from templates and a model defined in JSON
    And "properties" from "json-test.properties"
    When the Flagship engine is executed
    Then the output "json-result.xml" should match "expected-result.xml"

  Scenario: pom is Generated from templates and a model defined in YAML
    And "properties" from "yaml-test.properties"
    When the Flagship engine is executed
    Then the output "yaml-result.xml" should match "expected-result.xml"

  Scenario: pom is Generated from templates and a model defined in XML
    And "properties" from "xml-test.properties"
    When the Flagship engine is executed
    Then the output "xml-result.xml" should match "expected-result.xml"
