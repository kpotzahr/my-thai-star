@guest
Feature: Book a table more detailed checks
  As a guest I want to reserve a table so that I don't come to the restaurant in vain.

  @ui
  Scenario: Correct confirmation data before booking becomes valid
    Given The My Thai start page has been opened
    And the booking section has been opened
    When I enter and accept the following booking data:
      | name  | email                 | persons | date       | time  |
      | Mr. X | mr.x@some-company-com | 2       | <tomorrow> | 20:00 |
    Then I see all the entered details in the confirmation dialog

  @api
  Scenario: Correct data accepted and returned by the server
    When I book a table with the following booking data:
      | name  | email                 | persons | date    | time  |
      | Mr. X | mr.x@some-company.com | 2       | <today> | 23:00 |
    Then I all the entered details are accepted

  @api
  Scenario: Check email confirmation
    When I book a table with the following booking data:
      | name  | email                 | persons | date       | time  |
      | Mr. X | mr.x@some-company.com | 2       | 01.01.2022 | 20:00 |
    Then I get a confirmation email
    And The confirmation email contains a booking id

  @api
  Scenario: Check that a problem with the mail server prevents the booking
    Given The mail service has an internal problem
    When I book a table with the following booking data:
      | name  | email                 | persons | date       | time  |
      | Mr. X | mr.x@some-company.com | 2       | 01.01.2022 | 20:00 |
    Then The booking is not accepted