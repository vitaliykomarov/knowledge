import java.util.ArrayList;
import java.util.Scanner;

/* 4- Write a program and ask the user to continuously enter a number or type "Quit" to exit. 
The list of numbers may include duplicates. Display the unique numbers that the user has entered. */

public class Unique {
    
    Scanner input = new Scanner(System.in);

    public void UniqueNumbers(){

        ArrayList<Integer> numbers = new ArrayList<Integer>();
        ArrayList<Integer> uniqueNumbers = new ArrayList<Integer>();
        
        while(true){
            System.out.print("Please enter a number or \"Quit\" to exit: ");
            String temp = input.nextLine();
            if(!temp.isEmpty() && !temp.equals("Quit")){
                numbers.add(Integer.parseInt(temp));
            } else {
                break;
            }
        }
        for (Integer num : numbers) {
            if(!uniqueNumbers.contains(num)){
                uniqueNumbers.add(num);
            }
        }
        System.out.println("You entered next unique numbers: ");
        for (Integer num : uniqueNumbers) {
            System.out.print(num);
        }
    }
}
