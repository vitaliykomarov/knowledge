""" 
Note: for all these exercises, ignore input validation unless otherwise directed. 
Assume the user enters a value in the format that the program expects. 
For example, if the program expects the user to enter a number, 
don't worry about validating if the input is a number or not. 
When testing your program, simply enter a number.

5- Write a program and ask the user to enter a series of numbers separated by comma. 
Find the maximum of the numbers and display it on the console. 
For example, if the user enters “5, 3, 8, 1, 4", the program should display 8.  
"""
numbers = input("Please enter a series of numbers separated by comma: ")
numbersList = [int(num.strip()) for num in numbers.split(',')]
maxNum = numbersList[0]
for num in numbersList:
    if num > maxNum:
        maxNum = num
print("Max number will be:",maxNum)
