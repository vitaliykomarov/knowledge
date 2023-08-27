import java.util.Scanner;

/* 2- Write a program and ask the user to enter their name. 
Use an array to reverse the name and then store the result in a new string. 
Display the reversed name on the console. */

public class Reverse {

    Scanner input = new Scanner(System.in);

    public void ReverseName() {
        System.out.print("Please enter your name: ");
        char[] letters = input.nextLine().toCharArray();
        boolean letterOrNo = true;

        /*
         * char[] reverse = new char[letters.length];
         * for (int i = 1; i <= letters.length; i++) {
         * reverse[reverse.length-i] = letters[i-1];
         * }
         * System.out.print("The reverse name will be: ");
         * for (char c : reverse) {
         * System.out.print(c);
         * }
         */
        /*
         * for (int i = 0; i <= letters.length / 2; i++) {
         * char temp = letters[i];
         * letters[i] = letters[letters.length - i - 1];
         * letters[letters.length - i - 1] = temp;
         * }
         * System.out.print("The reverse name will be: ");
         * for (char b : letters) {
         * System.out.print(b);
         * }
         * break outerloop;
         */

        //simple check for letters
        outerloop: while (true) {
            for (char c : letters) {
                if (!Character.isLetter(c)) {
                    letterOrNo = false;
                    if (letterOrNo == false) {
                        break;
                    }
                } else {
                    char[] reverse = new char[letters.length];
                    for (int i = 1; i <= letters.length; i++) {
                        reverse[reverse.length - i] = letters[i - 1];
                    }
                    System.out.print("The reverse name will be: ");
                    for (char b : reverse) {
                        System.out.print(b);
                    }
                    break outerloop;

                }
            }
            System.out.print("Please try again: ");
            letters = input.nextLine().toCharArray();
        }
    }
}
