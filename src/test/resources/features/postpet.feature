@prodtest
Feature: Validate product interest rates

  Scenario: Update interest rate for product
    Given user points to eq_post_object_service
    When user provides the below headers
      | Content-Type | application/json |
    When user provides basic authentication
    When user provides following body
      | postProduct.json |
      | name=eq_test123  |
    When user issues POST request to /objects
    Then status code should be 200
    Then store name in CUSTOM_name

  Scenario: Verify the interest rate for product
    Given user points to eq_post_object_service
    When user provides basic authentication
    When user provides below query params
      | name | CUSTOM_name |
    When user issues GET request to /objects
    Then status code should be 200

  Scenario: verify the list of regres users
    Given user points to regres_users-service
    When user provides below query params
      | page | 2 |
    When user issues GET request to /products
    Then status code should be 200

  Scenario: verify my own api post
    Given user points to myApi_service
    When user provides the below headers
      | Content-Type | application/json |
    When user issues POST request to /post
    Then status code should be 200