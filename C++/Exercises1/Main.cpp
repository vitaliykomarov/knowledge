#include <iostream>
using namespace std;

/* Note: for all these exercises, ignore input validation unless otherwise directed.
Assume the user enters a value in the format that the program expects.
For example, if the program expects the user to enter a number, don't worry about validating if the input is a number or not.
When testing your program, simply enter a number. */

class Exercises1
{
public:
    void ValidNumbers();
    void MaximumNumber();
    void PhotoOrientation();
    void SpeedCamera();
};

/* 1- Write a program and ask the user to enter a number.
The number should be between 1 to 10. If the user enters a valid number, display "Valid" on the console.
Otherwise, display "Invalid".
(This logic is used a lot in applications where values entered into input boxes need to be validated.) */
void Exercises1::ValidNumbers()
{
    int number;
    cout << "Please enter a number: ";
    cin >> number;
    if (number >= 1 && number <= 10)
    {
        cout << "Valid";
    }
    else
    {
        cout << "Invalid";
    }
}

// 2- Write a program which takes two numbers from the console and displays the maximum of the two.
void Exercises1::MaximumNumber()
{
    int firstNumber, secondNumber;
    cout << "Please enter the first number: ";
    cin >> firstNumber;
    cout << "Please enter the second number: ";
    cin >> secondNumber;
    if (firstNumber > secondNumber)
    {
        cout << "The first number " << firstNumber << " is bigger.";
    }
    else if (secondNumber > firstNumber)
    {
        cout << "The second number " << secondNumber << " is bigger.";
    }
    else
    {
        cout << "Both numbers are equal.";
    }
}

/* 3- Write a program and ask the user to enter the width and height of an image.
Then tell if the image is landscape or portrait. */
void Exercises1::PhotoOrientation()
{
    int width, height;
    cout << "Please enter the width: ";
    cin >> width;
    cout << "Please enter the height: ";
    cin >> height;
    if (width > height)
    {
        cout << "This is a landscape photo.";
    }
    else if (height > width)
    {
        cout << "This is a portrait photo.";
    }
    else
    {
        cout << "This is a square photo.";
    }
}

/* 4- Your job is to write a program for a speed camera.
For simplicity, ignore the details such as camera, sensors, etc and focus purely on the logic.
Write a program that asks the user to enter the speed limit.
Once set, the program asks for the speed of a car.
If the user enters a value less than the speed limit, program should display Ok on the console.
If the value is above the speed limit, the program should calculate the number of demerit points.
For every 5km/hr above the speed limit, 1 demerit points should be incurred and displayed on the console.
If the number of demerit points is above 12, the program should display License Suspended. */
void Exercises1::SpeedCamera()
{
    int speedLimit, carSpeed, demeritPoints;
    cout << "Please enter the speed limit: ";
    cin >> speedLimit;
    cout << "Please enter the speed of car: ";
    cin >> carSpeed;
    if (carSpeed<=speedLimit)
    {
        cout << "Ok";
    }
    else 
    {
        demeritPoints = (carSpeed-speedLimit) / 5;
        if (demeritPoints<12)
        {
            cout << "You have " << demeritPoints << " demerit points.";
        }
        else
        {
            cout << "You have " << demeritPoints << " demerit points. \nLicense Suspended";
        }
        
    }
    
}

int main(int argc, char const *argv[])
{
    Exercises1 exercises1;
    // exercises1.ValidNumbers();
    // exercises1.MaximumNumber();
    // exercises1.PhotoOrientation();
    exercises1.SpeedCamera();
    return 0;
}
