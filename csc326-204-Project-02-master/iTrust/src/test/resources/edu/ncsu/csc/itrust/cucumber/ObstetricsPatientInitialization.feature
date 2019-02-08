#Author: smmahaff

Feature: Initialize an Obstetrics Patient
	As an HCP-OGBYN
	I want to be able to create a new obstetrics patient module
	So that I can monitor my patient's pregnancy. 

Scenario Outline: Initialize Obstetrics Patient [S1][S2][S3]
Given I login with mid: <mid> and password: <password>
When I navigate to the obstetrics page
And I search for the obstetrics patient: <patient>
And click Add New Obstetrics Patient
And input the LMP: <lmp> and Date: <date> and submit
Then the EDD and number of weeks pregnant are displayed

Examples:
|mid		|password	|patient		|lmp		|date		|
|9000000012	|pw			|Princess		|5/16		|02/08/2017	|
|9000000012	|pw			|Random			|1/17		|03/22/2017	|

Scenario Outline: Invalid Obstetrics Patient [E3]
Given I login with mid: <mid> and password: <password>
When I navigate to the obstetrics page
And I search for the obstetrics patient: <patient>
Then the message The patient is not eligible for obstetric care is displayed

Examples:
|mid		|password		|patient			|
|9000000012	|pw				|Anakin				|
|9000000012	|pw				|Andy 				|

Scenario Outline: Edit Prior Pregnancy Info
Given I login with mid: <mid> and password: <password>
When I navigate to the obstetrics page
And I search for the obstetrics patient: <patient>
And view an existing record
And I enter <year>, <weeks>, <hours>, <weight>, <type>, <multiple> for the fields
And I submit
Then the values I edited are saved

Examples:
|mid		|password		|patient			|year			|weeks				|hours			|weight			|type				|multiple		|
|9000000012	|pw				|Princess			|1995			|38					|8				|15				|Vaginal Delivery	|no				|
|9000000012	|pw				|Princess 			|2003			|37					|10				|30				|Caesarean Section	|2				|

Scenario Outline: Edit Prior Pregnancy Info Invalid
Given I login with mid: <mid> and password: <password>
When I navigate to the obstetrics page
And I search for the obstetrics patient: <patient>
And view an existing record
And I enter <year>, <weeks>, <hours>, <weight>, <type>, <multiple> for the fields
And I submit
Then an error appears

Examples:
|mid		|password		|patient			|year			|weeks				|hours			|weight			|type				|multiple		|
|9000000012	|pw				|Princess 			|0000			|38					|8				|15				|Vaginal Delivery	|no				|
|9000000012	|pw				|Princess 			|abcd			|38					|8				|15				|Vaginal Delivery	|no				|
|9000000012	|pw				|Princess 			|9999			|38					|8				|15				|Vaginal Delivery	|no				|
|9000000012	|pw				|Princess 			|1995			|100				|8				|15				|Vaginal Delivery	|no				|
|9000000012	|pw				|Princess 			|1995			|ab					|8				|15				|Vaginal Delivery	|no				|
|9000000012	|pw				|Princess 			|1995			|-1					|8				|15				|Vaginal Delivery	|no				|
|9000000012	|pw				|Princess 			|1995			|38					|1000			|15				|Vaginal Delivery	|no				|
|9000000012	|pw				|Princess 			|1995			|38					|$$$			|15				|Vaginal Delivery	|no				|
|9000000012	|pw				|Princess			|1995			|38					|-1				|15				|Vaginal Delivery	|no				|
|9000000012	|pw				|Princess 			|1995			|38					|8				|900			|Vaginal Delivery	|no				|
|9000000012	|pw				|Princess 			|1995			|38					|8				|zzz			|Vaginal Delivery	|no				|
|9000000012	|pw				|Princess 			|1995			|38					|8				|				|Vaginal Delivery	|no				|
|9000000012	|pw				|Princess 			|1995			|38					|8				|15				|Vaginal Delivery	|no				|
|9000000012	|pw				|Princess 			|1995			|38					|8				|15				|Vaginal Delivery	|50				|
