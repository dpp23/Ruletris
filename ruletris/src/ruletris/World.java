package ruletris;

import net.sourceforge.jetris.*;

public class World
{
	private static JetrisMainFrame mf;
	private boolean isPieceNotDropped;
	public World()
	{
		isPieceNotDropped = true;
		mf = new JetrisMainFrame();
	}
	
	
	//sets the grid configuration using arr (at least 20x10 int matrix), values 1-7 give different colours
	public boolean setGrid(int  arr [][])
	{
		if(arr.length < 20) return false;
		if(arr[0].length < 10) return false;
		mf.getGrid().setGrid(arr);
		mf.updateGrid(1);
		return true;
	}
	
	
	
	public void setNextFigure(int i)
	{
		mf.setNextFigure(mf.ff.getFigure(i));
	}
	
	public  int[][] getCurrentGrid()
	{
		return mf.getGrid().toArray();
	}

	public  int[][] getCurrentPiece()
	{
		return mf.getCurrentFigure().toArray();
	}
	
	public int[][] getNextPiece()
	{
		return mf.getFigure().toArray();
	}
	
	//!!! all the methods bellow return true if successful and false otherwise, without any side effects
	
	//The next two methods change the current and next figure respectively
		//for i = 0 Figure I, 
		//for i = 1 Figure T, 
		//for i = 2 Figure O, 
		//for i = 3 Figure L, 
		//for i = 4 Figure J, 
		//for i = 5 Figure S,
		//default Figure Z 
	public boolean setCurrentFigure(int i)
	{
		if(!isPieceNotDropped)return false;
		mf.setCurrentFigure(mf.ff.getFigure(i));
		mf.updateGrid(1);
		return true;
	}
		
	//rotates the current piece r times clockwise
	public boolean rotatePiece(int r)
	{
		if(!isPieceNotDropped)return false;
		for(int i = 0; i < r; i++)
		{
			if(!mf.rotationTry())return false;
			mf.updateGrid(1);
		}
		
		return true;
	}

	//moves the current piece m squares to the left
	public boolean moveLeft(int m)
	{
		if(!isPieceNotDropped)return false;
		for(int i=0; i < m; i++)
		{
			mf.moveLeft();
		}
		mf.updateGrid(1);
		return true;
	}
	
	//Moves the current piece m squares to the right
	public boolean moveRight(int m)
	{
		if(!isPieceNotDropped)return false;
		for(int i=0; i < m; i++)
		{	
			mf.moveRight();
		}
		mf.updateGrid(1);
		return true;
	}

	public boolean dropPiece()
	{
		if(!isPieceNotDropped)return false;
		mf.moveDrop();
		mf.updateGrid(1);
		isPieceNotDropped = false;
		return true;
	}
	
	//moves the next piece onto the grid
	public boolean nextPieceOnGrid()
	{
		if(isPieceNotDropped) dropPiece();
		mf.addFigure();
		mf.updateGrid(1);
		isPieceNotDropped = true;
		return true;
	}
	
}
