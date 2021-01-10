```sql
--1. Using the Select Into command, make a copy of the following table: [AdventureWorks2019].[Sales].[SalesTerritory]
SELECT *
INTO [AdventureWorks2019].[Sales].[SalesTerritory_201211]
FROM [AdventureWorks2019].[Sales].[SalesTerritory]

--2. Which product sold 2 or more quantities? Use a subquery for columns PRODUCTID, PRODUCT_NAME, PRODUCT_DECS with the following tables:PRODUCTS and SALES

CREATE DATABASE SUBQUERY
GO

USE SUBQUERY
GO

CREATE TABLE PRODUCTS
(
PRODUCTID INT IDENTITY PRIMARY KEY NOT NULL,
PRODUCT_NAME VARCHAR (100) NULL,
PRODUCT_DECS VARCHAR (125) NULL)

CREATE TABLE SALES
(
SALESID INT IDENTITY PRIMARY KEY NOT NULL,
PRODUCTID INT FOREIGN KEY REFERENCES PRODUCTS (PRODUCTID),
PRODUCT_PRICE INT NULL,
PRODUCT_QTY INT NULL)

SELECT * FROM PRODUCTS
SELECT * FROM SALES

INSERT INTO PRODUCTS
VALUES
('HARLEY DAVIDSON','HARLEY BIKE THAT HAS TOP SPEED OF 180 MPH'),
('AUSTIN MARTIN','TOP JAMES BOND MODEL OF CHOICE'),
('SESNA','SINGLE ENGINE PLANE, EASY TO FLY')

INSERT INTO SALES (PRODUCTID, PRODUCT_PRICE, PRODUCT_QTY)
VALUES
(2, 125000, 5),
(3, 155000, 1),
(2, 125000, 5),
(3, 125000, 2),
(2, 125000, 1)

SELECT * FROM PRODUCTS
SELECT * FROM SALES

SELECT 
PRODUCTID,
PRODUCT_NAME,
PRODUCT_DECS 
FROM PRODUCTS 
WHERE PRODUCTID IN (SELECT PRODUCTID FROM SALES WHERE PRODUCT_QTY >= 2)

--3. What is the purpose of data types?
Answer: Data types determine what kind of information is to be stored in the intended column

--4. If you insert data into a datatype char(3), the value 'ABCD', why does it error out?
Answer: It will error out as the char data type size char(3) can only hold 3 values and not 4

--5.What is the difference between Standard characters and Unicode characters?
Answer:
The primary difference between Standard and Unicode characters is the storage requirements.
Standard uses a single byte of space and Unicode uses two bytes of space.

--For question 6, I have created these tables for the next question:

Use TSQL
Go

Create Table Source
(Text1 varchar (9))

Insert Into Source
Values('ABCDEFGHI')--<< 9 characters

Select * From Source

Select Text1,Len(Text1) AS SourceLength
From Source

Create Table Destination
(Text1 varchar (5))

Select * From Destination


--6. When you run the following script, it will error out because the destination table has a limit of 5 characters

Insert Into Destination
Values('ABCDEFGHI')


--6. The example above illustrates that we are trying to move 9 characters of data into a destination columns that has a max of 5 characters.
--Write a script the will allow this insert into destination command? Hint: use substring to remove the last five characters
INSERT INTO Destination
SELECT SUBSTRING(Text1,1,4) AS FOURVALUES
FROM Source


--7. Show the text1 column in lower case.
SELECT 
Text1, 
LOWER(Text1) as LowerText
FROM Source


--8. Use the function to get today's date? And, what date, month, and day will it be in 175 days?
SELECT GETDATE() AS TodaysDate

Select 
GETDATE()+ 175 AS DateIn175Days,
DATENAME (MONTH, GETDATE()+175) AS [Months],
DATENAME (DAY,  GETDATE()+175) AS [Days],
DATENAME (WEEKDAY,GETDATE()+ 175) AS [WEEKDAY]


--9. using Convert, change 'Select GETDATE() AS TodaysDate' to different styles 112, 111, 110, 113

SELECT GETDATE() AS TodaysDate
SELECT CONVERT(varchar (20), GETDATE(), 110) AS TodaysDate
SELECT CONVERT(varchar (20), GETDATE(), 111) AS TodaysDate
SELECT CONVERT(varchar (20), GETDATE(), 112) AS TodaysDate
SELECT CONVERT(varchar (20), GETDATE(), 113) AS TodaysDate
```