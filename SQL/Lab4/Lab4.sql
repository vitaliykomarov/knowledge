--1. HIGHLIGHT THE FOLLOWING SCRIPT UNTIL YOU REACH QUIZ 4, THEN PRESS F5.THEN TAKE THE TEST
USE TSQL
GO

--Create a table
CREATE TABLE Emp_Test
(NameID int NOT NULL IDENTITY (1, 1) PRIMARY KEY,--<< Note: This is the Primary Key for this table.
Fname varchar (20)NULL,
Lname varchar (20)NULL,
Gender char (1)NULL)

--Insert data into table names
INSERT INTO Emp_Test
VALUES
('Mary','Jones','F'),--1
('Todd','Smith','M'),--2
('Sam','Book','M'),--3
('Bill','Lamp','M'),--4
('Leslie','Keys','F'),--5
('Daniella','Horton','F')--6

--Create a second table
CREATE TABLE Emp_Depart
(DepartmentID int NOT NULL IDENTITY (1, 1)PRIMARY KEY,--<<Note: This is the Primary Key for this table
Department varchar (20),
BuildingCode varchar (20),
NameID int FOREIGN KEY REFERENCES Emp_Test (NameID) NULL)--<<Note: This is the Foreign Key in this table that has a relationship with Names table

--Insert records

INSERT INTO Emp_Depart
VALUES
('Sales','abc', 1),
('Accounts','xyz', 1),
('Purchasing','123', 2),
('Business','xyz', 2),
('Taxes','xyz', NULL),
('Finance','xxz', NULL),
('Order','zzz', NULL)

--Lab4

Select * from Emp_Test
Select * from Emp_Depart

--1. Which departments does Mary Jones belong to?Hint: Write an inner join with a where clause to retrieve Mary's data. Use the following tables Emp_Test, Emp_Depart
SELECT
Emp_Test.Fname,
Emp_Test.Lname,
Emp_Depart.Department
FROM
Emp_Test
INNER JOIN
Emp_Depart
ON Emp_Test.NameID = Emp_Depart.NameID
WHERE Emp_Test.Fname = 'Mary'

--2.Match the following join with the description:

--Example of INNER JOIN - 4
--Example of LEFT OUTER JOIN - 3
--Example of RIGHT OUTER JOIN - 2
--Example of FULL OUTER JOIN - 1

--1 GIVES US ALL THE DATA FROM BOTH TABLES EVEN IF THEY DONT MATCH
--2 GIVES US ALL THE DATA FROM THE SECOND TABLE EVEN WHEN THERE IS NO MATCH IN THE FIRST TABLE
--3 GIVES US ALL THE DATA FROM THE FIRST TABLE EVEN WHEN THERE IS NO MATCH IN THE SECOND TABLE
--4 GIVES US ALL THE DATA FROM THE FIRST TABLE WHEN THERE IS A MATCH IN THE SECOND TABLE

--3. Write a Full Join that eliminates Nulls. Hint: use the where clause to filter nulls. Use the following tables Emp_Test, Emp_Depart
SELECT
Emp_Test.Fname,
Emp_Test.Lname,
Emp_Depart.Department
FROM
Emp_Test
FULL OUTER JOIN
Emp_Depart
ON Emp_Test.NameID = Emp_Depart.NameID
WHERE Fname IS NOT NULL AND Lname IS NOT NULL AND Department IS NOT NULL

Drop table Emp_Test
Drop table Emp_Depart