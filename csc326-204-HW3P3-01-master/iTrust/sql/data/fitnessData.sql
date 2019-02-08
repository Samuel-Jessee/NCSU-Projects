/*
 * Adds fitness data to the database for testing.
 * 
 * author: Samuel Jessee (sijessee)
 */
INSERT INTO fitness(patient_id, fitness_date, cal_burned, steps, distance, floors, min_sedentary, min_light, min_fair, min_very, act_cal)
VALUES
('1', '2008-8-15', '200', '302', '3.2', '7', '60', '150', '190', '192', '187'),
('1', '2008-9-15', '200', '302', '3.2', '7', '60', '150', '190', '192', '187'),
('2', '2008-9-15', '200', '302', '3.2', '7', '60', '150', '190', '192', '187');
