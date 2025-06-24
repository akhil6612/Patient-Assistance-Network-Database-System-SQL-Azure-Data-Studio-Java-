/*Q1*/

DROP PROCEDURE IF EXISTS InsertTeam;
GO

CREATE PROCEDURE InsertTeam
    @Team_Name VARCHAR(100),
    @Team_Type VARCHAR(50),
    @Formed_date DATE
AS
BEGIN
    BEGIN TRY

        INSERT INTO Team (Team_Name, Team_Type, Formed_date)
        VALUES (@Team_Name, @Team_Type, @Formed_date);
    END TRY
    BEGIN CATCH

        SELECT 
            ERROR_MESSAGE() AS ErrorMessage;

    END CATCH
END
GO




/*Q2*/
DROP PROCEDURE IF EXISTS InsertClientAndAssociateTeams;
GO
CREATE PROCEDURE InsertClientAndAssociateTeams
    @Social_Security_Number VARCHAR(30),
    @Person_Name VARCHAR(100),
    @Gender CHAR(1),
    @Profession VARCHAR(100),
    @Mailing_address VARCHAR(255),
    @Email_address VARCHAR(255),
    @Phone_number VARCHAR(15),
    @on_mailing_list BIT,
    @Emergency_Phone_number VARCHAR(15),
    @Emergency_contact_Name VARCHAR(100),
    @Relationship VARCHAR(50),
    @Doctor_Name VARCHAR(100),
    @Doctor_Phone_number VARCHAR(15),
    @Assigned_date DATE,
    @Need_type VARCHAR(50),
    @Importance INT,
    @policyId VARCHAR(20),
    @providerName VARCHAR(100),
    @providerAddress VARCHAR(255),
    @policyType varchar(50)

AS
BEGIN
    BEGIN TRY
        
        IF NOT EXISTS (SELECT 1 FROM Person WHERE Social_Security_Number = @Social_Security_Number)
        BEGIN
            INSERT INTO Person (Social_Security_Number, Name, Gender, Profession, Mailing_address, Email_address, Phone_number, on_mailing_list)VALUES 
            (@Social_Security_Number, @Person_Name, @Gender, @Profession, @Mailing_address, @Email_address, @Phone_number, @on_mailing_list);
        END
         IF NOT EXISTS (SELECT 1 FROM Person WHERE Social_Security_Number = @Social_Security_Number AND Phone_number = @Emergency_Phone_number)
        BEGIN
        
            INSERT INTO Emergency_contacts (Social_Security_Number, Phone_number, Name, Relationship)VALUES (@Social_Security_Number, @Emergency_Phone_number, @Emergency_contact_Name, @Relationship);
        END
       
        IF NOT EXISTS (SELECT 1 FROM Client WHERE Social_Security_Number = @Social_Security_Number)
        BEGIN
            INSERT INTO Client (Social_Security_Number, Doctor_Name, Doctor_Phone_number, Assigned_date)VALUES (@Social_Security_Number, @Doctor_Name, @Doctor_Phone_number, @Assigned_date);
        END
        INSERT INTO Needs (Social_Security_Number, Need_Type, Importance)VALUES(@Social_Security_Number, @Need_type, @Importance);

        INSERT INTO Insurance (Social_Security_Number, Policy_id, Provider_name, Provider_address, Policy_Type)VALUES(@Social_Security_Number, @policyId, @providerName, @providerAddress, @policyType);

    END TRY
    BEGIN CATCH
        SELECT ERROR_MESSAGE() AS ErrorMessage;
    END CATCH
END
GO



DROP PROCEDURE IF EXISTS InsertIntoCares;
GO

CREATE PROCEDURE InsertIntoCares
    @Client_Social_Security_Number VARCHAR(30),
    @Team_Name VARCHAR(100),
    @active_or_inactive BIT
AS
BEGIN
    BEGIN TRY
    
        INSERT INTO Cares (Client_Social_Security_Number, Team_Name, active_or_inactive)
        VALUES (@Client_Social_Security_Number, @Team_Name, @active_or_inactive);
    END TRY
    BEGIN CATCH
        SELECT ERROR_MESSAGE() AS ErrorMessage;
    END CATCH
END
GO






/*Q3*/

DROP PROCEDURE IF EXISTS InsertVolunteerAndAssociateTeams;
GO

CREATE PROCEDURE InsertVolunteerAndAssociateTeams
    @Social_Security_Number VARCHAR(30),
    @Person_Name VARCHAR(100),
    @Gender CHAR(1),
    @Profession VARCHAR(100),
    @Mailing_address VARCHAR(255),
    @Email_address VARCHAR(255),
    @Phone_number VARCHAR(15),
    @on_mailing_list BIT,
    @Emergency_Phone_number VARCHAR(15),
    @Emergency_contact_Name VARCHAR(100),
    @Relationship VARCHAR(50),
    @Date_of_join DATE,
    @Training_date DATE,
    @Training_location VARCHAR(100)
AS
BEGIN
    BEGIN TRY
       
        IF NOT EXISTS (SELECT 1 FROM Person WHERE Social_Security_Number = @Social_Security_Number)
        BEGIN
            INSERT INTO Person (Social_Security_Number, Name, Gender, Profession, Mailing_address, Email_address, Phone_number, on_mailing_list)
            VALUES (@Social_Security_Number, @Person_Name, @Gender, @Profession, @Mailing_address, @Email_address, @Phone_number, @on_mailing_list);
        END

        IF NOT EXISTS (SELECT 1 FROM Emergency_contacts WHERE Social_Security_Number = @Social_Security_Number AND Phone_number = @Emergency_Phone_number)
        BEGIN
            INSERT INTO Emergency_contacts (Social_Security_Number, Phone_number, Name, Relationship)VALUES (@Social_Security_Number, @Emergency_Phone_number, @Emergency_contact_Name, @Relationship);
        END

        IF NOT EXISTS (SELECT 1 FROM Volunteers WHERE Social_Security_Number = @Social_Security_Number)
        BEGIN
            INSERT INTO Volunteers (Social_Security_Number, Date_of_join, Training_date, Training_location)VALUES (@Social_Security_Number, @Date_of_join, @Training_date, @Training_location);
        END
    END TRY
    BEGIN CATCH
        SELECT 
            ERROR_MESSAGE() AS ErrorMessage;
    END CATCH
END
GO

DROP PROCEDURE IF EXISTS InsertIntoServes;
GO

CREATE PROCEDURE InsertIntoServes
    @Social_Security_Number VARCHAR(30),
    @Team_Name VARCHAR(100),
    @Working_Hours INT,
    @active_or_inactive BIT,
    @Month VARCHAR(15)
AS
BEGIN
    BEGIN TRY
        
        INSERT INTO Serves (Social_Security_Number, Team_Name, working_hours, active_or_inactive, Month)
        VALUES (@Social_Security_Number, @Team_Name, @Working_Hours, @active_or_inactive, @Month);
    END TRY
    BEGIN CATCH
        SELECT ERROR_MESSAGE() AS ErrorMessage;
    END CATCH
END
GO

/*Q4*/
DROP PROCEDURE IF EXISTS UpdateVolunteerWorkingHours;
GO


CREATE PROCEDURE UpdateVolunteerWorkingHours
    @Social_Security_Number VARCHAR(30),
    @Team_Name VARCHAR(100),
    @Working_Hours INT,
    @active_or_inactive BIT,
    @Month VARCHAR(30)
AS
BEGIN
    BEGIN TRY
        UPDATE Serves
        SET working_hours = @Working_Hours
        WHERE Social_Security_Number = @Social_Security_Number
        AND Team_Name = @Team_Name
        AND Month = @Month
        AND active_or_inactive = 1;

    END TRY
    BEGIN CATCH
        
        SELECT 
            ERROR_MESSAGE() AS ErrorMessage;
    END CATCH
END
GO


/*Q5*/
DROP PROCEDURE IF EXISTS InsertEmployee;
GO

CREATE PROCEDURE InsertEmployee
    @Social_Security_Number VARCHAR(30),
    @Person_Name VARCHAR(100),
    @Gender CHAR(1),
    @Profession VARCHAR(100),
    @Mailing_address VARCHAR(255),
    @Email_address VARCHAR(255),
    @Phone_number VARCHAR(15),
    @on_mailing_list BIT,
    @Emergency_Phone_number VARCHAR(15),
    @Emergency_contact_Name VARCHAR(100),
    @Relationship VARCHAR(50),
    @Salary DECIMAL(10, 2),
    @Marital_status VARCHAR(30),
    @Hire_date DATE
     
AS
BEGIN
    BEGIN TRY
         
        IF NOT EXISTS (SELECT 1 FROM Person WHERE Social_Security_Number = @Social_Security_Number)
        BEGIN
            INSERT INTO Person (Social_Security_Number, Name, Gender, Profession, Mailing_address, Email_address, Phone_number, on_mailing_list)
            VALUES (@Social_Security_Number, @Person_Name, @Gender, @Profession, @Mailing_address, @Email_address, @Phone_number, @on_mailing_list);
        END
        IF NOT EXISTS (SELECT 1 FROM Emergency_contacts WHERE Social_Security_Number = @Social_Security_Number AND Phone_number = @Emergency_Phone_number)
        BEGIN
            INSERT INTO Emergency_contacts (Social_Security_Number, Phone_number, Name, Relationship)
            VALUES (@Social_Security_Number, @Emergency_Phone_number, @Emergency_contact_Name, @Relationship);
        END

        IF NOT EXISTS (SELECT 1 FROM Employees WHERE Social_Security_Number = @Social_Security_Number)
        BEGIN
            
            INSERT INTO Employees (Social_Security_Number, Salary, Marital_status, Hire_date)
            VALUES (@Social_Security_Number, @Salary, @Marital_status, @Hire_date);
        END
        
        
    END TRY
    BEGIN CATCH
        SELECT 
            ERROR_MESSAGE() AS ErrorMessage;
    END CATCH
END
GO

DROP PROCEDURE IF EXISTS InsertIntoReports;
GO

CREATE PROCEDURE InsertIntoReports
    @Social_Security_Number VARCHAR(30),
    @Team_Name VARCHAR(100),
    @date DATE,
    @description VARCHAR(100)
AS
BEGIN
    BEGIN TRY
        
        INSERT INTO Reports (Team_Name, Social_Security_Number, date, description)
        VALUES (@Team_Name, @Social_Security_Number, @date, @description);
        
    END TRY
    BEGIN CATCH
        SELECT ERROR_MESSAGE() AS ErrorMessage;
    END CATCH
END
GO


/*Q6*/

DROP PROCEDURE IF EXISTS InsertExpenseByAnEmployee;
GO

CREATE PROCEDURE InsertExpenseByAnEmployee
    @Social_Security_Number VARCHAR(30),
    @Expense_date DATE,
    @Amount DECIMAL(10, 2),
    @Description VARCHAR(100)
    
AS
BEGIN
    BEGIN TRY
        
       INSERT INTO Expense (Social_Security_Number, Expense_date, Amount, Description)
       VALUES (@Social_Security_Number,@Expense_date, @Amount, @Description);  

    END TRY
    BEGIN CATCH
        
        SELECT 
            ERROR_MESSAGE() AS ErrorMessage;
    END CATCH
END
GO
/*Q7*/
DROP PROCEDURE IF EXISTS DonorDonations;
GO

CREATE PROCEDURE DonorDonations
    @Social_Security_Number VARCHAR(50),
    @Person_Name VARCHAR(100),
    @Gender CHAR(1),
    @Profession VARCHAR(100),
    @Mailing_address VARCHAR(255),
    @Email_address VARCHAR(255),
    @Phone_number VARCHAR(15),
    @on_mailing_list BIT,
    @Emergency_Phone_number VARCHAR(15),
    @Emergency_contact_Name VARCHAR(100),
    @Relationship VARCHAR(50),
    @Anonymous BIT,
    @Type_of_dontion VARCHAR(100),
    @Date DATE,
    @Amount DECIMAL(10, 2),
    @Name_of_fund_campaign VARCHAR(100),
    @Check_Number VARCHAR(30),
    @Card_number VARCHAR(30),
    @Card_Type VARCHAR(30),
    @Expiration_Date DATE
AS
BEGIN
    BEGIN TRY
       
        IF NOT EXISTS (SELECT 1 FROM Person WHERE Social_Security_Number = @Social_Security_Number)
        BEGIN
            INSERT INTO Person (Social_Security_Number, Name, Gender, Profession, Mailing_address, Email_address, Phone_number, on_mailing_list)
            VALUES (@Social_Security_Number, @Person_Name, @Gender, @Profession, @Mailing_address, @Email_address, @Phone_number, @on_mailing_list);
        END
        IF NOT EXISTS (SELECT 1 FROM Emergency_contacts WHERE Social_Security_Number = @Social_Security_Number AND Phone_number = @Emergency_Phone_number)
        BEGIN
            INSERT INTO Emergency_contacts (Social_Security_Number, Phone_number, Name, Relationship)VALUES (@Social_Security_Number, @Emergency_Phone_number, @Emergency_contact_Name, @Relationship);
        END
        IF NOT EXISTS (SELECT 1 FROM Donors WHERE Social_Security_Number = @Social_Security_Number)
        BEGIN
            INSERT INTO Donors (Social_Security_Number, Anonymous) VALUES (@Social_Security_Number, @Anonymous);
        END
       -- associating several donations
        IF @Type_of_dontion = 'check'
            BEGIN
                INSERT INTO Donor_Check (Social_Security_Number, Type_of_donation, Date, Amount, Name_of_fund_campaign, Check_Number)
                VALUES 
                (@Social_Security_Number, @Type_of_dontion, @Date, @Amount, @Name_of_fund_campaign, @Check_Number);
            END
        ELSE
            BEGIN
                INSERT INTO Credit_card (Social_Security_Number, Type_of_donation, Date, Amount, Name_of_fund_campaign, Card_number, Card_Type, Expiration_date)
                VALUES 
                (@Social_Security_Number, @Type_of_dontion, @Date, @Amount, @Name_of_fund_campaign, @Card_number,@Card_Type, @Expiration_Date);
            END
    END TRY
    BEGIN CATCH
       
        SELECT 
            ERROR_MESSAGE() AS ErrorMessage;
    END CATCH
END
GO



/*Q8*/

DROP PROCEDURE IF EXISTS RetrieveClient;
GO

CREATE PROCEDURE RetrieveClient
    @Social_Security_Number VARCHAR(30)
AS
BEGIN
    BEGIN TRY
        -- retrieve client
        SELECT Doctor_Name, Doctor_Phone_number
        FROM Client
        WHERE Social_Security_Number = @Social_Security_Number;
 

    END TRY
    BEGIN CATCH
        SELECT 
            ERROR_MESSAGE() AS ErrorMessage;
    END CATCH
END
GO





/*Q9*/
DROP PROCEDURE IF EXISTS GetTotalExpensesByEmployee;
GO
CREATE PROCEDURE GetTotalExpensesByEmployee
    @start_date DATE,
    @end_date DATE
    
AS
BEGIN
    BEGIN TRY
      
      SELECT Social_Security_Number, SUM(Amount) AS Total_Expenses
      FROM Expense
      WHERE Expense_date BETWEEN @start_date AND @end_date  
      GROUP BY Social_Security_Number
      ORDER BY Total_Expenses DESC;

    END TRY
    BEGIN CATCH
        
        SELECT 
            ERROR_MESSAGE() AS ErrorMessage;
    END CATCH
END
GO


/*Q10*/
DROP PROCEDURE IF EXISTS GetVolunteersByClient;
GO

CREATE PROCEDURE GetVolunteersByClient
    @Client_Social_Security_Number VARCHAR(30) 
AS
BEGIN
    BEGIN TRY
        
        SELECT DISTINCT Social_Security_Number
        FROM Serves
        WHERE Team_Name IN (
        SELECT Team_Name
        FROM Cares
        WHERE Client_Social_Security_Number = @Client_Social_Security_Number
        AND active_or_inactive = 1
        ) 
        
    END TRY
    BEGIN CATCH

        SELECT ERROR_MESSAGE() AS ErrorMessage;

    END CATCH
END;
GO

/*Q11*/

DROP PROCEDURE IF EXISTS NamesOfTeams;
GO

CREATE PROCEDURE NamesOfTeams
    @formed_date DATE
AS
BEGIN
    BEGIN TRY
        
        SELECT Team_Name
        FROM Team
        WHERE Formed_date > @formed_date; 
        

    END TRY
    BEGIN CATCH
        SELECT 
            ERROR_MESSAGE() AS ErrorMessage;
    END CATCH
END
GO


/*Q12*/
DROP PROCEDURE IF EXISTS PeopleInformation;
GO

CREATE PROCEDURE PeopleInformation
   
AS
BEGIN
    BEGIN TRY
        
        SELECT 
            p.Name, p.Social_Security_Number, p.Phone_number, p.Email_address, 
            e.Name AS Emergency_Contact_Name,e.Phone_number AS Emergency_Contact_Phone,e.Relationship AS Emergency_Contact_Relationship
        FROM 
            Person p
        LEFT JOIN 
            Emergency_contacts e 
        ON 
            p.Social_Security_Number = e.Social_Security_Number;
       
    END TRY
    BEGIN CATCH
        SELECT 
            ERROR_MESSAGE() AS ErrorMessage;
    END CATCH
END
GO



/*Q13*/
DROP PROCEDURE IF EXISTS TotalAmountDonatedByDonors;
GO

CREATE PROCEDURE TotalAmountDonatedByDonors
AS
BEGIN
    BEGIN TRY
        SELECT 
            p.Name AS Donor_Name,
            ISNULL(dc.Total_Check, 0) + ISNULL(cc.Total_Credit, 0) AS Total_Donated,
            d.Anonymous
        FROM 
            Donors d
        JOIN 
            Person p ON p.Social_Security_Number = d.Social_Security_Number
        JOIN 
            Employees e ON e.Social_Security_Number = d.Social_Security_Number
        LEFT JOIN 
            (SELECT Social_Security_Number, SUM(Amount) AS Total_Check
             FROM Donor_Check
             GROUP BY Social_Security_Number) dc ON d.Social_Security_Number = dc.Social_Security_Number
        LEFT JOIN 
            (SELECT Social_Security_Number, SUM(Amount) AS Total_Credit
             FROM Credit_card
             GROUP BY Social_Security_Number) cc ON d.Social_Security_Number = cc.Social_Security_Number
        ORDER BY 
            Total_Donated DESC;
    END TRY
    BEGIN CATCH
        SELECT ERROR_MESSAGE() AS ErrorMessage;
    END CATCH
END
GO




/*Q14*/
DROP PROCEDURE IF EXISTS IncreaseEmployeesSalary;
GO

CREATE PROCEDURE IncreaseEmployeesSalary
   
AS
BEGIN
    BEGIN TRY
        
        UPDATE Employees
        SET Salary = Salary * 1.10
        WHERE Social_Security_Number IN (
            SELECT Social_Security_Number
            FROM Reports
            GROUP BY Social_Security_Number
            HAVING COUNT(Team_Name) > 1
            );
        

    END TRY
    BEGIN CATCH
      
        SELECT 
            ERROR_MESSAGE() AS ErrorMessage;
    END CATCH
END
GO


/*Q15*/

DROP PROCEDURE IF EXISTS DeleteClients;
GO

CREATE PROCEDURE DeleteClients
AS
BEGIN
    BEGIN TRY
        --temporary table to to delete clients
        CREATE TABLE #AllClientsToDelete (Social_Security_Number VARCHAR(15));

        INSERT INTO #AllClientsToDelete (Social_Security_Number)

        SELECT Social_Security_Number
        FROM Needs
        WHERE Need_Type = 'Transportation' 
          AND Importance < 5 AND Social_Security_Number NOT IN (SELECT Social_Security_Number FROM Insurance WHERE Policy_Type = 'Health');

        --delete all clients who met the above conditions
        DELETE FROM Needs
        WHERE Social_Security_Number IN (SELECT Social_Security_Number FROM #AllClientsToDelete);

        DELETE FROM Insurance
        WHERE Social_Security_Number IN (SELECT Social_Security_Number FROM #AllClientsToDelete);

        DELETE FROM Cares
        WHERE Client_Social_Security_Number IN (SELECT Social_Security_Number FROM #AllClientsToDelete);

        DELETE FROM Client
        WHERE Social_Security_Number IN (SELECT Social_Security_Number FROM #AllClientsToDelete);

        DROP TABLE #AllClientsToDelete;

    END TRY
    BEGIN CATCH
        SELECT ERROR_MESSAGE() AS ErrorMessage;
    END CATCH
END;
GO