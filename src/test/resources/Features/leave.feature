@leave @entitlement @regression
Feature: Leave Management

  Background:
    Given user opens the OrangeHRM application
    When user enters valid username and password
    And user clicks on the login button
    Then dashboard page should be displayed


  # SCENARIO OUTLINE ONLY FOR ADD ENTITLEMENT

  @leave
  Scenario Outline: Add leave entitlement successfully

    When user navigates to Leave module
    And user clicks on Entitlements tab
    And user selects Add Entitlements option
    Then Add Leave Entitlement page should be displayed

    When user searches and selects logged-in employee name
    And user selects leave type "<leaveType>"
    And user enters entitlement days "<entitlementDays>"
    And user clicks on Save entitlement button
    Then Updating Entitlement popup should be displayed
    When user confirms entitlement update
    Then entitlement record should be displayed in records

    Examples:
      | leaveType   | entitlementDays |
      | CAN - FMLA  | 5              |


  # APPLY LEAVE (DATA FROM JSON OR STATIC)

  @leave
  Scenario: Apply leave successfully

    When user navigates to Leave module
    And user navigates to Apply Leave page
    Then Apply Leave page should be displayed

    When user selects leave type from json
    And user selects from date and to date from json
    And user clicks on Apply button
    Then leave application should be submitted successfully


  # VERIFY IN MY LEAVE (DATA FROM JSON)


  @leave
  Scenario: Verify applied leave in My Leave page

    When user navigates to Leave module
    And user navigates to My Leave page
    And user filters leave by date range from json
    And user clicks on Search button
    Then applied leave record should be displayed

    When user logs out from the application
    Then login page should be displayed
