#author Sean Mahaffey
# Homework 2 Part 1

Feature: View Full Calendar
	As a patient
	I want to be able to view my calendar
	So I have a record of upcoming appointments and conflicts

Scenario Outline: Test View Full Calendar
	Given user logs in as Patient with MID: <MID> with PASS: <PW>
	When user navigates to the View Full Calendar page
	Then the Calendar page is displayed

Examples:
| MID | PW |
| 1   | pw |
| 2   | pw |
| 5   | pw |
