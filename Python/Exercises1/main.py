""" 
Note: for all these exercises, ignore input validation unless otherwise directed. 
Assume the user enters a value in the format that the program expects. 
For example, if the program expects the user to enter a number, 
don't worry about validating if the input is a number or not. 
When testing your program, simply enter a number.
"""

# 1- Write a program and ask the user to enter a number.
# The number should be between 1 to 10.
# If the user enters a valid number, display "Valid" on the console.
# Otherwise, display "Invalid".
# (This logic is used a lot in applications where values entered into input boxes need to be validated.)
number = int(input("Please enter a number from 1 to 10: "))
if (number <= 10 and number >= 1):
    print("Valid")
else:
    print("Invalid")

# 2- Write a program which takes two numbers from the console and displays the maximum of the two.
firstNumber = int(input("Please enter the first number: "))
secondNumber = int(input("Please enter the second number: "))
if firstNumber > secondNumber:
    print("The first number", firstNumber,
          "is bigger than second number", secondNumber)
elif secondNumber > firstNumber:
    print("The second number", secondNumber,
          "is bigger than first number", firstNumber)
else:
    print("Both numbers are equal", firstNumber, "=", secondNumber)

# 3- Write a program and ask the user to enter the width and height of an image.
# Then tell if the image is landscape or portrait.
height = int(input("Please enter the height of the photo: "))
width = int(input("Please enter the width of the photo: "))
if height > width:
    print("This is a portrait photo.")
elif width > height:
    print("This is a landscape photo.")
else:
    print("This is a square photo")

# 4- Your job is to write a program for a speed camera.
# For simplicity, ignore the details such as camera, sensors, etc and focus purely on the logic.
# Write a program that asks the user to enter the speed limit.
# Once set, the program asks for the speed of a car.
# If the user enters a value less than the speed limit, program should display Ok on the console.
# If the value is above the speed limit, the program should calculate the number of demerit points.
# For every 5km/hr above the speed limit, 1 demerit points should be incurred and displayed on the console.
# If the number of demerit points is above 12, the program should display License Suspended.
speedLimit = int(input("Please enter the speed limit: "))
carSpeed = int(input("Please enter the car speed: "))
if carSpeed <= speedLimit:
    print("Ok")
else:
    demeritPoints = int((carSpeed - speedLimit) / 5)
    if (demeritPoints < 12):
        print("Your demerit points", demeritPoints)
    else:
        print("Your demerit points", demeritPoints, "\nLicense Suspended.")
