#include "Methods.h"

using namespace std;

/* 2- Write a program that reads a text file and displays the longest word in the file. */

void Methods::LongestWord()
{
    string inputText;
    vector<string> text;
    ifstream readFile("file.txt");
    while (getline(readFile, inputText))
    {
        stringstream ss(inputText);
        while (getline(ss, inputText, ' '))
        {
            text.push_back(inputText);
        }
    }
    string longestWord = text[0];
    for (string word : text)
    {
        // cout << word << endl;
        if (word.length() > longestWord.length())
        {
            longestWord = word;
        }
    }
    //cout << "The longest word in the file: " << longestWord;
}