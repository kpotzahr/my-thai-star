@waiter @ui
Feature: Login as waiter
  As a waiter I want to login and see a list of reservations

  Scenario: See an existing reservation
    Given There is a reservation for "someone.new@mail.com"
    And The My Thai start page has been opened
    When I login as waiter
    Then I can find the reservation for the email

  @api
  Scenario: Correct data accepted and returned by the server
    Given A booking with the following data:
      | name  | email                 | persons | date       | time  |
      | Mr. X | mr.x@some-company-com | 2       | 01.01.2022 | 18:00 |
    When I search for reservations for "mr.x@some-company-com"
    Then The list contains the booking