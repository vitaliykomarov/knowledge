# Lab2
Note: for all these exercises, ignore input validation unless otherwise directed.
Assume the user enters a value in the format that the program expects.
For example, if the program expects the user to enter a number, don't worry about validating if the input is a number or not. 
When testing your program, simply enter a number.        

1- Write a program to count how many numbers between 1 and 100 are divisible by 3 with no remainder.
Display the count on the console.  
Code Listing 1:
```cs
        public static void Exercises1()
        {
            int count = 0;

            for(int i = 1; i<=100; i++)
            {
                if (i % 3 == 0)
                {
                    count++;
                }
            }

            Console.WriteLine("Numbers between 1 and 100: " + count);
        }
```

2- Write a program and continuously ask the user to enter a number or "ok" to exit. 
Calculate the sum of all the previously entered numbers and display it on the console.  
Code Listing 2:
```cs
        public static void Exercises2()
        {
            int sum = 0;
            while (true)
            {
                Console.Write("Enter a number or 'ok' to exit: ");
                var input = Console.ReadLine();
                if (input.ToLower() == "ok")
                {
                    break;
                }
                sum += Convert.ToInt32(input);
            }
            Console.WriteLine("Sum of all entered numbers: " + sum);
        }
```

3- Write a program and ask the user to enter a number.
Compute the factorial of the number and print it on the console.
For example, if the user enters 5, the program should calculate 5 x 4 x 3 x 2 x 1 and display it as 5! = 120.  
Code Listing 3:
```cs
        public static void Exercises3()
        {
            Console.Write("Enter a number: ");
            int input = Convert.ToInt32(Console.ReadLine());

            int factorial = 1;
            for(int i = 1; i<=input; i++)
            {
                factorial *= i;
            }

            Console.WriteLine("{0}! = {1}", input, factorial);
        }
```

4- Write a program that picks a random number between 1 and 10. 
Give the user 4 chances to guess the number.
If the user guesses the number, display “You won"; otherwise, display “You lost". 
(To make sure the program is behaving correctly, you can display the secret number on the console first.)  
Code Listing 4:
```cs
        public static void Exercises4()
        {
            int secret = new Random().Next(1, 11);
            //Console.WriteLine(secret);
            int chances = 4;
            int answer;

            while(chances > 0)
            {
                Console.Write("Try to guesses the number, you have {0} chances: ", chances);
                answer = Convert.ToInt32(Console.ReadLine());
                if(answer == secret)
                {
                    Console.WriteLine("You won");
                    return;
                }
                else
                {
                    chances--;
                }
            }
            Console.WriteLine("You lost");
        }
```

5- Write a program and ask the user to enter a series of numbers separated by comma.
Find the maximum of the numbers and display it on the console.
For example, if the user enters “5, 3, 8, 1, 4", the program should display 8.  
Code Listing 5:
```cs
        public static void Exercises5()
        {
            Console.Write("Enter a numbers separated by comma: ");
            string input = Console.ReadLine();
            string[] numbers = input.Split(",");

            int max = Convert.ToInt32(numbers[0]);

            foreach(string str in numbers)
            {
                int number = Convert.ToInt32(str);
                if (number > max)
                {
                    max = number;
                }
            }
            Console.WriteLine("The max of numbers: " + max);
        }
```
