
package ruletris;

/*
 * This interface specifies the methods of World available to the user.
 */
public interface WorldUI {
    public int getYoffset();
    public int getXoffset();
    public  int[][] getCurrentGrid();
    public  int[][] getCurrentPiece();
    public int[][] getNextPiece();
    public boolean rotatePiece(int r);
    public boolean moveLeft(int m);
    public boolean moveRight(int m);
    public boolean moveDown(int m);
    public boolean dropPiece();
    
}
