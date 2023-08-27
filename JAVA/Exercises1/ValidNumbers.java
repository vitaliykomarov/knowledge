import java.util.Scanner;

/* Note: for all these exercises, ignore input validation unless otherwise directed. 
Assume the user enters a value in the format that the program expects. 
For example, if the program expects the user to enter a number, don't worry about validating if the input is a number or not. 
When testing your program, simply enter a number. */

/*
Write a program and ask the user to enter a number. 
The number should be between 1 to 10. 
If the user enters a valid number, display "Valid" on the console. 
Otherwise, display "Invalid". 
(This logic is used a lot in applications where values entered into input boxes need to be validated.)
*/

public class ValidNumbers {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        System.out.print("Please enter a number between 1 to 10: ");
        int x = input.nextInt();
        input.close();

        if (x>=1 && x<=10) {
            System.out.println("Valid");
        } else {
            System.out.println("Invalid");
        }
    }
}