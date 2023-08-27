""" 
4- Write a program and ask the user to enter a few words separated by a space. 
Use the words to create a variable name with PascalCase. 
For example, if the user types: "number of students", display "NumberOfStudents". 
Make sure that the program is not dependent on the input. 
So, if the user types "NUMBER OF STUDENTS", the program should still display "NumberOfStudents". 
"""

def PascalCase():
    words = input("Please enter a few words separated by a space: ")
    
    wordsList = [word.lower() for word in words.split(' ')]
    pascalCase = ""
    for word in wordsList:
        if len(word) > 1:
            pascalCase += word[0].upper()+word[1:]
        else:
            pascalCase += word[0].upper()
    print(pascalCase)
    # 
    print(''.join(word for word in words.title() if not word.isspace()))