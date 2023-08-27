"""
Note: for all these exercises, ignore input validation unless otherwise directed.
Assume the user enters a value in the format that the program expects.
For example, if the program expects the user to enter a number,
don't worry about validating if the input is a number or not.
When testing your program, simply enter a number.

4- Write a program that picks a random number between 1 and 10.
Give the user 4 chances to guess the number.
If the user guesses the number, display “You won"; otherwise, display “You lost".
(To make sure the program is behaving correctly, you can display the secret number on the console first.)
"""

import random

attempts = int(3)
number = random.randrange(1, 11)
print(number)
print("You have 4 attempts to guess the number.")
guessNum = int(input("Please enter a number: "))

for attempts in reversed(range(4)):
    if guessNum == number:
        print("You won")
    elif attempts == 0:
        print("You lost")
    else:
        guessNum = int(input(f"Try again you still have {attempts} attempts: "))
