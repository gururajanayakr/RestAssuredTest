# Author : Gururaja Nayak
  Feature: Automating the Location API
    Scenario Outline: Creating the new Location
      Given building the request for API
      * creating body with following JSON objects :
      |  key   |  value          |
      |  lat   |  -38.383494     |
      |  lng   |  33.427362      |
      | accuracy | 50            |
      | name     | #<testCaseName>.name |
      | phone_number | (+91) 983 893 3937 |
      | address      | #<testCaseName>.address |
      | website      | https://rahulshettyacademy.com |
      | language     | #<testCaseName>.language        |
      * I have  following types :
      | types |
      | shoe park |
      | shop      |
      When convert the data into JSON object and add body to the request
      * user calls "POST" method with the resource "AddPlaceAPI"
      Then API call got success and status is 200
      * verify "scope" in response body is "APP"
      Then user stores response value "place_id"
      * user calls "GET" method with the resource "GetPlaceAPI"
      Then API call got success and status is 200
      * verify "name" in response body is "#<testCaseName>.name"
      Then user calls "DELETE" method with the resource "DeletePlaceAPI"
      * API call got success and status is 200

    Examples:
      | testCaseName |
      | AddPlaceAPIOne |
      | AddPlaceAPITwo |
      | AddPlaceAPIThree |

