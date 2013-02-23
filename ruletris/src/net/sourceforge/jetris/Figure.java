package net.sourceforge.jetris;
import java.awt.Color;

public abstract class Figure {
    
    protected final static int I = 1;
    protected final static int T = 2;
    protected final static int O = 3;
    protected final static int L = 4;
    protected final static int J = 5;
    protected final static int S = 6;
    protected final static int Z = 7;
    protected final static int NONE = 0;
    
    protected final static Color COL_I = Color.RED;
    protected final static Color COL_T = Color.GRAY;
    protected final static Color COL_O = Color.CYAN;
    protected final static Color COL_L = Color.ORANGE;
    protected final static Color COL_J = Color.MAGENTA;
    protected final static Color COL_S = Color.BLUE;
    protected final static Color COL_Z = Color.GREEN;
    protected final static Color COL_NONE = Color.WHITE;
    
    
    protected int[] arrX;
    protected int[] arrY;
    
    protected int offsetX;
    protected int offsetY;
    
    protected int offsetXLast;
    protected int offsetYLast;
    
    protected Figure(int[] arrX, int[]arrY) {
        this.arrX = arrX;
        this.arrY = arrY;
        offsetYLast = offsetY = 0;
        offsetXLast = offsetX = 4;
    }
    
    protected int getMaxRightOffset() {
        int r = Integer.MIN_VALUE;
        for (int i = 0; i < arrX.length; i++) {
            if(r < arrX[i]) r = arrX[i];
        }
        return r+offsetX;
    }
    
    public void setOffset(int x, int y) {
        offsetXLast = offsetX;
        offsetYLast = offsetY; 
        offsetX = x;
        offsetY = y;
    }
   
    
    public int getYoffset()
    {
    	return offsetY;
    }
    public int getXoffset()
    {
    	return offsetX;
    }
    protected void resetOffsets() {
        offsetX = offsetY = offsetXLast = offsetYLast = 0;
    }
    
    public abstract void rotationRight();
    
    public int[][] toArray()
    {
    	int [][]temp = new int[4][4];
    	for(int i=0;i<4;i++)
    			temp[arrY[i]][arrX[i]]=1;
    	return temp;
    }
    
    protected abstract void rotationLeft();
    
    protected abstract int getGridVal();
    
    protected abstract Color getGolor(); 
}