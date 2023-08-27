""" 
1- Write a program and ask the user to enter a few numbers separated by a hyphen. 
Work out if the numbers are consecutive. 
For example, if the input is "5-6-7-8-9" or "20-19-18-17-16", display a message: "Consecutive"; 
otherwise, display "Not Consecutive". 
"""
def Consecutive():
    numbers = input("Please enter a few numbers separated by a hyphen: ")
    numbersList = [int(num) for num in numbers.split('-')]
    numbersList.sort()
    isConsecutive = True
    for index in range(1, len(numbersList)):
        if numbersList[index] != numbersList[index-1]+1:
            isConsecutive = False
            break
    print("Consecutive" if isConsecutive else "Not Consecutive")
