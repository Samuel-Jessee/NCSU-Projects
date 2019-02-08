#author Chris Tucker

Feature: Add/Edit Obstetrics Office Visit
	As an HCP-OGBYN
	I want to be able to record an obstetrics office visit.
	So that I can have a record of the visit.
	


Scenario Outline: Invalid/Missing inputs [E5]
Given I enter <weeksPregnant>, <weight>, <bloodPressure>, <fetalHeartRate>, <numBabies>, <isLowLyingPlacenta>
Then an error message appears

Examples:
|weeksPregnant	|weight				|bloodPressure		|fetalHeartRate	|numBabies			|isLowLyingPlacenta		|
|-55    		|210				|"null	"			|95  			|1					|"false"				|


Scenario Outline:Valid Inputs [S2]
Given I enter valid inputs <weeksPregnant>, <weight>, <bloodPressure>, <fetalHeartRate>, <numBabies>, <isLowLyingPlacenta>
Then the information is stored in the database

Examples:
|weeksPregnant	|weight				|bloodPressure	|fetalHeartRate	|numBabies			|isLowLyingPlacenta	|
|25   			|210				|"70/110"		|95				|1					|"false"			|

Scenario Outline:Edit Office Visit [S3]
Given I have selected to edit a previous office visit
And I enter <weeksPregnant>, <weight>, <bloodPressure>, <fetalHeartRate>, <numBabies>, <isLowLyingPlacenta>
Then the selected office visit is edited.

Examples:
|weeksPregnant	|weight				|bloodPressure	|fetalHeartRate	|numBabies			|isLowLyingPlacenta	|
|25   			|210				|"210/40"		|95				|1					|no					|


Scenario Outline:Add Ultrasound [S6]
Given have created an Obstetrics Office Visit with valid information
And I enter Ultrasound information <CRL>, <BPD>, <HC>, <FL>, <OFD>, <AC>, <HL>, <EFW>
Then the information is stored

Examples:
|CRL        	|BPD    			|HC         	|FL         	|OFD    			|AC 	  	|HL			|EFW			|
|25   			|13  				|19 			|22				|6					|8			|39			|87				|


Scenario Outline:Set Next Appointment [S7][E6]
Given I have selected to enter a new office visit on <date> at <time> with <weeksPreg>
Then a new appointment is set for <apptDate> at <apptTime>

Examples:
|date        	|time			|weeksPreg    		|apptDate			|apptTime		|
|3/8/2017  		|11:00  		|12					|4/05/2017			|11:00			|
|3/8/2017  		|11:00  		|15					|3/22/2017			|11:00			|
|3/8/2017  		|11:00  		|32					|3/15/2017			|11:00			|
|3/8/2017  		|11:00  		|41					|3/10/2017			|11:00			|
