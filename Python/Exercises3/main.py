"""
Note: For any of these exercises, ignore input validation unless otherwise directed.
Assume the user enters values in the format that the program expects.
"""

# 1- When you post a message on Facebook, depending on the number of people who like your post, Facebook displays different information.
# If no one likes your post, it doesn't display anything.
# If only one person likes your post, it displays: [Friend's Name] likes your post.
# If two people like your post, it displays: [Friend 1] and [Friend 2] like your post.
# If more than two people like your post, it displays: [Friend 1], [Friend 2] and [Number of Other People] others like your post.
# Write a program and continuously ask the user to enter different names, until the user presses Enter (without supplying a name).
# Depending on the number of names provided, display a message based on the above pattern.
import numbers


def Likes():
    names = []
    while True:
        name = input("Please enter a name or press \"Enter\" to exit: ")
        if name == "":
            break
        else:
            names.append(name)
    if len(names) != 0:
        if len(names) == 1:
            print(f"{names[0]} likes your post.")
        elif len(names) == 2:
            print(names[0], "and", names[1], "like your post.")
        else:
            print(
                f"{names[0]}, {names[1]} and {len(names)-2} others like your post.")

# Likes()

# 2- Write a program and ask the user to enter their name.
# Use an array to reverse the name and then store the result in a new string.
# Display the reversed name on the console.
def ReverseName():

    name = input("Please enter a name: ")
    index = len(name)
    reversedName = name[index::-1]
    print(reversedName)
    
    # tempString = list(name)
    # reversedString = tempString[::-1]
    # print(tempString)
    # print(reversedString)

    # reversedName = []
    # while index > 0:
    #     reversedName += name[index-1]
    #     index -= 1

# ReverseName()

""" 
3- Write a program and ask the user to enter 5 numbers.
If a number has been previously entered, display an error message and ask the user to re-try.
Once the user successfully enters 5 unique numbers, sort them and display the result on the console. 
"""
def SortNumbers():
    numbers = []
    while len(numbers) != 5:
        number = int(input(f"Please enter 5 unique numbers ({len(numbers)+1} of 5): "))
        if not number in numbers:
            numbers.append(number)
        else:
            print("Please try again")
    numbers.sort()
    print("You've entered next 5 unique numbers:",numbers)

# SortNumbers()

""" 
4- Write a program and ask the user to continuously enter a number or type "Quit" to exit.
The list of numbers may include duplicates. Display the unique numbers that the user has entered. 
"""
def UniqueNumbers():
    numbers = []
    while True:
        number = input("Please enter a number or type \"Quit\" to exit: ")
        if number.lower() == "quit":
            break
        else:
            numbers.append(int(number))
    uniqueNumbers = []
    [uniqueNumbers.append(num) for num in numbers if num not in uniqueNumbers]
    # uniqueNumbers = set(numbers)
    # list(set(numbers))
    print("You've entered next unique numbers:", uniqueNumbers)

# UniqueNumbers()

""" 
5- Write a program and ask the user to supply a list of comma separated numbers (e.g 5, 1, 9, 2, 10).
If the list is empty or includes less than 5 numbers, display "Invalid List" and ask the user to re-try;
otherwise, display the 3 smallest numbers in the list. 
"""
def SmallestNumbers():
    smallestNumbers = []
    numbers = input("Please enter a list of comma separated numbers (e.g 5, 1, 9, 2, 10): ")
    numbersList = [int(num.strip()) for num in numbers.split(',')]
    if len(numbersList) < 5:
        print("Invalid list")
        numbers = input("Please try again: ")
        numbersList.clear()
    else:
        while len(smallestNumbers) < 3:
            smallestNumbers.append(min (numbersList))
            index = numbersList.index(min(numbersList))
            del numbersList[index]
            # min = numbersList[0]
            # index = 0
            # for num in numbersList:
            #     if num<min:
            #         min=num
            #         index=numbersList.index(num)
            # smallestNumbers.append(min)
            # del numbersList[index]
    print("You've entered 3 smallest numbers in the list:", smallestNumbers)


SmallestNumbers()