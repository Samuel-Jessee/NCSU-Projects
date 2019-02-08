#author alrichma

Feature: Edit Personal Records
As a HCP or Admin
I want to edit Personal Records
So that the edits will appear

Scenario Outline:
	Given user logs in as HCP with MID: <MID> with PASS: <PW>
	And I click My Demographics
	When user attempts to change <first_name>, <last_name>, <address>, <address2>, <city>, <state>
	And I click submit
	Then the values are successfully changed
	
	Examples:
		| MID        | PW | first_name | last_name    | address           | address2  | city    | state          |
		| 9000000000 | pw | Sally      | Pediatrician | 123 Happy Street. | PO Box 42 | Raleigh | North Carolina |
	

	