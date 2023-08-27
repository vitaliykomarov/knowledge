import java.util.ArrayList;
import java.util.Scanner;

/* 
1- When you post a message on Facebook, depending on the number of people who like your post, 
Facebook displays different information.
If no one likes your post, it doesn't display anything.
If only one person likes your post, it displays: [Friend's Name] likes your post.
If two people like your post, it displays: [Friend 1] and [Friend 2] like your post.
If more than two people like your post, it displays: [Friend 1], [Friend 2] and [Number of Other People] others like your post.
Write a program and continuously ask the user to enter different names, until the user presses Enter (without supplying a name). 
Depending on the number of names provided, display a message based on the above pattern. 
*/

public class Likes {
    
    Scanner input = new Scanner(System.in);

    public void PostLikes() {
        ArrayList<String> names = new ArrayList<String>();
        while(true){
            System.out.print("Please enter a friend's name (or press Enter to exit): ");
            String inputName = input.nextLine();
            if(inputName.isEmpty()){
                break;
            }
            names.add(inputName);
        }
        
        if(names.size()>2){
            int size = names.size()-2;
            System.out.println(names.get(0) + ", " + names.get(1) + ", and " + size + " other like your post.");
        } else if(names.size()==2){
            System.out.println(names.get(0) + " and " + names.get(1) + " like your post.");
        } else {
            System.out.println(names.get(0) + " likes your post.");
        }
    }
}
