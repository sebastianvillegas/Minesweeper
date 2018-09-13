/*
 Sebastián Villegas
 jhoan.villegas@correounivalle.edu.co
 */
package minesweeper;

/** This class simulates the fields on the game's board.
   It's necessary due to the states we need to know about every field
   * 
   @author [Sebastián Villegas]
*/
public class Field {
    
    private boolean mine;      //Indicates if the field is a mine or not.
    private boolean selected;  //Indicates if the field has been uncovered.
    private boolean flag;      //Indicates if the field has a flag.
    private int minesAround;   //Indicates the number of mines around the field.
    
// Constructor
    public Field (){
        this.mine        = false;
        this.selected    = false;
        this.flag        = false;
        this.minesAround = 0;
    }
  
    
// Setters and getters    
    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }
    
    public void addMinesAround(){
        this.minesAround++;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
       
    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getMinesAround() {
        return minesAround;
    }

    public void setMinesAround(int minesAround) {
        this.minesAround = minesAround;
    }
   
    
}
