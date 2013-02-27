
package ruletris;

/*
 * This interface specifies the methods of World available to the user.
 */
public interface WorldUI {
    
    /*
     * All methods below adhere to the following conventions:
     * 1. The grid is a 20x10 array.
     * 2. The piece is a 4x4 array.
     * 3. 0 corresponds to empty space.
     * 4. Positive integer corresponds to occupied space.
     * 5. (0,0) is top left corner.
     * 6. Coordinates grow downwards and to the left.
     * 7. Currently moving piece is not included in the grid array.
     * 8. The piece is located in the top left corner of the piece array.
     * 9. If a move is not possible no action is performed.
     * 10. If the number of specified moves is too large, as many moves as
     *      possible will be performed.
     */
    
    /*
     * Returns an array representing current grid.
     */
    public  int[][] getCurrentGrid();
    
    /*
     * Returns an array representing the current element
     * in its current orientation.
     * If there is no piece moving, an array of zeros is returned.
     */
    public  int[][] getCurrentPiece();
    /*
     * Returns an array describing the next element.
     */
    public int[][] getNextPiece();
    
    /*
     * The two methods below return the current offset of the piece in a grid.
     * Y corresponds to the number of empty columns between left border and the piece.
     * X corresponds to the number of empty rows between top border and the piece.
     */
    public int getYoffset();
    public int getXoffset();
    
    /*
     * Rotates the current piece r times clockwise.
     */
    public boolean rotatePiece(int r);
    /*
     * Moves the current piece m columns to the left.
     */
    public boolean moveLeft(int m);
    /*
     * Moves the current piece m columns to the right.
     */
    public boolean moveRight(int m);
    /*
     * Moves the current piece m rows down.
     */
    public boolean moveDown(int m);
    /*
     * Drops the current piece.
     */
    public boolean dropPiece();
    
}
