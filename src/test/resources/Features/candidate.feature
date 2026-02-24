@candidate @smoke
Feature: Candidate Management

  Background:
    Given user opens the OrangeHRM application
    When user enters valid username and password
    And user clicks on the login button
    Then dashboard page should be displayed

  @smoke @regression
  Scenario: Add and search candidate successfully
    When user navigates to Recruitment Candidates page
    Then candidates page header should be displayed

    When user clicks on Add Candidate button
    And user enters candidate details from json
    And user uploads resume file
    And user saves the candidate
    Then candidate should be saved successfully

    When user searches candidate
    Then candidate record should be displayed

  @smoke @recruitment
  Scenario: Shortlist candidate and schedule interview
    When user navigates to Recruitment Candidates page
    Then candidates page header should be displayed

    When user searches candidate
    Then candidate record should be displayed

    When user clicks on View button for selected candidate
    Then candidate details page should be displayed

    When user clicks on Shortlist button
    And user enters shortlist details from json
    And user clicks on Save button
    Then candidate status should be updated to "Shortlisted"

    When user clicks on Schedule Interview button
    And user enters interview details from json
    And user clicks on Interview Save button
    Then candidate status should be updated to "Interview Scheduled"

  @smoke
  Scenario: Logout and session validation
    When user logs out from the application
    Then login page should be displayed
    And user session should be terminated