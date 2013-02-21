package ruletris;



import net.sourceforge.jetris.*;

public class World
{
	private final JetrisMainFrame mf;
	private boolean isPieceDropped;
	private boolean isGameOver;
	public World(JetrisMainFrame newMf)
	{
		mf = newMf;
		isPieceDropped = false;
		isGameOver = false;
		nextPieceOnGrid();
		isPieceDropped = false;
	}
	
	/*
	 * Let the callback tell us when the game is over. 
	 */
	public void gameOver(boolean gameState)
	{
		isGameOver = gameState;
	}
	
	//the following two methods return respectfully the X and Y offset of the current piece
	//if there is not a current piece in motion, return 0;
	public int getYoffset()
	{
		if(!isPieceDropped || isGameOver)return 0;
		return mf.getCurrentFigure().getYoffset();
	}
	public int getXoffset()
	{
		if(!isPieceDropped || isGameOver)return 0;
		return mf.getCurrentFigure().getXoffset();
	}
	
	
	//sets the grid configuration using arr (at least 20x10 int matrix), values 1-7 give different colours
	public boolean setGrid(int  arr [][])
	{
		if(isGameOver)return false;
		if(arr.length < 20) return false;
		for(int i=0; i<20; i++)
			if(arr[i].length < 10) return false;
		mf.getGrid().setGrid(arr);
		mf.paintTG();
		mf.updateGrid(1);
		return true;
	}
	
	
	
	public void setNextFigure(int i)
	{
		if(isGameOver)return;
		mf.setNextFigure(mf.ff.getFigure(i));
	}
	
	public  int[][] getCurrentGrid()
	{
		return mf.getGrid().toArray();
	}

	public  int[][] getCurrentPiece()
	{
		if(!isPieceDropped || isGameOver) return new int[4][4];
		return mf.getCurrentFigure().toArray();
	}
	
	public int[][] getNextPiece()
	{
		if(!isPieceDropped || isGameOver) return new int[4][4];
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
		if(!isPieceDropped || isGameOver)return false;
		mf.setCurrentFigure(mf.ff.getFigure(i));
		mf.updateGrid(1);
		return true;
	}
		
	//rotates the current piece r times clockwise
	public boolean rotatePiece(int r)
	{
		if(!isPieceDropped || isGameOver)return false;
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
		if(!isPieceDropped || isGameOver)return false;
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
		if(!isPieceDropped || isGameOver)return false;
		for(int i=0; i < m; i++)
		{	
			mf.moveRight();
		}
		mf.updateGrid(1);
		return true;
	}

	//returns false if the piece cannot be moved down or if it falls on the ground
	public boolean moveDown(int m)
	{
		if(!isPieceDropped || isGameOver)return false;
		while(m>0)
		{
			if(mf.moveDown())
			{
				isPieceDropped = false;
				return false;
			}
			m--;
		}
		mf.updateGrid(1);
		return true;
	}
	

	public boolean dropPiece()
	{
		if(!isPieceDropped || isGameOver)return false;
		mf.moveDrop();
		mf.updateGrid(1);
		isPieceDropped = false;
		return true;
	}
	
	//moves the next piece onto the grid
	public boolean nextPieceOnGrid()
	{
		if(isGameOver)return false; 
		if(isPieceDropped) return false; 
		mf.addFigure();
		mf.updateGrid(1);
		isPieceDropped = true;
		//isGameOver = mf.isGameOver();
		return true;
	}
	
}
