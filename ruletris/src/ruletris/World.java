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
