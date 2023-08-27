import java.util.Scanner;

/* Note: for all these exercises, ignore input validation unless otherwise directed. 
Assume the user enters a value in the format that the program expects. 
For example, if the program expects the user to enter a number, don't worry about validating if the input is a number or not. 
When testing your program, simply enter a number. */

// Write a program which takes two numbers from the console and displays the maximum of the two.

public class MaxNumber {
    public static void main(String[] args) {
        Scanner inputNumber = new Scanner(System.in);        
        System.out.print("Please enter the first number: ");
        int first = inputNumber.nextInt();
        System.out.print("Please enter the second number: ");
        int second = inputNumber.nextInt();
        inputNumber.close(); 
        
        if (first>second) {
            System.out.println("The first number " + first + " is greater than the second " + second + ".");
        } else if (second>first) {
            System.out.println("The second number " + second + " is greater than the first " + first + ".");
        } else {
            System.out.println("The firs number " + first + " is equal to the second number " + second + "." );
        }
    }
}
