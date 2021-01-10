Note: For any of these exercises, ignore input validation unless otherwise directed.
Assume the user enters values in the format that the program expects.

1- When you post a message on Facebook, depending on the number of people who like your post, Facebook displays different information.
If no one likes your post, it doesn't display anything.
If only one person likes your post, it displays: [Friend's Name] likes your post.
If two people like your post, it displays: [Friend 1] and [Friend 2] like your post.
If more than two people like your post, it displays: [Friend 1], [Friend 2] and[Number of Other People] others like your post.
Write a program and continuously ask the user to enter different names, until the user presses Enter (without supplying a name). 
Depending on the number of names provided, display a message based on the above pattern.  
Code Listing 1:
```cs
        public static void Exercises1()
        {
            List<string> names = new List<string>();

            while (true)
            {
                Console.Write("Enter a name (or press Enter to exit): ");
                string name = Console.ReadLine();

                if (String.IsNullOrWhiteSpace(name))
                {
                    break;
                }
                names.Add(name);
            }

            if(names.Count == 2)
            {
                Console.WriteLine("{0} and {1} like your post.", names[0], names[1]);
            }
            else if (names.Count > 2)
            {
                Console.WriteLine("{0}, {1} and {2} others like your post.", names[0], names[1], names.Count-2);
            }
            else
            {
                Console.WriteLine("{0} likes your post.", names[0]);
            }
        }
```

2- Write a program and ask the user to enter their name.
Use an array to reverse the name and then store the result in a new string. 
Display the reversed name on the console.  
Code Listing 2:
```cs
        public static void Exercises2()
        {
            Console.Write("Enter your name: ");
            string name = Console.ReadLine();
            char[] charName = new char[name.Length];

            for (int i = 0; i < name.Length; i++)
            {
                charName[i] = name[i];
            }
            Array.Reverse(charName);
            string reverseName = new string(charName);
            Console.WriteLine("You reverse name: " + reverseName);
        }
```

3- Write a program and ask the user to enter 5 numbers.
If a number has been previously entered, display an error message and ask the user to re-try. 
Once the user successfully enters 5 unique numbers, sort them and display the result on the console.  
Code Listing 3:
```cs
        public static void Exercises3()
        {
            List<int> uniqueNumbers = new List<int>();

            for(int i = 0; i<5; i++)
            {
                Console.Write("Enter five unique numbers: ");
                int input = Convert.ToInt32(Console.ReadLine());

                if (uniqueNumbers.Contains(input))
                {
                    Console.WriteLine("You entered previously number: " + input);
                    i--;
                    continue;
                }
                else
                {
                    uniqueNumbers.Add(input);
                }
            }
            uniqueNumbers.Sort();
            Console.WriteLine("You entered next sorted unique numbers: ");
            foreach(var num in uniqueNumbers)
            {
                Console.WriteLine(num);
            }
        }
```

4- Write a program and ask the user to continuously enter a number or type "Quit" to exit. 
The list of numbers may include duplicates.
Display the unique numbers that the user has entered.  
Code Listing 4:
```cs
        public static void Exercises4()
        {
            List<int> numbers = new List<int>();

            while (true)
            {
                Console.Write("Enter a number (or 'Quit' to exit): ");
                string input = Console.ReadLine();
                if (input.ToLower() == "quit")
                {
                    break;
                }
                else
                {
                    numbers.Add(Convert.ToInt32(input));
                }
            }

            List<int> uniqueNumbers = new List<int>();
            foreach(var number in numbers)
            {
                if (!uniqueNumbers.Contains(number))
                {
                    uniqueNumbers.Add(number);
                }
            }

            Console.WriteLine("You entered next unique numbers: ");
            foreach(var number in uniqueNumbers)
            {
                Console.WriteLine(number);
            }
        }
```

5- Write a program and ask the user to supply a list of comma separated numbers (e.g 5, 1, 9, 2, 10). 
If the list is empty or includes less than 5 numbers, display "Invalid List" and ask the user to re-try; otherwise, display the 3 smallest numbers in the list.  
Code Listing 5:
```cs
        public static void Exercises5()
        {
            List<int> listNumbers = new List<int>();
            string[] numbers;
            while (true)
            {
                Console.Write("Enter a numbers separated by comma (e.g 5, 1, 9, 2, 10): ");
                var input = Console.ReadLine();

                if (!String.IsNullOrWhiteSpace(input))
                {
                    numbers = input.Split(",");
                    if (numbers.Length < 5)
                    {
                        Console.WriteLine("Invalid List");
                    }
                    else
                    {
                        break;
                    }
                }
            }

            foreach (var number in numbers)
            {
                listNumbers.Add(Convert.ToInt32(number));
            }

            List<int> smallestNumbers = new List<int>();
            
            while (smallestNumbers.Count < 3)
            {
                int min = listNumbers[0];
                foreach (var number in listNumbers)
                {
                    if (number < min)
                    {
                        min = number;
                    }
                }
                smallestNumbers.Add(min);
                listNumbers.Remove(min);
            }

            Console.WriteLine("The 3 smallest number are: ");
            foreach (var number in smallestNumbers)
            {
                Console.WriteLine(number);
            }
        }
```
