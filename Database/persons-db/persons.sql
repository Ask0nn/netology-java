CREATE TABLE persons (
    name TEXT,
    surname TEXT,
    age INTEGER,
    phone_number TEXT,
    city_of_living TEXT,
  PRIMARY KEY (name, surname, age)
);

INSERT INTO persons 
("name", "surname", "age", "phone_number", "city_of_living")
VALUES 
("John", "Piterson", 45, "+78129028117", "Moscow"),
("Nellie", "Waddle", 25, "+78129021111", "Kiev"),
("Rosemary", "Stevens", 33, "+78129028024", "London"),
("Barbara", "Shanks", 18, "+78129021043", "New York"),
("Shane", "Schwab", 21, "+78129026666", "Moscow");
