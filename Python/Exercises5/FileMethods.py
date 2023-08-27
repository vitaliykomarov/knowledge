""" 
1- Write a program that reads a text file and displays the number of words.
"""
def NumberOfWords():
    file = open("file.txt", "r")
    # counter = 0
    words = file.read().split()
    # for line in file:
    #     for word in line.split():
    #         counter +=1
    print("Number of words in the file:",  len(words))
    
    file.close()


""" 
2- Write a program that reads a text file and displays the longest word in the file.  
"""
def LongestWord():
    file = open("file.txt", "r")
    words = file.read().split()
    wordLen = 0
    for word in words:
        if len(word) > wordLen:
            wordLen = len(word)
            longestWord = word
    print("The longest word in the file:", longestWord)

    # max_len = len(max(words, key=len))
    # longWord = [word for word in words if len(word) == max_len]
    # print(longWord)

    file.close()