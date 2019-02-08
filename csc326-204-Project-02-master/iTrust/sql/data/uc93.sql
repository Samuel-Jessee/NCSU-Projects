TRUNCATE obstetricsInitialization;
TRUNCATE previousPregnancyInfo;

INSERT INTO obstetricsinitialization(
oDate,
EDD,
LMP,
patientMID, 
visitID, 
weeksPreg,
RhNeg)
VALUES (
'2011-03-10',
'2011-04-10',
'2010-07-10',
1,
1,
10,
true
)ON DUPLICATE KEY UPDATE visitID = visitID;

/* Inserting initialization for Random Person for use in UC94 */
INSERT INTO obstetricsinitialization(
oDate,
EDD,
LMP,
patientMID, 
visitID, 
weeksPreg,
RhNeg)
VALUES (
'2017-02-05',
'2017-10-08',
'2017-01-01',
1,
2,
5,
true
)ON DUPLICATE KEY UPDATE visitID = visitID;

INSERT INTO previouspregnancyinfo(
amtChildren,
deliveryType,
hoursInLabor,
infoID,
patientMID,
weeksPreg,
weightGained,
year)
VALUES (
2,
1,
3,
1,
1,
30,
60,
2009
)ON DUPLICATE KEY UPDATE infoID = infoID;
