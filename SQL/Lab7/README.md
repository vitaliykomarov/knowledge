```sql
--1. A VIEW is which of the following:
/*
A. Views are created by defining the SELECT statement
B. The table that provide the data are called base tables
C. The view itself holds data.
D. They can be queried, updated, and dropped
E. Any changes are made to the originating table(s)
*/

Answer: A,B,D,E

--2. Write a select query for a VIEW for the following columns:
-- ,[Title]
-- ,[FirstName]
-- ,[MiddleName]
-- ,[LastName]
-- ,[Suffix]
--FROM [AdventureWorks2019].[Person].[Person]
USE AdventureWorks2019
GO

CREATE VIEW VWPerson
AS
SELECT
[Title],
[FirstName],
[MiddleName],
[LastName],
[Suffix]
FROM [AdventureWorks2019].[Person].[Person]

SELECT * FROM VWPerson

DROP VIEW VWPerson

--3. Write a CASE statement for the following select statement giving a 'Give Raise' to the 'Information Services' and 'No Raise' to all others

SELECT [DepartmentID],
[Name],
[GroupName],
[ModifiedDate]
FROM [AdventureWorks2019].[HumanResources].[Department]

SELECT [DepartmentID],
[NAME],
CASE [Name]
WHEN 'Information Services' THEN 'Give Raise'
ELSE 'No Raise'
END AS 'BONUS',
[GroupName],
[ModifiedDate]
FROM [AdventureWorks2019].[HumanResources].[Department]
```
