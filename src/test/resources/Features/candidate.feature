@smoke
Feature: Candidate Management

  Background:
    Given user opens the OrangeHRM application
    When user enters valid username and password
    And user clicks on the login button
    Then dashboard page should be displayed
    When user navigates to Recruitment Candidates page
    Then candidates page header should be displayed

  @smoke @login
  Scenario: Login with valid credentials and navigate to recruitment
    # Covered by Background

  @smoke @regression
  Scenario: Add new candidate with required details
    When user clicks on Add Candidate button
    And user enters candidate details from json
      | firstName |
      | lastName  |
      | email     |
      | phone     |
      | vacancy   |
      | keywords  |
    And user uploads resume file
    And user saves the candidate
    Then candidate should be saved successfully

  @smoke
  Scenario: Search candidate by name
    When user enters candidate name in search field
    And user tap on suggestion candidate name dropdown
    And user clicks on Search button
    Then candidate should appear in the candidates list



