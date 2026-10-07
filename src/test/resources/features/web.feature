Feature: jQuery UI Playwright Automation

  @web @smoke @regression
  Scenario: Droppable
    When I drag the element to the droppable target
    Then the jQuery UI action should be successful

  @web @sanity @regression
  Scenario: Selectable
    When I select items 1 3 and 7
    Then the jQuery UI action should be successful

  @web @sanity @regression
  Scenario: Controlgroup
    When I choose Automatic Insurance and Book Now
    Then the jQuery UI action should be successful

  @web @smoke @regression
  Scenario: Datepicker
    When I select and validate the current date
    Then the jQuery UI action should be successful

  @web @sanity @regression
  Scenario: Resizable
    When I resize the resizable box
    Then the jQuery UI action should be successful

  @web @sanity @regression
  Scenario: Sortable ASC to DESC
    When I reverse the sortable items
    Then the jQuery UI action should be successful