--1.Write a query that selects all the columns from the table HumanResources.Department
SELECT *
FROM [AdventureWorks2019].[HumanResources].[Department]

--2.Write a query that selects the FirstName, MiddleName,LastName from the table Person.Personwhere the LastName has a pattern containing 'so'
SELECT
[FirstName],
[MiddleName],
[LastName]
FROM [AdventureWorks2019].[Person].[Person]
WHERE [LastName] Like '%so%';

--3.Table AdventureWorks2019.Sales.SalesOrderDetail has a column called SalesOrderID.How many distinct (non-duplicate fields) SalesOrderID are there?
SELECT
DISTINCT [SalesOrderID]
FROM [AdventureWorks2019].[Sales].[SalesOrderDetail]

--4.Select all the data from table AdventureWorks2019.Sales.SalesTerritory where the ID = 5 OR the Group is Europe
SELECT *
FROM [AdventureWorks2019].[Sales].[SalesTerritory]
WHERE [TerritoryID] = 5 OR [Group] = 'Europe';

--5.Sort the table [AdventureWorks2019].[Sales].[Currency] with the Name field in a descending order
SELECT *
FROM [AdventureWorks2019].[Sales].[Currency]
ORDER BY [Name] DESC;
