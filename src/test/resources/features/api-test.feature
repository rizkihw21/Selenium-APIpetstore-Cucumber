Feature: Testing API Pet Store

@POSTuser
Scenario: Successfully create a new user
    Given I have the user data for creating a new user
    When I send a POST request to "/user" with the user data
    Then I should get a 200 status code
    And the response should contain the new user information

@POSTuserNegative
Scenario: Fail to create a user due to invalid userStatus format (string instead of integer)
    Given I have the user data with invalid userStatus format
    When I send a POST request to "/user" with the user data
    Then I should get a 500 status code
    And the response should indicate a server error
    And the response message should be "something bad happened"

@GETloginUser
Scenario: Successfully login user
    Given I have valid login credentials with username "rizki" and password "rizki123"
    When I send a GET request to "/user/login" with the credentials
    Then I should get a 200 status code
    And the response should contain the login details

@GETlogoutUser
Scenario: Successfully logout user
    Given I am logged in with valid account
    When I send a GET request to "/user/logout"
    Then I should get a 200 status code
    And the response should indicate the logout is successfully

@POSTaddNewPet
Scenario: Add a new pet to the store
    Given I have data for added a new pet to the store
    When I send a POST request to "/pet" with the pet data
    Then I should get 200 status code
    And the response should contains the new pet information
