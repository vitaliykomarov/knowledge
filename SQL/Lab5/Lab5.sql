--1. Find the SUM, MIN, MAX, COUNT of [AdventureWorks2019].[Sales].[SalesOrderDetail]
USE AdventureWorks2019
GO

SELECT 
[SalesOrderID],
SUM (OrderQty) AS TotalQty,
MIN (OrderQty) AS MinQty,
MAX (OrderQty) AS MaxQty,
Count (*) AS TotalQty
FROM
[AdventureWorks2019].[Sales].[SalesOrderDetail]
GROUP BY [SalesOrderID]

--2. What is a Null value?
Answer: NULL values permit entry of a blank data when the value at the time of entry is unknown

--3. The purpose of ISNULL() is to replace a NULL value with another value; Write a query that replaces a Null value with a 'NoMiddleName'for column MiddleName
--in the following table: [AdventureWorks2019].[Person].[Person]
SELECT 
MiddleName,
ISNULL (MiddleName, 'NoMiddleName') AS ReplaceNullValues
FROM [AdventureWorks2019].[Person].[Person]

--4. Use Alter command to add a column called 'Country' to the [TSQL].[dbo].[Names] table with the appropriate data type
USE TSQL
GO

ALTER TABLE Employee
ADD Country varchar(20)

SELECT * 
FROM Employee

--5. What is the total quantaties sold for the item SalesOrderID = '43659'? Use the following table: [AdventureWorks2019].[Sales].[SalesOrderDetail]
USE AdventureWorks2019
GO

SELECT 
SalesOrderID,
COUNT (OrderQty) TotalQty
FROM [AdventureWorks2019].[Sales].[SalesOrderDetail]
WHERE SalesOrderID = '43659'
GROUP BY SalesOrderID
ORDER BY SalesOrderID

--6. What is the total quantaties sold and total count of orders for each SalesOrderID greater than 30? Use the following table: [AdventureWorks2019].[Sales].[SalesOrderDetail].
--Hint: Use the Having Clause

SELECT
SalesOrderID,
SUM ([OrderQty]) AS TotalQty, 
COUNT (*) as TotalOrders
FROM [AdventureWorks2019].[Sales].[SalesOrderDetail]
GROUP BY SalesOrderID
HAVING COUNT(*) > 30
ORDER BY SalesOrderID