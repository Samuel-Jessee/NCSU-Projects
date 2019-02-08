#Author: ggyang
Feature: Bug2 View Basic Health Info Web
As a HCP
I want to be able to view the basic health info webpage
So I can see a paitient basic health info

# tests to view the basic health information on the web page
Scenario: Test Bug2 View Baisc Health
	Given user clicks on HCP 1 sample user from login page
	When user clicks on Basic Health Information from Patient dropdown list
	And user types in 1 into search box
	And user clicks on Patient Random Person with MID 1
	Then the Basic Health Information for Patient 1 is visible