#include "Methods.h"

using namespace std;

// 1- Write a program that reads a text file and displays the number of words.

void Methods::NumberOfWords()
{
    string inputText;
    ifstream readFile("file.txt");
    int counter;

    vector<string> text;
    while (getline(readFile, inputText))
    {
        text.push_back(inputText);      
        stringstream ss(inputText);
        while (getline(ss, inputText, ' '))
        {
            counter++;
        }
        
    }
    for (string word : text)
    {
        cout << word << endl;
    }

    // while(readFile >> inputText){
    //     counter++;
    // }
    cout << "Number of words in the file: " << counter;
}