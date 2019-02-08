TRUNCATE childbirths;

INSERT INTO childbirths(
visitID,
patientMID,
pitocin,
nitrousOxide,
pethidine,
epiduralAnaesthesia,
magnesiumSulfate,
rhimmuneglobulin,
method,
childrenids,
cbdate)
VALUE (
1,
1,
0,
2.7,
8,
0,
0.2,
0,
'Vaginal Delivery',
'1',
'2017-03-31 12:01:01')ON DUPLICATE KEY UPDATE visitID = visitID;

INSERT INTO childreninchildbirth(
childID,
patientMID,
approximate,
gender,
cbdate)
VALUE(
1,
2000,
0,
'Male',
'2017-03-31 13:01:01')ON DUPLICATE KEY UPDATE childID = childID;
