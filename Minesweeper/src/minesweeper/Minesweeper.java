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
        System.out.println("Please insert the height.");
        int height    = scan.nextInt();
        System.out.println("Please insert the width.");
        int width = scan.nextInt();
        System.out.println("Please insert the number of mines.");
        int mines    = scan.nextInt();
        
        Logic logic = new Logic(height, width, mines);
                
        
    }
    
}
