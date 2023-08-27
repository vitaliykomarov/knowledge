""" 
2- Write a program and ask the user to enter a few numbers separated by a hyphen. 
If the user simply presses Enter, without supplying an input, exit immediately; 
otherwise, check to see if there are duplicates. 
If so, display "Duplicate" on the console. 
"""

def Duplicate():
    numbers = input("Please enter a few numbers separated by a hyphen: ")
    if numbers == "":
        exit()
    else:
        numbersList = [int(num) for num in numbers.split('-')]
    if len(numbersList) != len(set(numbersList)):
        print("Duplicate")