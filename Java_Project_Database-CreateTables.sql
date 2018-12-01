/********************************************************************************/
/*																				*/
/*	Alejandro Tenorio, Daniel Kim												*/
/*	Application Development														*/
/*	Professor Aria																*/
/*	Java Project Databse														*/
/*	This SQL script creates the tables needed for our project 					*/
/*	Nov. 24, 2018          														*/
/*																				*/
/********************************************************************************/

USE java_project_database_master;

CREATE TABLE CUSTOMER (
	CustomerID			Integer					NOT NULL			UNIQUE,
	FirstName			Varchar (45)			NOT NULL,
    LastName			Varchar (45)			NOT NULL,
    StreetAddress		Varchar (45)			NOT NULL,
    City				Varchar (45)			NOT NULL,
    State				Varchar (45)			NOT NULL,
    ZipCode				Integer					NOT NULL,
    Email				Varchar (45)			NOT NULL			UNIQUE,
    SSN					Integer					NOT NULL			UNIQUE,
    CONSTRAINT			CUSTOMER_PK				PRIMARY KEY (CustomerID)
);

CREATE TABLE USERS (
	UserID				Integer					NOT NULL			UNIQUE,
    CustomerID			Integer					NOT NULL			UNIQUE,
    UserName			Varchar	(45)			NOT NULL			UNIQUE,
    PasswordAsHash		Varchar	(45)			NOT NULL,
    isAdmin				TinyInt					NOT NULL,
    CONSTRAINT			USERS_PK				PRIMARY KEY (UserID, CustomerID, UserName),
    CONSTRAINT			USERS_CUSTOMER_ID_FK	FOREIGN KEY (CustomerID) REFERENCES CUSTOMER (CustomerID)
);

CREATE TABLE SECURITY_QUESTION (
	QuestionID			Integer					NOT NULL			UNIQUE,
    SecurityQuestion	Varchar (45)			NOT NULL,
    UserInputAnswer		Varchar (45)			NOT NULL,
    CustomerID			Integer					NOT NULL			UNIQUE,
    CONSTRAINT			SECURITY_QUESTION_PK	PRIMARY KEY (QuestionID),
    CONSTRAINT			SECURITY_QUESTION_FK	FOREIGN KEY (CustomerID) REFERENCES CUSTOMER (CustomerID)
);

CREATE TABLE FLIGHT (
	FlightID			Integer					NOT NULL			UNIQUE,
    Carrier				Varchar (45)			NOT NULL,
    DepartingCity		Varchar (45)			NOT NULL,
    DepartingDate		Date					NOT NULL,
    DepartingTime		time					NOT NULL,
    ArrivingCity		Varchar (45)			NOT NULL,
    ArrivalDate			Date					NOT NULL,
    ArrivalTime			Time					NOT NULL,
    PassengerLimit		Integer					NOT NULL,
    CONSTRAINT			FLIGHT_PK				PRIMARY KEY (FlightID)
);

CREATE TABLE FLIGHT_TICKET (
	TicketID			Integer					NOT NULL			UNIQUE,
    CustomerID			Integer					NOT NULL			UNIQUE,
    FlightID			Integer					NOT NULL			UNIQUE,
    PurchaseDate		DateTime				NOT NULL,
    CONSTRAINT 			FLIGHT_TICKET_PK		PRIMARY KEY (TicketID, CustomerID, FlightID),
    CONSTRAINT 			FL_TCKT_CUSTOMER_ID_FK	FOREIGN KEY (CustomerID) REFERENCES CUSTOMER (CustomerID),
    CONSTRAINT			FL_TCKT_FLIGHT_ID_FK	FOREIGN KEY (FlightID)	 REFERENCES FLIGHT (FlightID)
);