

CREATE TABLE Person (
    Social_Security_Number VARCHAR(30) PRIMARY KEY,
    Name VARCHAR(100),
    Gender CHAR(1),
    Profession VARCHAR(50),
    Mailing_address VARCHAR(255),
    Email_address VARCHAR(100),
    Phone_number VARCHAR(15),
    on_mailing_list BIT DEFAULT 0
);

CREATE TABLE Client (
    Social_Security_Number VARCHAR(30),
    Doctor_Name VARCHAR(100),
    Doctor_Phone_number VARCHAR(15),
    Assigned_date DATE,
    PRIMARY KEY (Social_Security_Number),
    FOREIGN KEY (Social_Security_Number) REFERENCES Person(Social_Security_Number)
);

CREATE TABLE Volunteers (
    Social_Security_Number VARCHAR(30),
    Date_of_join DATE,
    Training_date DATE,
    Training_location VARCHAR(100),
    PRIMARY KEY (Social_Security_Number),
    FOREIGN KEY (Social_Security_Number) REFERENCES Person(Social_Security_Number)
);

CREATE TABLE Employees (
    Social_Security_Number VARCHAR(30),
    Salary DECIMAL(10, 2),
    Marital_status VARCHAR(20),
    Hire_date DATE,
    PRIMARY KEY (Social_Security_Number),
    FOREIGN KEY (Social_Security_Number) REFERENCES Person(Social_Security_Number)
);

CREATE TABLE Donors (
    Social_Security_Number VARCHAR(30),
    Anonymous BIT DEFAULT 0,
    PRIMARY KEY (Social_Security_Number),
    FOREIGN KEY (Social_Security_Number) REFERENCES Person(Social_Security_Number)
);


CREATE TABLE Emergency_contacts (
    Social_Security_Number VARCHAR(30),
    Phone_number VARCHAR(15),
    Name VARCHAR(100),
    Relationship VARCHAR(50),
    PRIMARY KEY (Social_Security_Number, Phone_number),
    FOREIGN KEY (Social_Security_Number) REFERENCES Person(Social_Security_Number)
);


CREATE TABLE Needs (
    Social_Security_Number VARCHAR(30),
    Need_Type VARCHAR(50),
    Importance INT,
    PRIMARY KEY (Social_Security_Number, Need_Type),
    FOREIGN KEY (Social_Security_Number) REFERENCES Client(Social_Security_Number)
);

CREATE TABLE Insurance (
    Social_Security_Number VARCHAR(30),
    Policy_id VARCHAR(20),
    Provider_name VARCHAR(100),
    Provider_address VARCHAR(255),
    Policy_Type VARCHAR(50),
    PRIMARY KEY (Social_Security_Number, Policy_id),
    FOREIGN KEY (Social_Security_Number) REFERENCES Client(Social_Security_Number)
);



CREATE TABLE Team (
    Team_Name VARCHAR(100) PRIMARY KEY,
    Team_Type VARCHAR(50),
    Formed_date DATE
);
CREATE INDEX idx_formed_date ON Team(Formed_date);



CREATE TABLE Expense (
    Social_Security_Number VARCHAR(30),
    Expense_date DATE,
    Amount DECIMAL(10, 2),
    Description VARCHAR(255),
    PRIMARY KEY (Social_Security_Number, Expense_date, Amount, Description),
    FOREIGN KEY (Social_Security_Number) REFERENCES Employees(Social_Security_Number)
);
CREATE INDEX idx_expense_date ON Expense(Expense_date);



CREATE TABLE Donor_Check (
    Social_Security_Number VARCHAR(30),
    Type_of_donation VARCHAR(50),
    Date DATE,
    Amount DECIMAL(10, 2),
    Name_of_fund_campaign VARCHAR(100),
    Check_Number VARCHAR(50),
    PRIMARY KEY (Social_Security_Number,Type_of_donation, Date , Amount),
    FOREIGN KEY (Social_Security_Number) REFERENCES Donors(Social_Security_Number)
);

CREATE TABLE Credit_card (
    Social_Security_Number VARCHAR(30),
    Type_of_donation VARCHAR(50),
    Date DATE,
    Amount DECIMAL(10, 2),
    Name_of_fund_campaign VARCHAR(100),
    Card_number VARCHAR(16),
    Card_Type VARCHAR(50),
    Expiration_date DATE,
    PRIMARY KEY (Social_Security_Number,Type_of_donation, Date , Amount),
    FOREIGN KEY (Social_Security_Number) REFERENCES Donors(Social_Security_Number)
);


CREATE TABLE Serves (
    Social_Security_Number VARCHAR(30),
    Team_Name VARCHAR(100),
    working_hours INT,
    active_or_inactive BIT DEFAULT 0,
    Month VARCHAR(15),
    PRIMARY KEY (Social_Security_Number,Team_Name),
    FOREIGN KEY (Social_Security_Number) REFERENCES Volunteers(Social_Security_Number),
    FOREIGN KEY (Team_Name) REFERENCES Team(Team_Name)
);

CREATE TABLE Team_leader (
    Social_Security_Number VARCHAR(30),
    Team_Name VARCHAR(100),
    PRIMARY KEY (Social_Security_Number, Team_Name),
    FOREIGN KEY (Social_Security_Number) REFERENCES Volunteers(Social_Security_Number),
    FOREIGN KEY (Team_Name) REFERENCES Team(Team_Name)
);


CREATE TABLE Reports (
    Team_Name VARCHAR(100),
    Social_Security_Number VARCHAR(30),
    date DATE,
    description VARCHAR(255),
    PRIMARY KEY (Team_Name,Social_Security_Number),
    FOREIGN KEY (Team_Name) REFERENCES Team(Team_Name),
    FOREIGN KEY (Social_Security_Number) REFERENCES Employees(Social_Security_Number)
);


CREATE TABLE Cares (
    Client_Social_Security_Number VARCHAR(30),
    Team_Name VARCHAR(100),
    active_or_inactive BIT DEFAULT 0,
    PRIMARY KEY (Client_Social_Security_Number,Team_Name),
    FOREIGN KEY (Client_Social_Security_Number) REFERENCES Client(Social_Security_Number),
    FOREIGN KEY (Team_Name) REFERENCES Team(Team_Name)
);


CREATE TABLE Policies (
    Policy_id VARCHAR(30) PRIMARY KEY,
    Social_Security_Number VARCHAR(30),
    FOREIGN KEY (Social_Security_Number) REFERENCES Client(Social_Security_Number)
);











