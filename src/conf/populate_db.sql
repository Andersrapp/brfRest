INSERT INTO Address ( city, `streetName`, `streetNumber`)
VALUES("Gothenburg", "Waernsgatan", "1A");

INSERT INTO Address ( city, `streetName`, `streetNumber`)
VALUES("Gothenburg", "Waernsgatan", "1B");

INSERT INTO Address ( city, `streetName`, `streetNumber`)
VALUES("Gothenburg", "Waernsgatan", "1C");

INSERT INTO Address ( city, `streetName`, `streetNumber`)
VALUES("Gothenburg", "Waernsgatan", "3A");

INSERT INTO Address ( city, `streetName`, `streetNumber`)
VALUES("Gothenburg", "Waernsgatan", "3B");

INSERT INTO Address ( city, `streetName`, `streetNumber`)
VALUES("Gothenburg", "Waernsgatan", "3C");

INSERT INTO Address ( city, `streetName`, `streetNumber`)
VALUES("Gothenburg", "Waernsgatan", "5A");

INSERT INTO Address ( city, `streetName`, `streetNumber`)
VALUES("Gothenburg", "Waernsgatan", "5B");

INSERT INTO Address ( city, `streetName`, `streetNumber`)
VALUES("Gothenburg", "Waernsgatan", "5C");





INSERT INTO Resident(ssn, `firstName`, `lastName`)
VALUES("197607261992", "Anders", "Rapp");

INSERT INTO Resident(ssn, `firstName`, `lastName`)
VALUES("197606132905", "Kristina", "Rapp");

INSERT INTO Resident(ssn, `firstName`, `lastName`)
VALUES("196006069666", "James", "Hetfield");



INSERT INTO ContactInformation(residentId, telephone, email)
VALUES(1, "+46703305805", "anders.rapp@gmail.com");

INSERT INTO ContactInformation(residentId, telephone, email)
VALUES(2, "+46706893068", "kristina.rapp@gmail.com");

INSERT INTO ContactInformation(residentId, telephone, email)
VALUES(3, "+1 8547452321", "james.hetfield@metallica.com");



INSERT INTO Apartment(`apartmentNumber`, address, `roomCount`, area, `floorCode`, share)
VALUES(56, 6, 3, 74, 1201, 3.2);

INSERT INTO Apartment(`apartmentNumber`, address, `roomCount`, area, `floorCode`, share)
VALUES(55, 6, 3, 73, 1101, 3.1);

INSERT INTO Apartment(`apartmentNumber`, address, `roomCount`, area, `floorCode`, share)
VALUES(57, 6, 3, 76, 1301, 3.0);

INSERT INTO Residency(apartment, resident,`fromDate`)
VALUES(1, 1, 20120626);

INSERT INTO Residency(apartment, resident,`fromDate`)
VALUES(1, 2, 20120626);

INSERT INTO Residency(apartment, resident,`fromDate`, `toDate`)
VALUES(1, 3, 20060606, 20070707);

INSERT INTO Commitment(`role`, resident, `fromDate`, authorized)
VALUES("chairman", 1, 20140414, true);

INSERT INTO Commitment(`role`, resident, `fromDate`, `toDate`,authorized)
VALUES("chairman", 3, 20060701, 20060930, true);

INSERT INTO Commitment(`role`, resident, `fromDate`, `toDate`,authorized)
VALUES("secretary", 2, 20120101, 20121231, true);

INSERT INTO Commitment(`role`, resident, `fromDate`, `toDate`,authorized)
VALUES("member", 1, 20061001, 20061231, true);
