#Author: Samuel Jessee (sijessee)

Feature: Request Appointment
As a patient
I want to be able to request an appointment with my HCP
So that I can get the medical care that I need

Scenario: Patient requests appointment
	Given Gandalf Stormcrow is an active HCP with MID: 9000000003
	And Random Person is a patient with MID: 1
	When Random Person logs in as a patient with MID: 1
	And requests an appointment
	And chooses Gandalf Stormcrow as their HCP
	And chooses appointment type General checkup
	And chooses the date: 1/28/2018
	And enters the Comment “This is a unique comment.”
	And submits the request
	And Gandalf Stormcrow logs in with MID 9000000003
	And chooses to view pending appointment requests
	Then the new appointment request will be listed with the comment “This is a unique comment.”
