import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/* Note: for all these exercises, ignore input validation unless otherwise directed. 
Assume the user enters a value in the format that the program expects. 
For example, if the program expects the user to enter a number, don't worry about validating if the input is a number or not. 
When testing your program, simply enter a number. */

public class Exercises {

    public static Scanner input = new Scanner(System.in);

    /*
     * 1- Write a program to count how many numbers between 1 and 100 are divisible
     * by 3 with no remainder.
     * Display the count on the console.
     */
    static void CountDivisibleNumbers() {
        int count = 0;
        for (int i = 1; i <= 100; i++) {
            if (i % 3 == 0) {
                count++;
            }
        }
        System.out.println("Numbers between 1 to 100 divisible by 3 with no remainde: " + count);
    }

    /*
     * 2- Write a program and continuously ask the user to enter a number or "ok" to
     * exit.
     * Calculate the sum of all the previously entered numbers and display it on the
     * console.
     */
    static void SumNumbers() {

        int sumNumbers = 0;
        while (true) {
            System.out.print("Please enter a number or \"ok\" to exit: ");
            String tempInput = input.next();
            if (!(tempInput.equals("ok"))) {
                sumNumbers += Integer.parseInt(tempInput);
            } else {
                System.out.println("Sum numbers: " + sumNumbers);
                break;
            }
        }
    }

    /*
     * 3- Write a program and ask the user to enter a number.
     * Compute the factorial of the number and print it on the console.
     * For example, if the user enters 5, the program should calculate 5 x 4 x 3 x 2
     * x 1 and display it as 5! = 120.
     */
    static void Factorial() {
        System.out.print("Please enter a number to calculate factorial: ");
        long factorial = input.nextLong();
        int index = (int) factorial;
        if (factorial == 0 || factorial == 1) {
            System.out.println("Factorial " + factorial + " will be: 1");
        }
        for (long i = factorial - 1; i >= 1; i--) {
            factorial = factorial * i;
        }
        System.out.println("Factorial " + index + " will be: " + factorial);
    }

    /*
     * 4- Write a program that picks a random number between 1 and 10.
     * Give the user 4 chances to guess the number.
     * If the user guesses the number, display “You
     * won"; otherwise, display “You lost".
     * (To make sure the program is behaving correctly, you can display the secret
     * number on the console first.)
     */
    static void GuessNumber() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(1, 11);
        //System.out.println(randomNumber); // Just check

        System.out.print("Try to guess the number: ");
        int guessedNumber = input.nextInt();

        // chances is 3 because we used the first attempt at the beginning
        for (int chances = 3; chances >= 0; chances--) {
            if (guessedNumber == randomNumber) {
                System.out.println("You won");
                //break;
            } else if (chances == 0) {
                System.out.println("You lost");
                break;
            } else {
                System.out.print("Try again you still have " + chances + " attempts: ");
                guessedNumber = input.nextInt();
            }
        }
    }

    /*
     * 5- Write a program and ask the user to enter a series of numbers separated by comma.
     * Find the maximum of the numbers and display it on the console.
     * For example, if the user enters “5, 3, 8, 1, 4", the program should display
     * 8.
     */
    static void MaxNumber() {
        System.out.print("Please enter a series of numbers separated by comma: ");
        String[] line = input.nextLine().split(",");
        int[] numbers = new int[line.length];
        int maxNumber = 0;
        
        for(int i =0; i<line.length; i++){
            numbers[i] = Integer.parseInt(line[i]);
            maxNumber = numbers[0];
            //System.out.println(numbers[i]);
            //if(numbers[i]>maxNumber){
            //    maxNumber = numbers[i];
            //}
        }
        for (int i : numbers) {
            if(i>maxNumber){
                maxNumber = i;
            }
        }
        
        System.out.println("The maximum number will be: " + maxNumber);

    }

    public static void main(String[] args) {
        //CountDivisibleNumbers();
        //SumNumbers();
        //Factorial();
        GuessNumber();
        //MaxNumber();
    }
}