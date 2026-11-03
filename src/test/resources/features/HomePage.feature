Feature: Kapruka Homepage Verification

  Scenario: Open the Kapruka homepage
    Given I am on the Kapruka homepage
    Then I should print the homepage result

  Scenario: Verify Popular Categories section exists
    Given I am on the Kapruka homepage
    Then I should see the section "Popular Categories"