#Author: Louis Le

Feature: View Patient Health Tracking Information
	As a LHCP or HCP
	I want to be able to view a patient's health tracking information on a specified date
	So I can better understand how to make my patient better
	
Scenario Outline: Successfully Read Patient Health On Specific Date
	Given user logs in as HCP with MID: <MID> with PASS: <PW>
	And I click Fitness Health Information
	And I enter patient <first_name>
	And input <month>/<day>/<year> date
	And click on edit
	Then I can view correct data on the patient

Examples:
	| MID        | PW       | first_name   | month | day | year |
	| 9000000000 | pw       | Random       | 02    | 01  | 2008 |
	| 9000000000 | pw       | Andy         | 02    | 10  | 2008 |
	| 9000000000 | pw       | Care         | 02    | 15  | 2008 |
	| 9000000000 | pw       | Jennifer     | 02    | 20  | 2008 |
	| 9000000003 | pw       | Random       | 02    | 31  | 2008 |
	| 9000000003 | pw       | Andy         | 02    | 01  | 2008 |
	| 9000000003 | pw       | Care         | 02    | 10  | 2008 |
	| 9000000003 | pw       | Jennifer     | 02    | 15  | 2008 |
	| 9000000007 | pw       | Random       | 02    | 20  | 2008 |
	| 9000000007 | pw       | Andy         | 02    | 31  | 2008 |
	| 9000000007 | pw       | Care         | 02    | 01  | 2008 |
	| 9000000007 | pw       | Jennifer     | 02    | 10  | 2008 |