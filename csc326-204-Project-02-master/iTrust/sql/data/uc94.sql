TRUNCATE obstetricsofficevisit;
TRUNCATE ultrasound;
TRUNCATE patientgooglecal;

INSERT INTO obstetricsofficevisit(
oDate,
numWeeks,
weight,
bloodPressure,
FHR,
numFetus,
lowLyingPlacenta,
ultrasound,
visitID,
patientMID,
picture,
highgenetic,
complications)
VALUE (
'2017-03-10',
9,
150,
'70/110',
70,
1,
false,
'1,',
1,
1,
'',
1,
'none')ON DUPLICATE KEY UPDATE visitID = visitID;

INSERT INTO ultrasound(
CRL,
BPD,
HC,
FL,
OFD,
AC,
HL,
EFW,
ultraID,
patientMID)
VALUE(
1.0,
2.0,
3.0,
4.0,
5.0,
6.0,
7.0,
8.0,
1,
1)ON DUPLICATE KEY UPDATE ultraID = ultraID;
