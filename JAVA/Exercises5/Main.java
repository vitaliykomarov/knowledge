import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;




public class Main {

    // 1- Write a program that reads a text file and displays the number of words.
    public static void NumbeOfWords() throws IOException {
        //System.out.println(System.getProperty("user.dir"));
        Scanner scanFile = new Scanner(new File("D:\\Development\\JAVA\\Exercises5\\file.txt"));
        ArrayList<String> words = new ArrayList<String>();
        while(scanFile.hasNext()){
            words.add(scanFile.next());
        }
        scanFile.close();
        //System.out.print(words);
        // for (String word : text) {

        //     System.out.println(word + " ");
        // }
        System.out.println("\nNumber of words in the file: " + words.size());

    }

    // 2- Write a program that reads a text file and displays the longest word in the file.
    public static void LongestWord() throws IOException  {
        Scanner scanFile = new Scanner(new File("D:\\Development\\JAVA\\Exercises5\\file.txt"));
        ArrayList<String> words = new ArrayList<String>();
        while(scanFile.hasNext()){
            words.add(scanFile.next());
        }
        scanFile.close();
        int letters=words.get(0).length();
        String longestWord = words.get(0);
        for (String word : words) {
            
            if(word.length()>letters){
                letters=word.length();
                longestWord=word;
            }
        }
        System.out.println("The longest word: " + longestWord + " = " + letters );
    }

    public static void main(String[] args) throws IOException {
        NumbeOfWords();
        LongestWord();
    }
}