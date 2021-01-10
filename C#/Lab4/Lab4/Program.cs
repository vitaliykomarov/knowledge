using System;
using System.Collections.Generic;

namespace Lab4
{
    class Program
    {
        /*
         * Note: For all these exercises, ignore input validation unless otherwise specified.
         * Assume the user provides input in the format that the program expects.
         */

        /*
         * 1- Write a program and ask the user to enter a few numbers separated by a hyphen. 
         * Work out if the numbers are consecutive. 
         * For example, if the input is "5-6-7-8-9" or "20-19-18-17-16", display a message: "Consecutive"; otherwise, display "Not Consecutive".
         */
        public static void Exercises1()
        {
            Console.Write("Enter a few numbers separated by a hyphen: ");
            string input = Console.ReadLine();

            List<int> numbers = new List<int>();
            foreach(var num in input.Split("-"))
            {
                numbers.Add(Convert.ToInt32(num));
            }

            numbers.Sort();

            bool isConsecutive = true;
            for (int i = 1; i < numbers.Count; i++)
            {
                if (numbers[i] != numbers[i - 1] + 1)
                {
                    isConsecutive = false;
                    break;
                }
            }

            string message = isConsecutive ? "Consecutive" : "Not Consecutive";
            Console.WriteLine(message);
        }

        /*
         * 2- Write a program and ask the user to enter a few numbers separated by a hyphen.
         * If the user simply presses Enter, without supplying an input, exit immediately; otherwise, check to see if there are duplicates.
         * If so, display "Duplicate" on the console.
         */
        public static void Exercises2()
        {
            Console.Write("Enter a few numbers separated by a hyphen: ");
            string input = Console.ReadLine();

            if (String.IsNullOrWhiteSpace(input))
            {
                Console.WriteLine("Invalid input");
            }

            List<int> numbers = new List<int>();
            foreach (var num in input.Split("-"))
            {
                numbers.Add(Convert.ToInt32(num));
            }

            bool isDuplicate = false;
            List<int> uniqNumbers = new List<int>();

            foreach(var num in numbers)
            {
                if (uniqNumbers.Contains(num))
                {
                    isDuplicate = true;
                    break;
                }
                else
                {
                    uniqNumbers.Add(num);
                }
            }

            if(isDuplicate == true)
            {
                Console.WriteLine("Duplicate");
            }
        }

        /*
         * 3- Write a program and ask the user to enter a time value in the 24-hour time format(e.g. 19:00). 
         * A valid time should be between 00:00 and 23:59. 
         * If the time is valid, display "Ok"; otherwise, display "Invalid Time". 
         * If the user doesn't provide any values, consider it as invalid time.
         */
        public static void Exercises3()
        {
            Console.Write("Enter a time in the 24-hour time format (e.g. 19:00): ");
            string input = Console.ReadLine();

            if (String.IsNullOrWhiteSpace(input))
            {
                Console.WriteLine("Invalid Time");
                return;
            }

            string[] time = input.Split(":");

            int hour = Convert.ToInt32(time[0]);
            int minute = Convert.ToInt32(time[1]);

            if (hour >= 0 && hour <= 23 && minute >= 0 && minute <= 59)
            {
                Console.WriteLine("Ok");
            }
            else
            {
                Console.WriteLine("Invalid Time");
            }
        }

        /*
         * 4- Write a program and ask the user to enter a few words separated by a space.
         * Use the words to create a variable name with PascalCase.
         * For example, if the user types: "number of students", display "NumberOfStudents". 
         * Make sure that the program is not dependent on the input.
         * So, if the user types "NUMBER OF STUDENTS", the program should still display "NumberOfStudents".
         */
        public static void Exercises4()
        {
            Console.Write("Enter a few word separated by a space: ");
            string input = Console.ReadLine();

            string text = "";

            foreach(var str in input.Split(" "))
            {
                string varText = char.ToUpper(str[0])+str.ToLower().Substring(1);
                text += varText;
            }
            Console.WriteLine(text);
        }

        /*
         * 5- Write a program and ask the user to enter an English word. 
         * Count the number of vowels (a, e, o, u, i) in the word. 
         * So, if the user enters "inadequate", the program should display 6 on the console.
         */
        public static void Exercises5()
        {
            Console.Write("Enter an English word: ");
            string input = Console.ReadLine();
            List<char> vowels = new List<char>(new char[] { 'a', 'e', 'o', 'u', 'i' });
            int countVowels = 0;

            foreach(var character in input.ToLower())
            {
                if (vowels.Contains(character))
                {
                    countVowels++;
                }
            }

            Console.WriteLine("Vowels in the word: " + countVowels);
        }

        static void Main(string[] args)
        {
            //Exercises1();
            //Exercises2();
            //Exercises3();
            //Exercises4();
            Exercises5();
        }
    }
}
