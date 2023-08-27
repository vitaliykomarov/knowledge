import java.util.ArrayList;
import java.util.Scanner;

/* 
5- Write a program and ask the user to supply a list of comma separated numbers (e.g 5, 1, 9, 2, 10). 
If the list is empty or includes less than 5 numbers, display "Invalid List" and ask the user to re-try; 
otherwise, display the 3 smallest numbers in the list.
*/

public class SmallestNumbers {

    Scanner input = new Scanner(System.in);

    public void Smallest(){
        System.out.print("Please enter a list of comma separated numbers (e.g 5, 1, 9, 2, 10): ");
        String[] numbers;
        ArrayList<Integer> listNumbers = new ArrayList<Integer>();
        ArrayList<Integer> smallestNumbers = new ArrayList<Integer>();
        
        while(true){
            
            String tempList = input.nextLine();
            if(!tempList.isEmpty()){
                numbers = tempList.split(",");
                if(numbers.length<5){
                    System.out.println("Invalid list");
                } else{
                    break;
                }
            }
            System.out.print("Please try again: ");
        }

        for (String num : numbers) {
            listNumbers.add(Integer.parseInt(num));
        }

        while(smallestNumbers.size()<3){
            int min = listNumbers.get(0);
            int index = 0;
            for (Integer num : listNumbers) {
                if(num<min){
                    min=num;
                    index = listNumbers.indexOf(min);
                }
            }
            smallestNumbers.add(min);
            listNumbers.remove(index);
        }

        System.out.print("You've entered 3 smallest numbers in the list: ");
        for (Integer num : smallestNumbers) {
            System.out.print(num + ", ");
        }
    }
}
