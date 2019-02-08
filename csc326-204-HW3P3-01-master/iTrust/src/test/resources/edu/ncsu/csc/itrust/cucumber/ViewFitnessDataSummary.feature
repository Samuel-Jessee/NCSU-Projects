#Author: sijessee

Feature: View Data Graph
  As an HCP
  I want to be able to view a patient's fitness data as a graph
  So that I can see how the patient's physical activity has changed over a specific period of time

Scenario Outline: View fitness data graph
	Given user logs in as HCP with MID: <MID> with PASS: <PW>
	And I click Fitness Health Information
	And I enter patient <firstName>
	When I click View Summary Report
	And select <data>
	And input start date: <m1>/<d1>/<y1> 
	And input end date: <m2>/<d2>/<y2> 
	And click View Summary
	Then a graph displaying the data will be displayed

Examples:
	| MID        | PW     | firstName    | m1 | d1 | y1   | m2 | d2 | y2   |
	| 9000000000 | pw       | Random       | 02 | 01 | 2008 | 02 | 02 | 2008 |
	| 9000000000 | pw       | Andy         | 02 | 10 | 2008 | 01 | 10 | 2009 |
	| 9000000000 | pw       | Care         | 02 | 15 | 2008 | 02 | 15 | 2010 |
	| 9000000000 | pw       | Jennifer     | 02 | 20 | 2008 | 04 | 10 | 2008 |
	| 9000000003 | pw       | Random       | 02 | 31 | 2008 | 04 | 24 | 2008 |
	| 9000000003 | pw       | Andy         | 02 | 01 | 2008 | 04 | 14 | 2008 |
	| 9000000003 | pw       | Care         | 02 | 10 | 2008 | 05 | 07 | 2008 |
	| 9000000003 | pw       | Jennifer     | 02 | 15 | 2008 | 05 | 29 | 2008 |
	| 9000000007 | pw       | Random       | 02 | 20 | 2008 | 11 | 13 | 2008 |
	| 9000000007 | pw       | Andy         | 02 | 31 | 2008 | 06 | 26 | 2008 |
	| 9000000007 | pw       | Care         | 02 | 01 | 2008 | 04 | 17 | 2008 |
	| 9000000007 | pw       | Jennifer     | 02 | 10 | 2008 | 03 | 02 | 2008 |