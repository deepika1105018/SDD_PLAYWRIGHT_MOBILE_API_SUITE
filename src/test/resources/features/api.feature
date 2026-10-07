Feature: ReqRes API automation
  Background:
    Given ReqRes API is available

  @api @smoke @regression
  Scenario: Get user 10
    When I get users from page 2
    Then status should be 200 and user 10 first name should be Byron

  @api @sanity @regression
  Scenario: Create user with chained data
    When I create a user using chained GET data
    Then status should be 201 with generated id and valid schema
