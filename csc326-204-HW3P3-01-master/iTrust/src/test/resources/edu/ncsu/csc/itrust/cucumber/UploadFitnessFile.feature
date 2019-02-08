#author alrichma

Feature: Upload Fitness File
As a HCP
I want to upload a Fitness File
So that the information will appear for the patient

Scenario Outline: Valid File upload
	Given user logs in as HCP with MID: <MID> with PASS: <PW>
	And I click Fitness Health Information
	And I enter patient <first_name>
	When user attempts to add file <file>
	And I click upload
	Then the values are successfully uploaded
	
	Examples:
		| MID        | PW | first_name | file                 |
		| 9000000000 | pw | Random  |fibit_export_HW3.csv  |
	
Scenario Outline: Invalid File upload
	Given user logs in as HCP with MID: <MID> with PASS: <PW>
	And I click Fitness Health Information
	And I enter patient <first_name>
	When user attempts to add file <file>
	And I click upload
	Then the values are not uploaded

	Examples:
		| MID        | PW | file                         |
		| 9000000000 | pw | fibit_invalid_export_HW3.csv |