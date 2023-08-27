import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/* Note: For all these exercises, ignore input validation unless otherwise specified. 
Assume the user provides input in the format that the program expects.
*/

public class Main {

    static Scanner input = new Scanner(System.in);

    /*
     * 1- Write a program and ask the user to enter a few numbers separated by a
     * hyphen.
     * Work out if the numbers are consecutive.
     * For example, if the input is "5-6-7-8-9" or "20-19-18-17-16", display a
     * message: "Consecutive";
     * otherwise, display "Not Consecutive".
     */
    public static void Consecutive() {
        System.out.print("Please enther a few number separated by a hyphen: ");
        String[] tempList = input.nextLine().split("-");
        ArrayList<Integer> listNumbers = new ArrayList<Integer>();
        while (true) {
            if (tempList.length <= 0) {
                System.out.print("Please try again: ");
                tempList = input.nextLine().split("-");
                continue;
            } else {
                for (String tmp : tempList) {
                    listNumbers.add(Integer.parseInt(tmp));
                }
                break;
            }
        }

        boolean isConsecutive = true;
        if (listNumbers.size() == 1) {
            System.out.println("Consecutive");
        } else {
            Collections.sort(listNumbers);
            for (int i = 1; i < listNumbers.size(); i++) {
                if (listNumbers.get(i) != listNumbers.get(i - 1) + 1) {
                    isConsecutive = false;
                    break;
                }
            }
            if (isConsecutive == true) {
                System.out.println("Consecutive");
            } else {
                System.out.println("Not Consecutive");
            }
        }

    }

    /*
     * 2- Write a program and ask the user to enter a few numbers separated by a
     * hyphen.
     * If the user simply presses Enter, without supplying an input, exit
     * immediately; otherwise, check to see if there are duplicates.
     * If so, display "Duplicate" on the console.
     */
    public static void Duplicates() {
        System.out.print("Please enther a few number separated by a hyphen: ");
        String[] tempList = input.nextLine().split("-");
        ArrayList<Integer> listNumbers = new ArrayList<Integer>();

        if (tempList.length <= 0) {
            System.exit(0);
        } else {
            for (String nums : tempList) {
                listNumbers.add(Integer.parseInt(nums));
            }
        }

        boolean isDuplicate = false;
        ArrayList<Integer> uniqueNumbers = new ArrayList<Integer>();

        for (Integer nums : listNumbers) {
            if (!uniqueNumbers.contains(nums)) {
                uniqueNumbers.add(nums);
            } else {
                isDuplicate = true;
                break;
            }
        }

        if (isDuplicate == true) {
            System.out.println("Duplicate");
        } else {
            System.out.println("Not duplicate");
        }
    }

    /*
     * 3- Write a program and ask the user to enter a time value in the 24-hour time
     * format (e.g. 19:00).
     * A valid time should be between 00:00 and 23:59.
     * If the time is valid, display "Ok"; otherwise, display "Invalid Time".
     * If the user doesn't provide any values, consider it as invalid time.
     */
    public static void ValidTime() {
        System.out.print("Please enter a time value in the 24-hour time format (e.g. 19:00): ");
        String[] tempTime = input.nextLine().split(":");

        if (tempTime.length != 2) {
            System.out.println("Invalid Time");
        } else {
            int hours = Integer.parseInt(tempTime[0]);
            int minutes = Integer.parseInt(tempTime[1]);
            if (hours >= 0 && hours <= 23 && minutes >= 0 && minutes <= 59) {
                System.out.println("Ok");
            } else {
                System.out.println("Invalid Time");
            }
        }

    }

    /*
     * 4- Write a program and ask the user to enter a few words separated by a
     * space.
     * Use the words to create a variable name with PascalCase.
     * For example, if the user types: "number of students", display
     * "NumberOfStudents".
     * Make sure that the program is not dependent on the input.
     * So, if the user types "NUMBER OF STUDENTS", the program should still display
     * "NumberOfStudents".
     */
    public static void PascalCase() {
        System.out.print("Please enter a few words separated by a space: ");
        String[] words = input.nextLine().toLowerCase().split(" ");
        for (String word : words) {
            System.out.print(word.toUpperCase().charAt(0) + word.substring(1));
        }
    }

    // 5- Write a program and ask the user to enter an English word.
    // Count the number of vowels (a, e, o, u, i) in the word.
    // So, if the user enters "inadequate", the program should display 6 on the
    // console.
    public static void NumbersOfVowels(){
        ArrayList<Character> vowels = new ArrayList<Character>(List.of('a', 'e', 'o', 'u', 'i'));
        int count = 0;
        System.out.print("Please enter an English word: ");
        char[] word = input.nextLine().toCharArray();
        for (char letter : word) {
            if(vowels.contains(letter)){
                count++;
            }
        }
        System.out.println("Number of vowels in the word: " + count);
    }

    public static void main(String[] args) {
        // Consecutive();
        // Duplicates();
        // ValidTime();
        PascalCase();
        // NumbersOfVowels();
    }

}