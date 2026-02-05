Feature: OrangeHRM Recruitment Candidate Management

  Background:
    Given user launches OrangeHRM application
    And user logs in with valid credentials
    Then dashboard should be displayed

# ----------------------------
# Scenario 1: Navigation
# ----------------------------
  Scenario: Navigate to Recruitment Candidates page
    When user navigates to Recruitment Candidates page
    Then candidates page should be displayed

    # ----------------------------
  # Scenario 2: Add New Candidate
  # ----------------------------
  Scenario: Add new candidate with all required fields
    Given user is on Recruitment Candidates page
    When user clicks on Add Candidate
    And user enters candidate first name
    And user enters candidate last name
    And user enters candidate email
    And user enters candidate phone number
    And user selects vacancy from dropdown
    And user uploads candidate resume
    And user enters Keywords
    And user saves the candidate
    Then success message should be displayed

    # ----------------------------
  # Scenario 3: Verify Candidate in List

  Scenario: Search candidate by name or email
    Given user is on Recruitment Candidates page
    When user searches candidate by name or email
    Then candidate search results should be displayed

    # ----------------------------
# Scenario 5: View Candidate Details
# ----------------------------
  Scenario: View candidate details
    Given user is on Recruitment Candidates page
    And user selects a candidate from candidate list
    When user opens candidate details
    Then candidate detail page should be displayed