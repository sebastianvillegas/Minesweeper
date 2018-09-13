/*
 Sebastián Villegas
 jhoan.villegas@correounivalle.edu.co
 */
package minesweeper;

import java.util.Scanner;

/** This class manage all the logic of the game like the algorithms used to 
 * create the board and fill it with the objects Field o evaluate every case
 * in the game.

   @author [Sebastián Villegas]
*/
public class Logic {
    
    private Field[][] board; 
    private int height;              //number of rows
    private int width;               //number of columns
    private int nMines;              //number of mines
    private int remainingMines;      //Mines in a field without flag.
    private int flags;               //Number of flags the user has used.
    private boolean gameContinue;    //Flag to know if the game has to continue or finish.

    /**
     * Constructor
     * @param hei
     * Number of rows the user indicated
     * @param wid
     * Number of columns the user indicated
     * @param mines 
     * Number of mines the user indicated
     */
    public Logic (int hei, int wid, int mines){
        
        this.height         = hei;
        this.width          = wid;
        this.nMines         = mines;
        this.remainingMines = this.nMines; 
        this.flags          = 0;
        this.gameContinue   = true;
        board               = new Field[height][width];
        
        //We initialize every field on the board
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = new Field();
            }            
        }
        
        //With everything initialized, we can start to play.
        startGame();
        
    }

    private void startGame() {
        
        Scanner scan = new Scanner(System.in);
        
        //Variables for ask the action to the user
        int row         = 0; 
        int column      = 0;
        String action   = "";
        
        //Fill the board with random mines and number of mines around.
        fillBoard();
        
        System.out.println("");
        //Show the board.
        showBoard();
        
        System.out.println("");
        System.out.println("Now you can start playing.");
        System.out.println("Make a move with the next format: 'row column action' e.g 3 6 U.");
        System.out.println("There are 2 actions, undercover 'U' and flag 'M'.");
        
        /*
        While something doesn't change the flag gameContinue,
        the program is going to ask for actions to the user to
        make movements/plays.
        */
        while(gameContinue){
            System.out.println("Make the next move.");
            row = scan.nextInt();
            column = scan.nextInt();
            action = scan.nextLine();
            System.out.println("");
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
                    else if (board[i][j].isFlag()) {
                        System.out.print("P ");
                    } else {
                        System.out.print(". ");
                    }
                }
            }
            System.out.println("");
        }

    }

    /**
     * This method calculates the mines around a field.
     * @param row
     * row of the field's position
     * @param column 
     * column of the field's position
     */
    private void changeMinesAround(int row, int column) {
       
        /*
        The iterators go through the fields around the field and if it founds
        a mine, it changes the state minesAround of the field.
        */
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                if (i >= 0 && i < height && j >= 0 && j < width && (i != row || j!= column)) {
                    /*
                    This conditions avoid ArrayIndexOutOfBound Error
                    */
                    if (board[i][j].isMine()) {
                        board[row][column].addMinesAround();
                    }
                }
            }
        }
    }

    /**
     * This method represents all the cases that can happen
     * when the user makes a movement and it's consequences.
     * 
     * @param row
     * The row of the position the user is going to make his move.
     * @param column
     * The column of the position the user is going to make his move.
     * @param action 
     * The action the user is going to make in the indicated position,
     * it could be "U" (undercover) or M"mine".
     */
    private void makeMove(int row, int column, String action) {
        
        Scanner scan = new Scanner(System.in);
        
        //First case: The user wants to undercover a field.
        if (action.equals(" U")) {
            //If it's a mine, it's game over for him.
            if (board[row][column].isMine()) {
                gameOver(row, column);
            } else {
                //If it's a flag, it removes it and undercover the field.
                if (board[row][column].isFlag()) {
                    board[row][column].setFlag(false);
                    //We indicate there are less flags on the board.
                    this.flags--;
                    
                    //When it undercover a field, it could be just a field because has mines around
                    //or it could be several fields because hasn't mines around.
                    if (board[row][column].getMinesAround() > 0) {
                        board[row][column].setSelected(true);                    
                    } else {
                        uncoverEmpties(row, column);
                        board[row][column].setSelected(true);
                    }
                    
                    //if you remove a flag, it could be you win, so the algorithm checks this.
                    if (remainingMines == 0) {            
                        showBoard();
                        System.out.println("");
                        if (this.flags == this.nMines) {
                            /*
                            If all the mines are with a flag and the number of flags is the same with the
                            initial numer of mines, it means the user has won.
                            */
                            System.out.println("Victory, great job.");
                            System.out.println("Start again? Insert 1 to continue or anything else to finish");
                            String option = scan.nextLine();
                            //It asks if the user wants to keep playing.
                            if (option.equals("1")) {
                                restart();
                            } else {
                                this.gameContinue = false;
                            }
                        }
                    }
                    showBoard();
                    System.out.println("");
                
                } else {
                    // when the field isn't mine neither flag
                    // it has to do the normal process to undercover.
                    if (board[row][column].getMinesAround() > 0) {
                        board[row][column].setSelected(true);                    
                    } else {
                        uncoverEmpties(row, column);
                        board[row][column].setSelected(true);
                    }
                    showBoard();
                    System.out.println("");
                }
            }
        //Second case: the user wants to put or remove a flag    
        } else if (action.equals(" M")) {
            //It changes the field's state to the opposite
            board[row][column].setFlag(!board[row][column].isFlag());
            
            //Depending if it's putting o removing the flag
            //it adds or substract to the variable flags.            
            if (board[row][column].isFlag()) {
                this.flags++;
            } else {
                this.flags--;
            }
            
            //If the field is a mine, it indicates there are less mines remaining in the game.
            if(board[row][column].isMine()){
                this.remainingMines--;
            }
            
            //if you put a flag, it could be you win, so the algorithm checks this.
            if (remainingMines == 0) {
                showBoard();
                System.out.println("");
                if (this.flags == this.nMines) {
                    /*
                    If all the mines are with a flag and the number of flags is the same with the
                    initial numer of mines, it means the user has won.
                    */
                    System.out.println("Victory, great job.");
                    System.out.println("Start again? Insert 1 to continue or anything else to finish");
                    String option = scan.nextLine();
                    //It asks if the user wants to keep playing.
                    if (option.equals("1")) {
                        restart();
                    } else {
                        this.gameContinue = false;
                    }
                }
            } else {
                showBoard();
                System.out.println("");
            }
        }
    }
    
    /**
     * This method is for when the user loses, it means he took a mine.
     * his parameters are row and column to indicate the position where
     * the program will print a "x".
     * @param row
     * row of the position the program will print a "x".
     * @param column 
     * column of the position the program will print a "x".
     */
    private void gameOver(int row, int column) {
        
        Scanner scan = new Scanner(System.in);
        showBoard(row,column);
        System.out.println("");
        System.out.println("Ouh! You took a mine, game over. Try again.");
        System.out.println("Start again? Insert 1 to continue or anything else to finish");
        String option = scan.nextLine();
        //It asks if the user wants to keep playing.
        if (option.equals("1")) {
            restart();
        } else {
            this.gameContinue = false;
        }
    }

    /**
     * Recursive algorithm to undercover the fields around a field
     * with 0 mines around.
     * 
     * @param row
     * row of the field's position is going to be undercovered.
     * @param column 
     * column of the field's position is going to be undercovered.
     */
    private void uncoverEmpties(int row, int column) {
        
        if (!board[row][column].isSelected() ) {
            /*
            it validates if field hasn't been undercovered before to avoid infinite recursion.
            The iterators go through every field around the field indicated and undercover it.
            */
            for (int i = row - 1; i <= row + 1; i++) {
                for (int j = column - 1; j <= column + 1; j++) {
                    if (i >= 0 && i < height && j >= 0 && j < width && (i != row || j!= column)) {
                        /*
                        This conditions avoid ArrayIndexOutOfBound Error
                        */
                        if (board[i][j].getMinesAround() > 0) {
                            board[i][j].setSelected(true);
                        } else {
                            board[row][column].setSelected(true);
                            //Recursive call
                            uncoverEmpties(i,j);
                        }
                    }
                }
            }
        }
    }

    /**
     * Method to restart the game if the user wants to continue playing.
     */
    private void restart() {
        Scanner scan = new Scanner(System.in);
        //It ask for all the parameters again an reinitialize the variables.
        System.out.println("Please insert the height, width, number of mines e.g 8 8 12.");
        this.height         = scan.nextInt();
        this.width          = scan.nextInt();
        this.nMines         = scan.nextInt();
        this.remainingMines = this.nMines;
        this.flags          = 0;
        this.gameContinue   = true;
        board               = new Field[height][width];
        
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = new Field();
            }            
        }
        int row       = 0;
        int column    = 0;
        String action = "";
        
        //Fill the board with the new fields, the random mines and calculate the mines around.
        fillBoard();        
        System.out.println("");
        showBoard();
        
        System.out.println("");
        System.out.println("Now you can start playing.");
        System.out.println("Make a move with the next format: 'row column action' e.g 3 6 U.");
        System.out.println("There are 2 actions, undercover 'U' and flag 'M'.");
        
        /*
        While something doesn't change the flag gameContinue,
        the program is going to ask for actions to the user to
        make movements/plays.
        */
        while(gameContinue){
            System.out.println("Make the next move.");
            row = scan.nextInt();
            column = scan.nextInt();
            action = scan.nextLine();
            System.out.println("");
            makeMove(row - 1, column - 1, action);
        }
    }
   
}
