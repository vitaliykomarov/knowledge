""" 
Note: for all these exercises, ignore input validation unless otherwise directed. 
Assume the user enters a value in the format that the program expects. 
For example, if the program expects the user to enter a number, 
don't worry about validating if the input is a number or not. 
When testing your program, simply enter a number.

2- Write a program and continuously ask the user to enter a number or "ok" to exit. 
Calculate the sum of all the previously entered numbers and display it on the console.
"""
total = 0
while True:
    number = input("Please enter a number or \"ok\" to exit: ")
    if number.lower() == "ok":
        break
    else:
        total += int(number)
print("Sum of all entered numbers:",total)