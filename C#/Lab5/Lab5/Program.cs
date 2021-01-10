using System;
using System.Collections.Generic;
using System.IO;

namespace Lab5
{
    class Program
    {
        /*
         * 1- Write a program that reads a text file and displays the number of words.
         */
        public static void Exercises1()
        {
            var path = @"D:\Development\C#\Labs\Lab5\Lab5\Exercises5.txt";
            var input = "";
            if (File.Exists(path))
            {
               input = File.ReadAllText(path);
            }

            List<string> content = new List<string>();
            int count = 0;
            char[] splitChar = { ' ', '\n', '\r', '(', ')' };
            foreach (var word in input.Split(splitChar, StringSplitOptions.RemoveEmptyEntries))
            {
                content.Add(word);
                count++;
            }
            Console.WriteLine("Number of words in the file: " + count);
        }

        /*
         * 2- Write a program that reads a text file and displays the longest word in the file.
         */
        public static void Exercises2()
        {
            var path = @"D:\Development\C#\Labs\Lab5\Lab5\Exercises5.txt";
            var input = "";
            if (File.Exists(path))
            {
                input = File.ReadAllText(path);
            }

            List<string> content = new List<string>();
            char[] splitChar = { ' ', '\n', '\r', '(', ')' };
            foreach (var word in input.Split(splitChar, StringSplitOptions.RemoveEmptyEntries))
            {
                content.Add(word);
            }

            int indexLongWord = 0;
            int longWord = 0;

            for(int i = 0; i < content.Count; i++)
            {
                var word = content[i];
                int lengthWord = word.Length;
                if (lengthWord > longWord)
                {
                    longWord = lengthWord;
                    indexLongWord = i;
                }
            }

            Console.WriteLine("The longest word in the file - " + content[indexLongWord] + " - contains = " + longWord);
        }

        static void Main(string[] args)
        {
            //Exercises1();
            Exercises2();
        }
    }
}
