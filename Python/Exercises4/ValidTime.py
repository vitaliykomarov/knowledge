""" 
3- Write a program and ask the user to enter a time value in the 24-hour time format (e.g. 19:00).
A valid time should be between 00:00 and 23:59. 
If the time is valid, display "Ok"; otherwise, display "Invalid Time". 
If the user doesn't provide any values, consider it as invalid time. 
"""


def ValidTime():
    numbers = input(
        "Please enter a time value in the 24-hour time format (e.g. 19:00): ")
    numbersList = [int(num) for num in numbers.split(':')]
    if len(numbersList) != 2:
        print("Invalid Time")
    else: 
        hours = numbersList[0]
        minutes = numbersList[1]
        if hours >= 00 and hours <= 23 and minutes >= 00 and minutes <= 59:
            print("Ok")
        else:
            print("Invalid Time")
