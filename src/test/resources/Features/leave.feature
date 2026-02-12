@leave @smoke
Feature: Leave Management

  Background:
    Given user opens the OrangeHRM application
    When user enters valid username and password
    And user clicks on the login button
    Then dashboard page should be displayed

  @smoke @regression
  Scenario: Apply leave and verify success message
    When user navigates to Leave module
    And user navigates to Apply Leave page
    And user fills leave application form from json
    And user submits leave application
    Then success message should be displayed for leave

  @regression
  Scenario: Verify applied leave in My Leave page
    When user navigates to Leave module
    And user navigates to My Leave page
    Then my leave page should be displayed

    When user filters leave by date range from json
    Then applied leave should appear in leave list
    And leave status should be "Pending Approval"

  @smoke
  Scenario: Logout and session validation
    When user logs out from the application
    Then login page should be displayed
    And user session should be terminated
