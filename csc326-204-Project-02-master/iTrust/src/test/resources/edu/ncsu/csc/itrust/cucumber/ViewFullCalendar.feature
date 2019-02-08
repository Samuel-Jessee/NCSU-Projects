#Author: cmthomp7

Feature: 
	As a patient
	I want to be able to view my full calendar
	So that I can see my office visits, appointments, prescription dates and lab procedure dates for the current month.
	
Scenario: View Full Calendar
Given I login as Patient 2
When I click on Full Calendar
Then the full calendar is displayed for the current month.
 
 