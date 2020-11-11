@waiter
Feature: Login as waiter
  As a waiter I want to login in order to see a list of reservations

  Scenario: See an existing reservation
    Given There is a reservation for "someone.new@mail.com"
    And The My Thai start page has been opened
    When I login as Willy Waiter
    Then My login name is shown
    And I can see a reservation for "someone.new@mail.com"