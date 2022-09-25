Feature: parse json, yaml, xml, properties, and csv as a node and print it
  This feature demonstrates native support for converting common use file types into an INode

  Background:
    Given the "parser" directory
    And the instructions "test-instructions.json"

  Scenario: INode parsed from JSON
    And "properties" from "json-test.properties"
    When the Flagship engine is executed
    Then the output "json-result.xml" should match "expected-result.txt"

  Scenario: INode parsed from YAML
    And "properties" from "yaml-test.properties"
    When the Flagship engine is executed
    Then the output "yaml-result.xml" should match "expected-result.xml"

  Scenario: INode parsed from YML
    And "properties" from "yml-test.properties"
    When the Flagship engine is executed
    Then the output "yml-result.xml" should match "expected-result.xml"

  Scenario: INode parsed from XML
    And "properties" from "xml-test.properties"
    When the Flagship engine is executed
    Then the output "xml-result.xml" should match "expected-result.xml"

  Scenario: INode parsed from CSV
    And "properties" from "csv-test.properties"
    When the Flagship engine is executed
    Then the output "csv-result.xml" should match "csv-expected-result.txt"

  Scenario: INode parsed from properties
    And "properties" from "properties-test.properties"
    When the Flagship engine is executed
    Then the output "properties-result.xml" should match "expected-result.xml"
