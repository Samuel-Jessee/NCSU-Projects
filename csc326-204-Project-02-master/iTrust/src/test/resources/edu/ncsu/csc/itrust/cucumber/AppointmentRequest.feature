#Author: cmthomp7

Feature: Request an Appointment
	As a patient
	I want to be able to request an appointment from my HCP
	So that I can recieve the appropriate health care I need. 
	
Scenario Outline: Appointment Request 
Given I login as Patient with <mid> <password>
When I navigate to Appointment Requests page
And select <apttype>, enter <timestamp>, enter <aptcomment>, select <hmid>
And clicks request
Then the appointment request is linked to the HCP <hmid>

Examples:
|mid     |password       |apttype           |timestamp                 |aptcomment    |hmid        |
|1       |pw             |general checkup   |2017-01-27 13:00:00       |Test Bug 4    |9000000003  |
    
#may need to specify this feature a little more - do I need to include the steps
#for logging out and logging in as HCP 3 and checking appoitnment requests?
