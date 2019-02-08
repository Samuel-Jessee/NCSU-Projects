#Author: Samuel Jessee (sijessee)

Feature: Childbirth Hospital Visit
	As an HCP-OGBYN
	I want to be able to create a childbirth hospital visit
	So that I can help deliver the patient's baby on a moment's notice.

Scenario Outline: Scenario 7 8 and 9 [S1][S2][S3][S4][S5][S6][E1][E2][E3]
Given a childbirth visit was prescheduled: <expected>
When I select patient <patient>
And I select <prefMethod> as the preferred delivery method
And I enter <drugAmount> for the amount of each drug administered
And I enter the date and time: <date>
And I enter the delivery method: <method>
And I enter the hours in labor: <hours>
And I add the baby as a patient, entering <sex>, <estimate> and <dob>
And I save the childbirth visit

Examples:
|expected	|patient	|prefMethod									|drugAmount	|date				|method										|hours	|sex	|estimate	|dob				|
|false		|21			|Childbirth,Vaginal Delivery				|0			|2017-05-05T11:00	|Childbirth,Vaginal Delivery				|3		|male	|true		|2017-05-05T11:00	|
|false		|21			|Childbirth,Vaginal Delivery				|50			|2017-04-05T11:00	|Childbirth,Vaginal Delivery				|5		|female	|false		|2017-04-05T11:00	|
|true		|21			|Childbirth,Vaginal Delivery Forceps		|70			|2017-06-13T11:00	|Childbirth,Vaginal Forceps					|13		|female	|false		|2017-06-14T11:00	|
|true		|21			|Childbirth,Vaginal Delivery Vacuum Assist	|50			|2017-04-05T13:00	|Childbirth,Vaginal Delivery Vacuum Assist	|2		|male	|false		|2017-04-05T14:00	|
