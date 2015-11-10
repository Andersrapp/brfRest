CREATE TABLE Address(
id INTEGER PRIMARY KEY AUTO_INCREMENT,
city VARCHAR(40),
streetName VARCHAR(11) NOT NULL,
streetNumber VARCHAR(2),
UNIQUE KEY `uniqe_index`(streetName, streetNumber)
);

CREATE TABLE Apartment(
id INTEGER PRIMARY KEY AUTO_INCREMENT,
apartmentNumber INTEGER NOT NULL UNIQUE,
address INTEGER,
roomCount INTEGER,
area INTEGER NOT NULL,
floorCode INTEGER,
share FLOAT,
CONSTRAINT fk_apartment_address FOREIGN KEY(address) REFERENCES Address(id)
);

CREATE TABLE Resident(
id INTEGER PRIMARY KEY AUTO_INCREMENT,
ssn VARCHAR(12),
firstName VARCHAR(20),
lastName VARCHAR(20)
);

CREATE TABLE ContactInformation(
residentId INTEGER PRIMARY KEY,
telephone VARCHAR(30),
email VARCHAR(50),
CONSTRAINT fk_resident FOREIGN KEY(residentId) REFERENCES Resident(id)
);

CREATE TABLE Residency(
id INTEGER PRIMARY KEY AUTO_INCREMENT,
apartment INTEGER,
resident INTEGER,
fromDate DATE NOT NULL,
toDate DATE,
CONSTRAINT fk_residency_apartment FOREIGN KEY(apartment) REFERENCES Apartment(id),
CONSTRAINT fk_residency_resident FOREIGN KEY(resident) REFERENCES Resident(id)
);

CREATE TABLE Commitment(
id INTEGER PRIMARY KEY AUTO_INCREMENT,
role ENUM('member', 'chairman', 'cashier', 'secretary', 'deputy') NOT NULL,
resident INTEGER, 
fromDate DATE NOT NULL,
toDate DATE,
authorized BOOLEAN NOT NULL,
CONSTRAINT fk_commitment_resident FOREIGN KEY (resident) REFERENCES Resident(id)
);
