--1.Copy and paste the following create table command in your query pane.
Use AdventureWorks2019
Go

Create Table Names_Test
(Fname varchar (20),
Lname varchar (20),
State char (2),
City varchar (25),
Salary money)

--Then, using the Insert Into command, insert the following data:
--Fname:Mary
--Lname:Jones
--State:NY
--City:New York City
--Salary: 3500
INSERT INTO Names_Test
VALUES ('Mary', 'Jones', 'NY', 'New York City', 3500)

--2.Insert just the following values in the table Names_Test
--Fname:Joseph
--Salary: 4200
INSERT INTO Names_Test (Fname, Salary)
VALUES ('Joseph', 4200)

SELECT *
FROM Names_Test

--3.Use the table Names_Test and update the following:
--Update State from NY to CA
UPDATE Names_Test
SET [State] = 'CA'
WHERE [State] = 'NY'

--4.Use the table Names_Test and update the following:Update Lname from Jones to Smith and Update Salary from 3500 to 4200
UPDATE Names_Test
SET 
[Lname] = 'Smith',
[Salary] = 4200
WHERE [Lname] = 'Jones' AND [Salary] = 3500

--5.Using the IN operator, find all the data from the following table AdventureWorks2019.Person.Person where FirstName include: Gigi, David, Ken, Morgan
SELECT *
FROM [AdventureWorks2019].[Person].[Person]
WHERE [FirstName] IN ('Gigi','David','Ken','Morgan')
ORDER BY [FirstName]

--6.What is the difference between Delete and Truncate commands?
--Delete will delete all records from the table and not save any table space; 
--Delete can also be used with the where clause to filter data.
--Truncate deletes all the rows at one time and cannot use the where clause.
--Truncate releases the space back to the table

--7.Find all the products that range between products named 'M' and 'S'
SELECT *
FROM [AdventureWorks2019].[Production].[Product]
WHERE [Name] BETWEEN 'M' AND 'S'
ORDER BY [Name]