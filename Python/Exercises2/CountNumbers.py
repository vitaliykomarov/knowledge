""" 
Note: for all these exercises, ignore input validation unless otherwise directed. 
Assume the user enters a value in the format that the program expects. 
For example, if the program expects the user to enter a number, 
don't worry about validating if the input is a number or not. 
When testing your program, simply enter a number.

1- Write a program to count how many numbers between 1 and 100 are divisible by 3 with no remainder. 
Display the count on the console.
"""

count = int(0)
for number in range(1, 100):
    # count += 1
    # print(number)
    if number % 3 == 0:
        count += 1
print(count)