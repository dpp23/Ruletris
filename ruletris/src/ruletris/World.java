package ruletris;

import net.sourceforge.jetris.*;

public class World
{
	private static JetrisMainFrame mf;
	
	public World()
	{
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
	
	//rotates the current piece r times clockwise
	public void rotatePiece(int r)
	{
		for(int i = 0; i < r; i++)
		{
			mf.getCurrentFigure().rotationRight();
			mf.updateGrid(4);
		}
	}

	//moves the current piece m squares to the left
	public void moveLeft(int m)
	{
		for(int i=0; i < m; i++)
		{
			mf.moveLeft();
			mf.updateGrid(4);
		}
	}
	
	//Moves the current piece m squares to the right
	public void moveRight(int m)
	{
		for(int i=0; i < m; i++)
		{	
			mf.moveRight();
			mf.updateGrid(4);
		}
	}

	public void dropPiece()
	{
		mf.moveDrop();
		mf.updateGrid(4);
	}
}
