#include "Methods.h"
#include <iostream>
#include <vector>
#include <sstream>
#include <algorithm>

using namespace std;

/* Note: For all these exercises, ignore input validation unless otherwise specified.
Assume the user provides input in the format that the program expects.
*/

/* 1- Write a program and ask the user to enter a few numbers separated by a hyphen.
Work out if the numbers are consecutive.
For example, if the input is "5-6-7-8-9" or "20-19-18-17-16", display a message: "Consecutive"; otherwise, display "Not Consecutive". */
void Consecutive()
{
    vector<int> numbersList;
    string nums;
    cout << "Please enter a few numbers separated by a hyphen (e.g. 5-6-7-8-9): ";

    getline(cin, nums);
    stringstream ss(nums);
    int tmp;
    while (ss >> tmp)
    {
        if (ss.peek() == '-')
            ss.ignore();
        numbersList.push_back(tmp);
    }
    bool isConsecutive = true;
    sort(numbersList.begin(), numbersList.end());
    if (numbersList.size() != 1)
    {
        for (int i = 1; i < numbersList.size(); i++)
        {
            if (numbersList[i] != numbersList[i - 1] + 1)
            {
                isConsecutive = false;
                break;
            }
        }
    }

    if (isConsecutive == true)
    {
        cout << "Consecutive";
    }
    else
    {
        cout << "Not Consecutive";
    }
}

/* 2- Write a program and ask the user to enter a few numbers separated by a hyphen.
If the user simply presses Enter, without supplying an input, exit immediately; otherwise, check to see if there are duplicates.
If so, display "Duplicate" on the console. */
void Duplicate()
{
    vector<int> numbersList;
    string nums;
    cout << "Please enter a few numbers separated by a hyphen (e.g. 1-2-3): ";
    getline(cin, nums);

    if (nums.empty())
    {
        exit(0);
    }

    stringstream ss(nums);
    int tmp;
    while (ss >> tmp)
    {
        if (ss.peek() == '-')
            ss.ignore();
        numbersList.push_back(tmp);
    }
    sort(numbersList.begin(), numbersList.end());
    auto it = unique(numbersList.begin(), numbersList.end());
    bool isUnique = (it == numbersList.end());
    if (isUnique == false)
    {
        cout << "Duplicate";
    }
}

/* 3- Write a program and ask the user to enter a time value in the 24-hour time format (e.g. 19:00).
A valid time should be between 00:00 and 23:59.
If the time is valid, display "Ok"; otherwise, display "Invalid Time".
If the user doesn't provide any values, consider it as invalid time. */
void ValidTime()
{
    vector<int> time;
    string input;
    cout << "Please enter a time value in the 24-hour time format (e.g. 19:00): ";
    getline(cin, input);

    stringstream ss(input);
    int tmp;
    while (ss >> tmp)
    {
        if (ss.peek() == ':')
            ss.ignore();
        time.push_back(tmp);
    }

    if (time.size() != 2)
    {
        cout << "Invalid Time";
    }
    else
    {
        int hours = time[0];
        int minutes = time[1];
        if (hours >= 00 && hours <= 23 && minutes >= 00 && minutes <= 59)
        {
            cout << "Ok";
        }
        else
        {
            cout << "Invalid Time";
        }
    }
}

/* 4- Write a program and ask the user to enter a few words separated by a space.
Use the words to create a variable name with PascalCase.
For example, if the user types: "number of students", display "NumberOfStudents".
Make sure that the program is not dependent on the input.
So, if the user types "NUMBER OF STUDENTS", the program should still display "NumberOfStudents". */
void PascalCase()
{
    string input;
    cout << "Please enter a few words separated by a space: ";
    getline(cin, input);

    transform(input.begin(), input.end(), input.begin(), ::tolower);
    stringstream ss(input);
    string tmp;
    while (ss >> tmp)
    {
        if (ss.peek() == ' ')
            ss.ignore();
        tmp[0] = toupper(tmp[0]);
        cout << tmp;
    }
}

/* 5- Write a program and ask the user to enter an English word.
Count the number of vowels (a, e, o, u, i) in the word.
So, if the user enters "inadequate", the program should display 6 on the console.  */
void NumberOfVowels()
{
    char vowels[] = {'a', 'e', 'o', 'u', 'i'};
    int count = 0;
    string word;
    cout << "Please enter an English word: ";
    getline(cin, word);
    transform(word.begin(), word.end(), word.begin(), ::tolower);
    for(char letter: word){
        // find returns an iterator to the first occurrence of letter, or an iterator to one-past the end of the range if letter is not found
        // the end iterator is just a placeholder for a "one past last" element, used as an end condition for loops or to signal that an element does not exist
        // (in other words: find function return the Iterator to the found element. 
        // If the element is not found in the container, the returned Iterator points to the end of the specified range 
        // (which in this case is std::end). So, find(begin(vowels), end(vowels), letter) != end(vowels) means that element letter is in the array vowels.)
        if(find(begin(vowels), end(vowels), letter) != end(vowels)){
            count++;
        }
    }
    cout << "Number of vowels: " << count;
}