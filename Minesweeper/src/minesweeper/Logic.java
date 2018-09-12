/*
 Sebastián Villegas
 jhoan.villegas@correounivalle.edu.co
 */
package minesweeper;

import java.util.Scanner;

public class Logic {
    
    final private Field[][] board;
    final private int height;
    final private int width;
    final private int nMines;
    private int remainingMines;
    private int flags;
    private boolean gameContinue;

    
    public Logic (int hei, int wid, int mines){
        
        this.height         = hei;
        this.width          = wid;
        this.nMines         = mines;
        this.remainingMines = this.nMines;
        this.flags          = 0;
        this.gameContinue   = true;
        board               = new Field[height][width];
        
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = new Field();
            }            
        }
        
        startGame();
        
    }

    private void startGame() {
        
        Scanner scan = new Scanner(System.in);
        int row = 0;
        int column = 0;
        String action = "";
        
        fillBoard();
        showMines();
        System.out.println("");
        showBoard();
        
        System.out.println("");
        System.out.println("Now you can start playing.");
        System.out.println("Make a move with the next format: 'row column action', for example 3 6 U.");
        System.out.println("There are 2 actions, undercover 'U' and flag 'M'.");
        
        while(gameContinue){
            System.out.println("Make the next move.");
            row = scan.nextInt();
            column = scan.nextInt();
            action = scan.nextLine();
            
            makeMove(row - 1, column - 1, action);
                    
        }
        
    }

    private void fillBoard() {
        
        for (int i = 0; i < nMines; i++) {
            int randomRow   = (int) (Math.random() * height + 0); 
            int randomColumn = (int) (Math.random() * width + 0);
            
            if (board[randomRow][randomColumn].isMine()) {
                i--;                
            } else {
                board[randomRow][randomColumn].setMine(true);
            }
            
        }
        
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (!board[i][j].isMine()) {
                    changeMinesAround(i,j);
                }
            }
        }
    }
    
    private void showMines() {

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (board[i][j].isMine()) {
                    System.out.print("* ");
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
    
    private void showBoard() {

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) { 
                if (board[i][j].isSelected()) {
                    if (board[i][j].getMinesAround() == 0) {
                        System.out.print("- ");
                    } 
                    else {
                        System.out.print(board[i][j].getMinesAround() + " ");
                    }
                } 
                else if (board[i][j].isFlag()) {
                    System.out.print("P ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println("");
        }

    }
    
    private void showBoard(int row, int column) {

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i == row && j == column) {
                    System.out.print("x ");
                } else {
                    if (board[i][j].isMine() && board[i][j].isFlag()) {
                        System.out.print("+ ");
                    } 
                    else if (board[i][j].isMine()) {
                        System.out.print("* ");
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
            }
            System.out.println("");
        }

    }

    private void changeMinesAround(int row, int column) {
       
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                if (i >= 0 && i < height && j >= 0 && j < width && (i != row || j!= column)) {
                    if (board[i][j].isMine()) {
                        board[row][column].addMinesAround();
                    }
                }
            }
        }
    }

    private void makeMove(int row, int column, String action) {
        
        if (action.equals(" U")) {
            if (board[row][column].isMine()) {
                System.out.println("");
                gameOver(row, column);
            } else {
                if (board[row][column].isFlag()) {
                    board[row][column].setFlag(false);
                    this.flags--;
                }
                
                if (board[row][column].getMinesAround() > 0) {
                    board[row][column].setSelected(true);
                    
                } else {
                    uncoverEmpties(row, column);
                }
                showBoard();
            }
            
        } else if (action.equals(" M")) {
            board[row][column].setFlag(!board[row][column].isFlag());
            
            if (board[row][column].isFlag()) {
                this.flags++;
            } else {
                this.flags--;
            }
            
            if(board[row][column].isMine()){
                this.remainingMines--;
            }
            
            System.out.println("banderas" + flags);
            System.out.println("minas que quedan" + remainingMines);
            
            if (remainingMines == 0) {
                if (this.flags == this.nMines) {
                    showBoard();
                    System.out.println("Victory, great job.");
                    this.gameContinue = false;
                }
            } else {
                showBoard();
            }
        }
    }

    private void gameOver(int row, int column) {
        
        this.gameContinue = false;
        showBoard(row,column);
        System.out.println("Ouh! You took a mine, game over. Try again.");
    }

    private void uncoverEmpties(int row, int column) {
        
        if (!board[row][column].isSelected() ) {
            for (int i = row - 1; i <= row + 1; i++) {
                for (int j = column - 1; j <= column + 1; j++) {
                    if (i >= 0 && i < height && j >= 0 && j < width && (i != row || j!= column)) {
                        if (board[i][j].getMinesAround() > 0) {
                            board[i][j].setSelected(true);
                        } else {
                            board[row][column].setSelected(true);
                            uncoverEmpties(i,j);
                        }
                    }
                }
            }
        }
    }
   
}
