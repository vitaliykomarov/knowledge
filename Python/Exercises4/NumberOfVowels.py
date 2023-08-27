""" 
5- Write a program and ask the user to enter an English word. 
Count the number of vowels (a, e, o, u, i) in the word. 
So, if the user enters "inadequate", the program should display 6 on the console. 
"""
def NumberOfVowels():
    vowels = ["a", "e", "o", "u", "i"]
    counter = 0
    word = input("Please enter a word: ")
    for letter in word.lower():
        if letter in vowels:
            counter +=1
    print("Number of vowels in the word:", counter)
    