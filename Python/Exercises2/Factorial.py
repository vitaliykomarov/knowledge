""" 
Note: for all these exercises, ignore input validation unless otherwise directed. 
Assume the user enters a value in the format that the program expects. 
For example, if the program expects the user to enter a number, 
don't worry about validating if the input is a number or not. 
When testing your program, simply enter a number.

3- Write a program and ask the user to enter a number. 
Compute the factorial of the number and print it on the console. 
For example, if the user enters 5, the program should calculate 5 x 4 x 3 x 2 x 1 and display it as 5! = 120. 
"""
#from math import factorial


number = int(input("Please enter a number: "))
fact = 1
if number == 0 or number == 1:
    print("Factorial of a number", number, "will be: 1")
else:
    for x in range(1, number+1):
        fact = fact * x
print("The factorial of",number,"is:", fact)


#print("The factorial of",number,"is:", factorial(number))