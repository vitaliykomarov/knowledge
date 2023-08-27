#include <iostream>
#include <vector>
#include <algorithm>
#include <sstream>

using namespace std;

/* Note: For any of these exercises, ignore input validation unless otherwise directed.
Assume the user enters values in the format that the program expects. */

class Exercises3
{
public:
    /* 1- When you post a message on Facebook, depending on the number of people who like your post, Facebook displays different information.
    If no one likes your post, it doesn't display anything.
    If only one person likes your post, it displays: [Friend's Name] likes your post.
    If two people like your post, it displays: [Friend 1] and [Friend 2] like your post.
    If more than two people like your post, it displays: [Friend 1], [Friend 2] and [Number of Other People] others like your post.
    Write a program and continuously ask the user to enter different names, until the user presses Enter (without supplying a name).
    Depending on the number of names provided, display a message based on the above pattern. */
    void Likes()
    {
        vector<string> namesList;
        string name;
        while (true)
        {
            cout << "Please enter a name: ";
            getline(cin, name);
            if (name.empty())
            {
                break;
            }
            else
            {
                namesList.push_back(name);
            }
        }
        if (namesList.size() == 1)
        {
            cout << namesList[0] << " likes your post.";
        }
        else if (namesList.size() == 2)
        {
            cout << namesList[0] << " and " << namesList[1] << " like your post.";
        }
        else if (namesList.size() > 2)
        {
            cout << namesList[0] << ", " << namesList[1] << " and " << namesList.size() - 2 << " others like your post.";
        }
    }

    /*     2- Write a program and ask the user to enter their name.
    Use an array to reverse the name and then store the result in a new string.
    Display the reversed name on the console. */
    void Reverse()
    {
        cout << "Please enter your name: ";
        string name;
        cin >> name;
        reverse(name.begin(), name.end());
        cout << name;
    }

    /*     3- Write a program and ask the user to enter 5 numbers.
    If a number has been previously entered, display an error message and ask the user to re-try.
    Once the user successfully enters 5 unique numbers, sort them and display the result on the console. */
    void SortNumbers()
    {
        vector<int> numbers;
        int num;
        for (int i = 1; i <= 5; i++)
        {
            cout << "Please enter a unique number (" << i << " of 5): ";
            cin >> num;
            if (find(numbers.begin(), numbers.end(), num) != numbers.end())
            {
                cout << "You've already entered this number: " << num << "\n";
                i--;
                continue;
            }
            numbers.push_back(num);
        }
        sort(numbers.begin(), numbers.end());
        cout << "You've entered next numbers (the list was sorted): ";
        for (int i : numbers)
        {
            cout << i << " ";
        }
    }

    /*     4- Write a program and ask the user to continuously enter a number or type "Quit" to exit.
    The list of numbers may include duplicates. Display the unique numbers that the user has entered. */
    void UniqueNumbers()
    {
        vector<int> numbers, uniqueNumbers;
        string number;
        while (true)
        {
            cout << "Please enter a number or type \"Quit\" to exit: ";
            cin >> number;
            transform(number.begin(), number.end(), number.begin(), ::tolower);
            if (number == "quit")
            {
                break;
            }
            else
            {
                numbers.push_back(stoi(number));
            }
        }
        for (int num : numbers)
        {
            // cout << num << " ";
            if (!(find(uniqueNumbers.begin(), uniqueNumbers.end(), num) != uniqueNumbers.end()))
            {
                uniqueNumbers.push_back(num);
            }
        }
        cout << "\nYou entered next unique numbers: ";
        for (int num : uniqueNumbers)
        {
            cout << num << " ";
        }
    }

    /* 5- Write a program and ask the user to supply a list of comma separated numbers (e.g 5, 1, 9, 2, 10).
    If the list is empty or includes less than 5 numbers, display "Invalid List" and ask the user to re-try;
    otherwise, display the 3 smallest numbers in the list. */
    void SmallestNumbers()
    {
        vector<int> numbers, smallestNumbers;
        string num;
        cout << "Please enter a numbers separated by comma (e.g 5, 1, 9, 2, 10): ";
        while (true)
        {
            getline(cin, num);
            if (!num.empty())
            {
                stringstream ss(num);
                int tmp;
                while (ss >> tmp)
                {
                    if (ss.peek() == ',')
                        ss.ignore();
                    numbers.push_back(tmp);
                }
                // cout << numbers.size();
                //  numbers.push_back(stoi(num));
                if (numbers.size() < 5)
                {
                    numbers.clear();
                    cout << "Invalid list";
                }
                else
                {
                    break;
                }
            }
            cout << "\nPlease try again: ";
        }
        // for (int num : numbers)
        // {
        //     cout << num;
        // }
        sort(numbers.begin(), numbers.end());
        while (smallestNumbers.size() < 3)
        {
            int min = numbers[0];
            for (int i = 0; i <= numbers.size(); i++)
            {
                if (min > numbers[i])
                {
                    min = numbers[i];
                }
            }
            smallestNumbers.push_back(min);
            remove(numbers.begin(), numbers.end(), min);
        }
        cout << "The smallest numbers in the list: ";
        for (int num : smallestNumbers)
        {
            cout << num << " ";
        }
    }
};

int main(int argc, char const *argv[])
{
    Exercises3 exercises3;
    // exercises3.Likes();
    // exercises3.Reverse();
    // exercises3.SortNumbers();
    // exercises3.UniqueNumbers();
    exercises3.SmallestNumbers();
    return 0;
}
