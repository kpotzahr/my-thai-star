@waiter @ui
Feature: Login as waiter
  As a waiter I want to login and see a list of reservations

  Scenario: See an existing reservation
    Given There is a reservation for "someone.new@mail.com"
    And The My Thai start page has been opened
    When I login as waiter
    Then I can find the reservation for the email

