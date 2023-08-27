#include <iostream>
#include <sstream>
#include <vector>
#include <algorithm>
//#include<bits/stdc++.h>

using namespace std;

/* Note: for all these exercises, ignore input validation unless otherwise directed.
Assume the user enters a value in the format that the program expects.
For example, if the program expects the user to enter a number, don't worry about validating if the input is a number or not.
When testing your program, simply enter a number.
*/

class Exercises2
{
public:
    void DivisibleNumbers();
    void SumNumbers();
    void Factorial();
    void GuessNumber();
    void MaxNumber();
};

/* 1- Write a program to count how many numbers between 1 and 100 are divisible by 3 with no remainder.
Display the count on the console. */
void Exercises2::DivisibleNumbers()
{
    int count = 0;
    for (int i = 1; i <= 100; i++)
    {
        if (i % 3 == 0)
        {
            count++;
        }
    }
    cout << count << " number divisible by 3 with no remainder.";
}

/* 2- Write a program and continuously ask the user to enter a number or "ok" to exit.
Calculate the sum of all the previously entered numbers and display it on the console. */
void Exercises2::SumNumbers()
{
    string input;
    int sum = 0;
    while (true)
    {
        cout << "Please enter a number or \"ok\" to exit: ";
        // cin >> input;

        // A better way to do user input is to use std::getline to read characters from the keyboard until the user hits enter.
        getline(cin, input);

        // transform(input.begin(), input.end(), input.begin(), ::tolower);

        if (input == "ok" || input.empty())
        {
            break;
        }
        else
        {
            sum += stoi(input);
        }
    }
    if (sum != 0)
    {
        cout << "Sum of entered numbers: " << sum;
    }
}

/* 3- Write a program and ask the user to enter a number.
Compute the factorial of the number and print it on the console.
For example, if the user enters 5, the program should calculate 5 x 4 x 3 x 2 x 1 and display it as 5! = 120. */
void Exercises2::Factorial()
{
    long long factorial;
    int index;
    cout << "Please enter a number to find its factorial: ";
    cin >> index;
    factorial = (long long)index;
    if (index == 0 || index == 1)
    {
        cout << "Factorial " << index << " will be: 1.";
    }
    else
    {
        for (int i = index - 1; i >= 1; i--)
        {
            factorial = factorial * i;
        }
        cout << "Factorial " << index << " will be: " << factorial;
    }
}

/* 4- Write a program that picks a random number between 1 and 10.
Give the user 4 chances to guess the number.
If the user guesses the number, display “You won"; otherwise, display “You lost".
(To make sure the program is behaving correctly, you can display the secret number on the console first.) */
void Exercises2::GuessNumber()
{
    srand((unsigned)time(NULL));
    int random = rand() % 10 + 1;
    int guessNumber;
    cout << random;
    cout << "\nTry to guess the number: ";
    cin >> guessNumber;

    // i = 3 because we already used the first attempt
    for (int i = 3; i >= 0; i--)
    {
        if (guessNumber == random)
        {
            cout << "You won";
            break;
        }
        else if (i == 0)
        {
            cout << "You lost";
        }
        else
        {
            cout << "Try again you still have " << i << " attempts: ";
            cin >> guessNumber;
        }
    }
}

/* 5- Write a program and ask the user to enter a series of numbers separated by comma.
Find the maximum of the numbers and display it on the console.
For example, if the user enters “5, 3, 8, 1, 4", the program should display 8. */
void Exercises2::MaxNumber()
{
    string numbers;
    vector<int> numbersList;
    cout << "Please enter a series of numbers separated by comma: ";
    getline(cin, numbers);
    cout << numbers;
    stringstream ss(numbers);
    int tmp;
    while (ss >> tmp)
    {
        if (ss.peek() == ',')
            ss.ignore();
        numbersList.push_back(tmp);
    }
    int maxNum = numbersList[0];
    for (int num : numbersList)
    {
        if(num>maxNum){
            maxNum = num;
        }
    }   
    cout << "\nMax number will be: " << maxNum;
}

int main(int argc, char const *argv[])
{
    Exercises2 exercises2;
    // exercises2.DivisibleNumbers();
    // exercises2.SumNumbers();
    // exercises2.Factorial();
    // exercises2.GuessNumber();
    exercises2.MaxNumber();
    return 0;
}
