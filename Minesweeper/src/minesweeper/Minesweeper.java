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
        
        //Instance of Logic, class who manage the game's logic.
        Logic logic = new Logic(height, width, mines);
                
        
    }
    
}
