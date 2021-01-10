# Lab5
1- Write a program that reads a text file and displays the number of words.  
#### You must change the directory "D:\Development\C#\Exercises5\Exercises5\Exercises5.txt" to yours 
Code Listing 1:
```cs
        public static void NumberOfWords()
        {
            var path = @"D:\Development\C#\Exercises5\Exercises5\Exercises5.txt";
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
                //Console.WriteLine(content[count-1] + " " + count);
                count++;

            }
            Console.WriteLine("Number of words in the file: " + count);
        }
```

2- Write a program that reads a text file and displays the longest word in the file.   
#### You must change the directory "D:\Development\C#\Exercises5\Exercises5\Exercises5.txt" to yours  
Code Listing 2:
```cs
        public static void LongestWord()
        {
            var path = @"D:\Development\C#\Exercises5\Exercises5\Exercises5.txt";
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

            int longWord = 0;
            int indexLongWord = 0;

            for(int i = 0; i < content.Count; i++)
            {
                var word = content[i];
                int lenghtWord = word.Length;
                if (lenghtWord > longWord)
                {
                    longWord = lenghtWord;
                    indexLongWord = i;
                }
            }
            Console.WriteLine("The longest word in the file - " + content[indexLongWord] + " - contains = " + longWord);
        }
```
