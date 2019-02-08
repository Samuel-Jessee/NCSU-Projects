#author Chris Tucker

Feature: View Labor and Delivery Report
	As an HCP
	I want to be able to view the Labor and Delivery Report.
	So that I have all pertinent pregnancy information in case of emergency.

#This scenario commented out because this check is done in the front end, which cannot be tested without Selenium, and Selenium does not work with JSF	
#	Scenario Outline: Patient Selected Without Record [E2]
#	Given I search for a patient with no obstetrics initialization
#	Then I should be given an error
	
	Scenario Outline: Valid Patient Selected [S1]
	Given I search for a patient with <pid>, who has an obstetric initialization
	Then I should be shown the patient's Labor and Delivery Report
	
	Examples:
	|pid    |
	| 1     |