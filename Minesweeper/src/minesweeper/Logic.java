/*
 Sebastián Villegas
 jhoan.villegas@correounivalle.edu.co
 */
package minesweeper;


public class Logic {
    
    private Field[][] board;
    private int height;
    private int width;
    private int nMines;

    
    public Logic (int hei, int wid, int mines){
        
        this.height = hei;
        this.width  = wid;
        this.nMines = mines;
        board       = new Field[height][width];
        
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = new Field();
            }            
        }
        
        startGame();
        
    }

    private void startGame() {
        
        fillBoard();
        showBoard();
        System.out.println("");
        System.out.println("Now you can start playing");        
    }

    private void fillBoard() {
        
        for (int i = 0; i < nMines; i++) {
            int randomFile   = (int) (Math.random() * height + 0); 
            int randomColumn = (int) (Math.random() * width + 0);
            
            if (board[randomFile][randomColumn].isMine()) {
                i--;                
            } else {
                board[randomFile][randomColumn].setMine(true);
            }
            
        }
        
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                changeMinesAround(board[i][j]);
                
            }
            
        }
        
        

    }
    
    private void showBoard() {

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (board[i][j].isMine()) {
                    System.out.print("P ");
                } 
                else if (board[i][j].isSelected()) {
                    if (board[i][j].getMinesAround() == 0) {
                        System.out.print("- ");
                    } 
                    else {
                        System.out.print(board[i][j].getMinesAround() + " ");
                    }
                } 
                else {
                    System.out.print(". ");
                }
            }
            System.out.println("");
        }

    }

    private void changeMinesAround(Field field) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
