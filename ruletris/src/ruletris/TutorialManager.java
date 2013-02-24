package ruletris;


/*
 * The idea here is that we have a Tutorial Manager that keeps track of the current level and provide
 * everything that is needed for that level including:
 * 				- hints for the help box
 * 				- Pre-determined block arrangments that the user must seek to fill... which are passed to the tetris world.
 * 
 * Each level has its own object which is created at the start of the level and then destroyed at
 * the end of the level. 
 * 
 * The levels are obtained from level_1.txt files which are parsed on initialisation.
 * 
 */

public class TutorialManager 
{
	private int currentLevel;
	private int maxLevel;
	private Levels level; 
	
	public TutorialManager(int startLevel, int lastLevel)
	{
		currentLevel = startLevel;
		maxLevel = lastLevel;
		level = new Levels(currentLevel);
	}
	
	public LevelStep getNextHelp() 
	{
		return level.getNextHelp();
	}
	
	public LevelStep getCurrentHelp() 
	{
		return level.getCurrentHelp();
	}

	public LevelStep getPrevHelp() 
	{
		return level.getPrevHelp();
	}
	
	public int[][] getNextSetup() 
	{
		return null;	
	}

        // Needed by InderpendentBeanShellInterface
        public Levels getCurrentLevels ()
        {
                return level;
        }
	
	public boolean loadNextLevel()
	{
		if(currentLevel < maxLevel)
		{
			currentLevel++;
			level = new Levels(currentLevel);
			return true;
		}
		else { return false; }
	}
	
	public boolean loadPrevLevel()
	{
		if(currentLevel > 1)
		{
			currentLevel--;
			level = new Levels(currentLevel);
			return true;
		}
		else { return false;} 
	}


}
