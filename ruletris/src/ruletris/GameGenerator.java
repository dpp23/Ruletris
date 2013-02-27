package ruletris;

import net.sourceforge.jetris.JetrisMainFrame;
import ruletris.IndependentBeanShellInterface; 

/*
 * All hte important bits are now done through the game generator. 
 * 
 * Here we have all the functions that the GUI and the interpreter need to be able 
 * to speak to all the other bits. 
 *
 * 
 */


public class GameGenerator {

	private static World world;
	private static IndependentBeanShellInterface bsi;
	private static TutorialManager tut; 
	private static JetrisMainFrame mf;
	
	
	public GameGenerator()
	{
    	tut = new TutorialManager(1,4);
    	mf = new JetrisMainFrame(this);
    	world = new World(mf);
    	bsi = new IndependentBeanShellInterface();

        IndependentBeanShellInterface.setParent(this);
	}

/*-------------------------------------------------------------------------
 *          These are all callback functions from the GUI. 
 *          They use the Listener class. 
 * -------------------------------------------------------------------------
 */
	
	
	/*
	 *  This is what is run when the COMPILE button on the UI is pressed. 
	 */
	
	public void runFullWorld(final String file)
	{
		
		Thread interp = new Thread() 
		{
			  public void run() 
			  {
				  IndependentBeanShellInterface.run(file, world);
			  }
		};
		
		interp.isDaemon();
		interp.start();
	}

	public void gameOver()
	{
		bsi.isOver(); 
		world.gameOver(true);
		
	}
	
	
	/*--------------------------------------------------
	 *               To do with tutorials
	 * --------------------------------------------------
	 */
	
	
	public LevelStep getNextHelp()
	{
		return tut.getNextHelp(); 
	}
	
	public LevelStep getPrevHelp()
	{
		return tut.getPrevHelp(); 
	}
	
	public LevelStep getCurrentHelp()
	{
		return tut.getCurrentHelp(); 
	}
	
	public boolean getNewLevel()
	{
		return tut.loadNextLevel(); 
	}
	
	public void isNewLevel()
	{
		bsi.isOver(); 
	}

        // Needed by IndependentBeanShellInterface for objectives stuff (see run())
        public Levels getCurrentLevels()
        {
                return tut.getCurrentLevels();
        }
	
	/*
	 * Get the new board to start the test. This will be stored in a file somewhere. 
	 */
	public int[][] getNextSetup()
	{
		return null;
	}

        // This is called by IndependentBeanShellInterface whenever the user has completed the current level, e.g. has filled in all gaps.
        public void objectivesMet ()
        {
                System.out.println("Level complete!");
        }
	
	/*--------------------------------------------------
	 *            To do with running world
	 * --------------------------------------------------
	 */
	
	
	/*
	 * runBasicWorld is currently NOT used by anything.
	 */
	
	public void runBasicWorld(String file)
	{
		
    	int [][] temp = world.getCurrentPiece();
    	for(int k=0;k<4; k++)
    	{
    		for(int p=0;p<4;p++)
    			System.out.print(temp[k][p]);
    		System.out.println();
    	}
    	System.out.println();
       
        try {
        	int t [][] = 
         	   { {0,0,0,0,0,0,0,0,0,0},
         		 {0,0,0,0,0,0,0,0,0,0},	
         		 {0,0,0,0,0,0,0,0,0,0},
         		 {0,0,0,0,0,0,0,0,0,0},
         		 {0,0,0,0,0,0,0,0,0,0},
         		 {0,0,0,0,0,0,0,0,0,0},
         		 {0,0,0,0,0,0,0,0,0,0},
         		 {0,0,0,0,0,0,0,0,0,0},
         		 {0,0,0,0,0,0,0,0,0,0},
         		 {0,0,0,0,0,0,0,0,0,0},
         		 {0,0,0,0,0,0,0,0,0,0},
         		 {0,0,0,0,0,0,0,0,0,0},
         		 {0,0,0,0,0,0,0,0,0,0},
         		 {0,0,0,0,0,0,0,0,0,0},
         		 {0,0,0,0,0,0,0,0,0,0},
         		 {0,0,0,0,0,0,0,0,0,0},
         		 {0,0,0,0,0,0,0,0,0,0},
         		 {0,0,0,1,0,0,0,0,0,0},
         		 {0,0,0,1,0,0,0,0,0,0},
         		 {0,0,0,1,0,0,0,0,0,0}};
         	Thread.sleep(500);
         	world.setGrid(t);
        	while(true){
        		world.setCurrentFigure(5);
        		world.setNextFigure(0);
        	Thread.sleep(500);
        	//world.moveLeft(4);
        	Thread.sleep(500);
			//world.rotatePiece(1);
			Thread.sleep(500);
			world.dropPiece();
		    Thread.sleep(500);
		    world.nextPieceOnGrid();
        	}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
        temp = world.getCurrentPiece();
    	for(int k=0;k<4; k++)
    	{
    		for(int p=0;p<4;p++)
    			System.out.print(temp[k][p]);
    		System.out.println();
    	}
    	System.out.println();
		
	}
	

    
}

