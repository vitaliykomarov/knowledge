USE AdventureWorks2019
GO

SELECT
Person.StateProvince.Name,
Sales.SalesTerritory.SalesLastYear,
Sales.SalesTerritory.TerritoryID
FROM
Person.StateProvince
INNER JOIN
Sales.SalesTerritory
ON Person.StateProvince.TerritoryID = Sales.SalesTerritory.TerritoryID
Order By Sales.SalesTerritory.TerritoryID

select TerritoryID,Name, SalesLastYear, SalesYTD ,*
from Sales.SalesTerritory
Order By 1

SELECT StateProvinceCode,Name, TerritoryID ,*
FROM
Person.StateProvince
Order By 3

--How many name columns begin with the letter 'A'
SELECT *
FROM Person.StateProvince
WHERE [Name] Like 'a%'


--Which region (Name) made the highest SalesYTD? Use a Subquery
SELECT * 
FROM Sales.SalesTerritory

SELECT *
FROM Sales.SalesTerritory
WHERE SalesYTD IN (SELECT MAX(SalesYTD) FROM Sales.SalesTerritory AS HighestSalesYTD)

--How many territories are there in the Northwest region?
SELECT * 
FROM Sales.SalesTerritory

SELECT
[Name],
COUNT(*) AS [TotalTerritories]
FROM Person.StateProvince
WHERE [TerritoryID] = 1
GROUP By [Name]

/*1. Which product was the most expensive in the [AdventureWorks2019].[Production].[Product] table?
Please give me the name, and the price of the product so that i can buy it for my wife? */
SELECT
MAX(StandardCost) AS [MaxCost]
FROM [AdventureWorks2019].[Production].[Product]

SELECT
[ProductID],
[Name],
MAX(StandardCost) AS [MaxCost]
FROM [AdventureWorks2019].[Production].[Product]
GROUP BY ProductID, [Name]
HAVING MAX(StandardCost) >= 2171.2942
ORDER BY [MaxCost] DESC

/*2. Review the [AdventureWorks2019].[Production].[ProductReview] table and can you tell me
what specfic product was John Smith praising about from the product table [AdventureWorks2019].[Production].[Product]
i'd like to get that product to for my trip*/
SELECT *
FROM [AdventureWorks2019].[Production].[ProductReview]
SELECT *
FROM [AdventureWorks2019].[Production].[Product]

SELECT
p.[Name], p.[ProductID],pr.[Comments]
FROM
[AdventureWorks2019].[Production].[ProductReview] as pr
INNER JOIN
[AdventureWorks2019].[Production].[Product] as p
ON p.ProductID = pr.ProductID
WHERE pr.ReviewerName = 'John Smith'

--3. I tried the Mountain Bike Socks and they were great! 
--Can you replace his comments from 'lightweight' 'increadibley light' in upper case
SELECT
p.[Name],
p.[ProductID],
REPLACE(pr.[Comments], 'lightweight', UPPER('increadibley light')) AS Comments
FROM
[AdventureWorks2019].[Production].[ProductReview] as pr
INNER JOIN
[AdventureWorks2019].[Production].[Product] as p
ON p.ProductID = pr.ProductID
WHERE pr.ReviewerName = 'John Smith'



---------------------------------------
Use TSQL
Go

Create Table People
(Fname varchar (20),
Lname varchar (20),
DateOfBirth varchar (20),
Gender char (1))

Insert Into People
Values ('Bob','Smith','02-01-1961','M'),
('Brandy','Jones','04-16-1981','F'),
('Leslie','Mellon','05-01-2000','F'),
('Henry','Wonders','02-01-1961','M'),
('Jack','Palmer','07-10-1941','M')

Select * From People

---what is the age of Henry in months,days,mins?
SELECT
Fname,
Lname,
DateOfBirth,
DATEDIFF(DAY, DateOfBirth, GETDATE()) AS AgeInDays,
DATEDIFF(MONTH, DateOfBirth, GETDATE()) AS AgeInMonth,
DATEDIFF(MINUTE, DateOfBirth, GETDATE()) AS AgeInMinute
FROM People
WHERE Fname = 'Henry'

--Select all those people who are female and change the F to Female
SELECT
Fname,
Lname,
Gender,
CASE
WHEN Gender = 'F' THEN 'Female'
ELSE'Male'
END AS 'Females'
FROM People
WHERE Gender = 'F'