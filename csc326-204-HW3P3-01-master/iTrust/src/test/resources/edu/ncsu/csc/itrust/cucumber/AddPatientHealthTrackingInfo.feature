#Author: Louis Le

Feature: Add Patient Health Tracking Information
	As a LHCP or HCP
	I want to be able to add a patient's health tracking information on a specified date
	So I can better understand how to make my patient better
	
Scenario Outline: Successfully Read Patient Health On Specific Date
	Given user logs in as HCP with MID: <MID> with PASS: <PW>
	And I click Fitness Health Information
	And I enter patient <first_name>
	And input <month>/<day>/<year> date
	And click on edit
	And I can edit <state> data on the patient
	Then I can view the adjusted data on the patient and see message: <message>

Examples:
	| MID        | PW       | first_name   | last_name   | month | day | year | state   | message |
	| 9000000000 | pw       | Random       | Person      | 02    | 01  | 2008 | valid   | Information Successfully Changed |
	| 9000000000 | pw       | Andy         | Programmer  | 02    | 10  | 2008 | invalid | Error With Changing Information  |
	| 9000000000 | pw       | Care         | Needs       | 02    | 15  | 2008 | valid   | Information Successfully Changed |
	| 9000000000 | pw       | Jennifer     | Jareau      | 02    | 20  | 2008 | invalid | Error With Changing Information  |
	| 9000000003 | pw       | Random       | Person      | 02    | 31  | 2008 | valid   | Information Successfully Changed |
	| 9000000003 | pw       | Andy         | Programmer  | 02    | 01  | 2008 | invalid | Error With Changing Information  |
	| 9000000003 | pw       | Care         | Needs       | 02    | 10  | 2008 | valid   | Information Successfully Changed |
	| 9000000003 | pw       | Jennifer     | Jareau      | 02    | 15  | 2008 | invalid | Error With Changing Information  |
	| 9000000007 | pw       | Random       | Person      | 02    | 20  | 2008 | valid   | Information Successfully Changed |
	| 9000000007 | pw       | Andy         | Programmer  | 02    | 31  | 2008 | invalid | Error With Changing Information  |
	| 9000000007 | pw       | Care         | Needs       | 02    | 01  | 2008 | valid   | Information Successfully Changed |
	| 9000000007 | pw       | Jennifer     | Jareau      | 02    | 10  | 2008 | invalid | Error With Changing Information  |