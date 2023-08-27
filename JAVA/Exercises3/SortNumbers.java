import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/* 3- Write a program and ask the user to enter 5 numbers. 
If a number has been previously entered, display an error message and ask the user to re-try. 
Once the user successfully enters 5 unique numbers, sort them and display the result on the console. */

public class SortNumbers {

    Scanner input = new Scanner(System.in);
    
    public void Sort() {

        ArrayList<Integer> numbers = new ArrayList<Integer>();

        for(int i=1; i<=5; i++){
            System.out.print("Please enter a unique number (" + i + " of 5): ");
            int temp = input.nextInt();
            if(numbers.contains(temp)){
                System.out.println("You've previously entered: " + temp);
                i--;
                continue;
            }
            numbers.add(temp);
        }
        Collections.sort(numbers);
        System.out.print("You entered next numbers (the list was sorted):");
        for (Integer num : numbers) {
            System.out.print(" " + num);
        }
    }
}
