import java.util.Scanner;

/* Note: for all these exercises, ignore input validation unless otherwise directed. 
Assume the user enters a value in the format that the program expects. 
For example, if the program expects the user to enter a number, don't worry about validating if the input is a number or not. 
When testing your program, simply enter a number. */

/* Write a program and ask the user to enter the width and height of an image. 
Then tell if the image is landscape or portrait. */

public class PhotoOrientation {
    public static void main(String[] args) {
        Scanner inputNumber = new Scanner(System.in);
        
        System.out.print("Please enter the height: ");
        int height = inputNumber.nextInt();
        
        while(height<=0)
        {
            System.out.print("It must be a positive number, please try again: ");
            height = inputNumber.nextInt();
        }

        System.out.print("Please enter the width: ");
        int width = inputNumber.nextInt();

        while(width<=0)
        {
            System.out.print("It must be a positive number, please try again: ");
            width = inputNumber.nextInt();
        }

        inputNumber.close();
        
        if (height>width) {
            System.out.println("It's a portrait photo.");
        } else if (width>height) {
            System.out.println("It's a landscape photo.");
        } else {
            System.out.println("It's a square photo.");
        }
    }
}
