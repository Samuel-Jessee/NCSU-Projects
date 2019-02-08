# HW 2 P1 alrichma
# Bug 2: Failure to view Basic Health info
Feature: View Basic Patient Health
As a HCP
I want to view patient's basic health information
So I can see the basic health information page

Scenario Outline:
	Given user logs in as HCP with MID: <MID> with PASS: <PW>
	When user navigates to view basic health info page for patient
	And user enters <patient> in first name field
	And user chooses patient with MID <patientMID>
	Then the patient's basic health info page is successfully viewed

	Examples:
		# UC52: TestViewHealthMetricsByHCP
		| MID        | PW | patientMID | patient |
		| 9000000000 | pw | 100        | Anakin  |
		| 9000000000 | pw | 1          | Random  |