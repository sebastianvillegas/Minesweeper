/*
 Sebastián Villegas
 jhoan.villegas@correounivalle.edu.co
 */
package minesweeper;

public class Field {
    
    private boolean mine;
    private boolean selected;
    private boolean flag;
    private int minesAround;
    
    public Field (){
        this.mine        = false;
        this.selected    = false;
        this.flag        = false;
        this.minesAround = 0;
    }
    
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
