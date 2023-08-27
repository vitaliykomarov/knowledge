import java.util.Scanner;

/* Note: for all these exercises, ignore input validation unless otherwise directed. 
Assume the user enters a value in the format that the program expects. 
For example, if the program expects the user to enter a number, don't worry about validating if the input is a number or not. 
When testing your program, simply enter a number. */

// Your job is to write a program for a speed camera. 
// Write a program that asks the user to enter the speed limit. 
// Once set, the program asks for the speed of a car. 
// If the user enters a value less than the speed limit, program should display Ok on the console. 
// If the value is above the speed limit, the program should calculate the number of demerit points. 
// For every 5km/hr above the speed limit, 1 demerit points should be incurred and displayed on the console.
// If the number of demerit points is above 12, the program should display License Suspended.

public class SpeedCamera {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int demeritPoints;
        
        System.out.print("Please enter the speed limit: ");
        int speedLimit = input.nextInt();
        while(speedLimit<=0){
            System.out.print("The speed limit can't be 0 or negative, please try again: ");
            speedLimit = input.nextInt();
        }

        System.out.print("Please enter the car speed: ");
        int carSpeed = input.nextInt();
        while(carSpeed<0){
            System.out.print("The car speed can't be negative, please try again: ");
            carSpeed = input.nextInt();
        }

        if (carSpeed<=speedLimit) {
            System.out.println("Ok");
        } else if (carSpeed>speedLimit) {
            demeritPoints = (carSpeed-speedLimit) / 5;
            if(demeritPoints<12){
                System.out.println("You have " + demeritPoints + " demerit points. Please don't break the speed limit or your license will be suspended.");
            } else {
                System.out.println("You have " + demeritPoints + " demerit points. Your license suspended.");
            }
            
        }

        input.close();
    }
}
