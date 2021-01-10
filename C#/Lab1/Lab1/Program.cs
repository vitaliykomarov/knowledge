using System;

namespace Lab1
{
    class Program
    {
        //Note: for all these exercises, ignore input validation unless otherwise directed.
        //Assume the user enters a value in the format that the program expects. 
        //For example, if the program expects the user to enter a number, don't worry about validating if the input is a number or not. 
        //When testing your program, simply enter a number.


        /*
         * 1- Write a program and ask the user to enter a number. 
         * The number should be between 1 to 10. 
         * If the user enters a valid number, display "Valid" on the console. 
         * Otherwise, display "Invalid". (This logic is used a lot in applications where values entered into input boxes need to be validated.)
        */
        public static void Exercises1()
        {
            Console.Write("Enter a number (1 to 10): ");
            int input = Convert.ToInt32(Console.ReadLine());

            if(input>=1 && input <= 10)
            {
                Console.WriteLine("Valid");
            }
            else
            {
                Console.WriteLine("Invalid");
            }            
        }

        /*
         * 2- Write a program which takes two numbers from the console and displays the maximum of the two.
        */
        public static void Exercises2()
        {
            Console.Write("Enter the first number: ");
            int first = Convert.ToInt32(Console.ReadLine());
            Console.Write("Enter the second number: ");
            int second = Convert.ToInt32(Console.ReadLine());

            if (first > second)
            {
                Console.WriteLine("The first number {0} is greater than second {1}", first, second);
            }
            else if (second > first)
            {
                Console.WriteLine("The second number {0} is greater than first {1}", second, first);
            }
            else
            {
                Console.WriteLine("Both numbers are equal {0} = {1}", first, second);
            }
        }

        /*
         * 3- Write a program and ask the user to enter the width and height of an image. 
         * Then tell if the image is landscape or portrait.
        */
        public static void Exercises3()
        {
            Console.Write("Image width: ");
            int width = Convert.ToInt32(Console.ReadLine());

            Console.Write("Image height: ");
            int height = Convert.ToInt32(Console.ReadLine());

            if (width > height)
            {
                Console.WriteLine("The image is landscape");
            }
            else
            {
                Console.WriteLine("The image is portrait");
            }
        }

        /*
         * 4- Your job is to write a program for a speed camera. 
         * For simplicity, ignore the details such as camera, sensors, etc and focus purely on the logic. 
         * Write a program that asks the user to enter the speed limit. 
         * Once set, the program asks for the speed of a car. 
         * If the user enters a value less than the speed limit, program should display Ok on the console. 
         * If the value is above the speed limit, the program should calculate the number of demerit points. 
         * For every 5km/hr above the speed limit, 1 demerit points should be incurred and displayed on the console. 
         * If the number of demerit points is above 12, the program should display License Suspended.
        */
        public static void Exercises4()
        {
            Console.Write("Enter speed limit: ");
            int speedLimit = Convert.ToInt32(Console.ReadLine());

            Console.Write("Enter a car speed: ");
            int carSpeed = Convert.ToInt32(Console.ReadLine());

            int demeritPoints;
            int exceededSpeed;

            if (carSpeed <= speedLimit)
            {
                Console.WriteLine("OK");
            }
            else
            {
                exceededSpeed = carSpeed - speedLimit;
                demeritPoints = exceededSpeed / 5;
                if (demeritPoints < 12)
                {
                    Console.WriteLine("You have {0} demerit points, if you scored 12 points, you license will be suspended", demeritPoints);
                }
                else
                {
                    Console.WriteLine("You have {0} demerit points, your license suspended", demeritPoints);
                }
            }
        }


        static void Main(string[] args)
        {
            //Exercises1();
            //Exercises2();
            //Exercises3();
            Exercises4();
        }
    }
}