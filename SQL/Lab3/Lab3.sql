--1. When you create a simple database without specifying any settings, which system database is used?

Answer: Model Database

--2. Create a table for the following fields, select any name for the table, and assign the appropriate data types:
--NamesID --<< make this field disallow null values
--Fname
--Lname
--DateOfBirth
--City
--[State] --<< Limit this field to have only 2 characters
--Country

CREATE TABLE Person
(NamesID int NOT NULL,
Fname varchar(20) NULL,
Lname varchar(20) NULL,
DateOfBirth Date NULL,
City varchar(20) NULL,
[State] char(2) NULL,
Country varchar(20) NULL)


--3. Types of Constraints: Match the constraint to the definition

UNIQUE – 3
NOT NULL – 1
FOREIGN KEY – 5
DEFAULT – 2
CHECK - 6
PRIMARY KEY – 4

1. Specifies that a column cannot be blank
2. Uses a default value, when no other values is entered in the column
3. Provides a unique value for each row
4. Provides an unique identity for each record in a table
5. Provides the referential integrity of the data in one table to match values in another table
6. Ensures that the data entered in the column meets the condition set for that column


--4. How do you insure that a field cannot have a certain value?
--Write a script that will prevent the customerID from having a value less than zero

ALTER TABLE Customer
ADD CHECK (CustomerID > 0)

--5. Aliases (AS) provide a way of, which of the following

--1. Renames the column(s) for clarity
--2. Renames both the column(s) and the table(s) for clarity
--3. Renames the table(s) for clarity
--4. None of the above

Answer: 1 and 2