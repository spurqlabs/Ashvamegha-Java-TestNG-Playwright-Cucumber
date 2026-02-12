@timesheet @smoke
Feature: My Timesheet Management

  Background:
    Given user opens the OrangeHRM application
    When user enters valid username and password
    And user clicks on the login button
    Then dashboard page should be displayed


  @smoke @regression
  Scenario: Add new timesheet entry and validate total hours

    When user navigates to Time module
    And user navigates to My Timesheets page
    Then my timesheet page should be displayed

    When user clicks on Edit button
    And user adds new timesheet entry from json
    And user saves the timesheet
    Then success message should be displayed for timesheet

    And total hours should be calculated correctly


  @regression
  Scenario: Edit existing timesheet entry and validate updated totals

    When user navigates to Time module
    And user navigates to My Timesheets page
    Then my timesheet page should be displayed

    When user clicks on Edit button
    And user updates existing timesheet entry from json
    And user saves the timesheet
    Then success message should be displayed for timesheet

    And updated hours should be displayed correctly
    And total hours should be recalculated correctly


  @smoke
  Scenario: Logout and session validation

    When user logs out from the application
    Then login page should be displayed
    And user session should be terminated
