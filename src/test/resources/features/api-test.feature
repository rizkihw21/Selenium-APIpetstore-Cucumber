Feature: Testing API Pet Store

# ////////////TC 01 Positive////////////////////
@POSTuser
Scenario: Successfully create a new user
    Given I have the user data for creating a new user
    When I send a POST request to "/user" with the user data
    Then I should get a 200 status code
    And the response should contain the new user information

# ////////////TC 02 Negative////////////////////
@POSTuserNegative
Scenario: Fail to create a user due to invalid userStatus format (string instead of integer)
    Given I have the user data with invalid userStatus format
    When I send a POST request to "/user" with the user data
    Then I should get a 500 status code
    And the response should indicate a server error
    And the response message should be "something bad happened"

# ////////////TC 03 Positive////////////////////
@GETloginUser
Scenario: Successfully login user
    Given I have valid login credentials with username "rizki" and password "rizki123"
    When I send a GET request to "/user/login" with the credentials
    Then I should get a 200 status code
    And the response should contain the login details

# ////////////TC 04 Positive////////////////////
@GETlogoutUser
Scenario: Successfully logout user
    Given I am logged in with valid account
    When I send a GET request to "/user/logout"
    Then I should get a 200 status code
    And the response should indicate the logout is successfully

# ////////////TC 05 Positive////////////////////
@POSTaddNewPet
Scenario: Add a new pet to the store
    Given I have data for added a new pet to the store
    When I send a POST request to "/pet" with the pet data
    Then I should get 200 status code
    And the response should contains the new pet information

# ////////////TC 06 Negative////////////////////
@POSTaddNewPetnegative
Scenario: Add a new pet to the store with invalid request
    Given I have data for added a new pet to the store with invalid response
    When I send a POST request to "/pet" with the pet data
    Then I should get 500 status code
    And the response message should be "something bad happened"

# ////////////TC 07 Positive////////////////////
 @GETpositiveFindpetID
 Scenario: Try found pet ID
    Given I have valid pet id
    When I send a GET request to "/pet/1" with the credentials
    Then I should get 200 status code
    And the response should contains the new pet information

# ////////////TC 08 Negative////////////////////
 @GETnegativeFindpetID
 Scenario: Try not found pet ID
    Given I have invalid pet id
    When I send a GET request to "/pet/0050" with the credentials
    Then I should get 404 status code
    And the response message should be "Pet not found"

# ////////////TC 08 Positive////////////////////
@GETpetInventory
Scenario: Try get pet inventories by status
    Given I have valid API url
    When I send a GET request to "/store/inventory"
    Then I should get a 200 status code
    And the response should contains the pet inventories

# ///////////TC 09 Positive/////////////////////
@POSTstoreOrder
Scenario: Place an order for a pet
    Given I have data for added a new order for a pet
    When I send a POST request to "/store/order"
    Then I should get a 200 status code
    And the response should contains the order

# //////////TC 10 Negative ///////////////////
@POSTstoreOrderNegative
Scenario: Place invalid an order for a pet
    Given I have invalid data for added a new order for a pet
    When I send a POST request to "/store/order"
    Then I should get a 500 status code
    And the response message should be "something bad happened"
