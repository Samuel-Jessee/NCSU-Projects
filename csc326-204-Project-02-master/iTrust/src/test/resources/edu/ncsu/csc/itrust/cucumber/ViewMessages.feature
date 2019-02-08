#@author rebaker6

Feature: View Messages
As an HCP
I want to be able to differientiate between read and unread messages
So I know which messages I have already opened and which I still need to open

#test that messages are no longer unread after they have been read
Scenario: ViewUnreadMessage
	Given the user is logged in as Kelly Doctor
	
	When user navigates to My Messages page
	And clicks read on an unread message 
	And clicks back to return to the My Messages page
	
	Then the message should no longer be bold