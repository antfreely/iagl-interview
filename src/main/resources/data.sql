DELETE FROM route;
INSERT INTO route (airport_code_departure, airport_code_arrival, avios_earned) VALUES ('LHR', 'LAX', 4500);
INSERT INTO route (airport_code_departure, airport_code_arrival, avios_earned) VALUES ('LHR', 'SFO', 4400);
INSERT INTO route (airport_code_departure, airport_code_arrival, avios_earned) VALUES ('LHR', 'JFK', 3200);
INSERT INTO route (airport_code_departure, airport_code_arrival, avios_earned) VALUES ('LHR', 'YYZ', 3250);

DELETE FROM cabin_bonus;
INSERT INTO cabin_bonus (cabin_code, cabin_name, bonus) VALUES ('M', 'World Traveller', 0);
INSERT INTO cabin_bonus (cabin_code, cabin_name, bonus) VALUES ('W', 'World Traveller', 20);
INSERT INTO cabin_bonus (cabin_code, cabin_name, bonus) VALUES ('J', 'World Traveller', 50);
INSERT INTO cabin_bonus (cabin_code, cabin_name, bonus) VALUES ('F', 'World Traveller', 100);