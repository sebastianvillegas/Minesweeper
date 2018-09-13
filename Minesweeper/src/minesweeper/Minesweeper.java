/*
 Sebastián Villegas
 jhoan.villegas@correounivalle.edu.co
 */
package minesweeper;

import java.util.Scanner;

public class Minesweeper {
    
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        
        System.out.println("Welcome to the Minesweeper game.");
        System.out.println("Please insert the height, width and number of mines e.g 8 8 12.");
        int height    = scan.nextInt();
        int width     = scan.nextInt();
        int mines     = scan.nextInt();
        
        while(height > 85 && width > 85){
            System.out.println("height and width can't be bigger than 85");
            System.out.println("Please insert the height, width and number of mines e.g 8 8 12.");
            height    = scan.nextInt();
            width     = scan.nextInt();
            mines     = scan.nextInt();            
        }
        
        if (mines > (height * width)) {
            System.out.println("Adjusting the mines to the board's maximum capacity");
            mines = height * width;
        }
        //Instance of Logic, class who manage the game's logic.
        Logic logic = new Logic(height, width, mines);
                
        
    }
    
}
