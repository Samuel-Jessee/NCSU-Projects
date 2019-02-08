/*
 * Adds fitness data to the database for testing.
 * 
 * author: Samuel Jessee (sijessee)
 */
INSERT INTO fitness(patient_id, fitness_date, calories_burned, steps, distance, floors, minutes_sedentary, minutes_light, minutes_fair, minutes_very, active_calories)
VALUES
('1', '2008-8-15', '200', '302', '3.2', '7', '60', '150', '190', '192', '187'),
('1', '2008-9-15', '200', '302', '3.2', '7', '60', '150', '190', '192', '187'),
('2', '2008-9-15', '200', '302', '3.2', '7', '60', '150', '190', '192', '187');
