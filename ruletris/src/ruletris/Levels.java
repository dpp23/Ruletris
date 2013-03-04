package ruletris;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * each level has a file called     .... level_1.txt for level 1. These files are found in the levels
 * file in the main directory. 
 * 
 * In a level file each line represents one "hint". If you want to inject code into their text editor then seperate 
 * the hint and the code to inject with a "%" charecter. be sure not to NOT start a new line though.
 * 
 * If you want to specify which blocks to use in a level step, separate the rest again with a "%" character, and give a 7-character binary string, as follows:
 * [hint text] % [code inject] % [block specification]
 * where [block specification] represents {FigureI, FigureT, FigureO, FigureL, FigureJ, FigureS, FigureZ}
 * e.g. "hinty stuff % world.someMethod(); % 0010000" to denote "only use square blocks".
 * 
 * If you want to specify a preset board layout for a level step, separate again with another "%" character, and give an identifier for a file, as follows:
 * [hint text] % [code inject] % [block specification] % [preset layout file id]
 * e.g. "hinty stuff % world.someMethod(); % 0010000 % config_1" in level_1.txt will indicate layout information is in level_1_config_1.dat
 * 
 * If you do not want to fill in a section in the middle of a line, use the syntax of "%%" to denote an empty field in the middle
 * or "%%%" to denote two empty fields in the middle.
 * 
 * The syntax for the config files is as follows: 
 * [height] 
 * [data (over multiple lines)]
 * e.g.:
 * 3 
 * 1001111111
 * 1011111111
 * 1011111111
 * 
 * This will indicate that this configuration is desired on the bottom 3 rows of the game.
 * 
 * Starting a line with "[" (and ending it with "]") allows you to specify objectives in the form
 * [objective=value]
 * For example, if to complete the level you require that the number of holes is zero, use [numHoles=0]
 * 
 */

/* TODO: file path is using windows-specific syntax. Have we used a generic filepath slash somewhere before?
 */

public class Levels {


    private Objectives objectives = null;
	private int level;
	private LevelStep primary;
	
	public Levels(int thisLevel)
	{
		level = thisLevel;
		primary = null;
		parseLevelFile();
	}
	
	public void parseLevelFile()
	{
		/*  Parse the level file into a doubly linked list of levelSteps. */

                objectives = null; // Ignore objectives unless we read some in.
		
		try 
		{
			FileInputStream fstream = new FileInputStream("levels/level_"+level+".txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			  
			LevelStep current = null;
			String line = null;
			while ((line = reader.readLine()) != null) 
			{
                    if (line.startsWith("[")) {
                            if (objectives == null)
                                    objectives = new Objectives();
                            String[] obj = line.substring(1, line.length()-1).split("=");
                            if (obj[0].equals("numHoles")) objectives.numHoles = Integer.parseInt(obj[1]);
                            else if (obj[0].equals("height")) objectives.height = Integer.parseInt(obj[1]);
                            else if (obj[0].equals("heightUnder")) objectives.heightUnder = Integer.parseInt(obj[1]);
                    }
                    else {

					        String [] sections = line.split("%");
					
					        if(primary == null)
					        {
						        primary = new LevelStep();
						        primary.setFirst(true);
						        current = primary;
					        }
					        else
					        {
						        LevelStep newStep = new LevelStep();
						        newStep.setPrev(current);
						        current.setNext(newStep);
						        current = newStep;
					        }
					        if (sections.length > 0 )
					        {
						        current.addHelpText(sections[0]);
					        	//current.addHelpText(Integer.toString(sections.length));
					        }
					        if (sections.length > 1)
					        {
						        current.addInjectCode(sections[1]);
					        }
					        if (sections.length > 2)
					        {
					        	String specifiedBlocks = sections[2].replaceAll("\\s", "");
						        if(sections[2].length() > 0)
						        {
						        	try
						        	{
						        		boolean allowedBlocks[] = new boolean[7];
						        		for(int i = 0; i < 7; i++)
						        		{ 
						        			char flag = specifiedBlocks.charAt(i);
						        			if(flag == '0') { allowedBlocks[i] = false; }
						        			else if (flag == '1') { allowedBlocks[i] = true; }
						        			else { throw new Exception(); }
						        		}
						        		current.addBlockSpecification(allowedBlocks);
						        	}
						        	catch(Exception e)
						        	{
						        		System.err.println("Incorrect syntax for block specification");
						        	}
						        }
					        }
					        if (sections.length > 3)
					        {
					        	String configFileId = sections[3].replaceAll("\\s", "");
						        FileInputStream cfStream = new FileInputStream("levels/level_"+level+"_"+configFileId+".dat");
						        DataInputStream configIn = new DataInputStream(cfStream);
						        BufferedReader configReader = new BufferedReader(new InputStreamReader(configIn));
						        String configLine = configReader.readLine();
						        int height = 20 - Integer.parseInt(configLine);
						        int[][] layout = new int[20][10];
						        for(int i = 0; i < 20; i++) { for(int j = 0; j < 10; j++) { layout[i][j] = 0; } }
								while ((configLine = configReader.readLine()) != null) 
								{
									for(int j = 0; j < 10; j++) 
									{
										if (configLine.charAt(j) == '1') { layout[height][j] = 1; }
									}
									height++;
								}
								current.addPresetLayout(layout);
					        }
					        if (sections.length > 4)
					        {
					        	System.err.println("Incorrect number of arguments for this LevelStep.");
					        }
                    }
			}
			current.setLast(true);
		} 
		catch (IOException x) 
		{
			System.err.println(x);
		}
		
		/* Test that the linked list works OKAY
		LevelStep test = primary;
		while(test != null)
		{
			System.out.println(test.getHelpText());
			test = test.getNext();
		}
		*/
	}

	public LevelStep getNextHelp() 
	{
		LevelStep returnStep = null;
		
		if(primary.getNext() != null)
		{
			primary = primary.getNext();
			returnStep = primary;
		}
		return returnStep;
	}
	
	public LevelStep getPrevHelp() 
	{
		LevelStep returnStep = null;
		
		if(primary.getPrev() != null)
		{
			primary = primary.getPrev();
			returnStep = primary;
		}
		return returnStep;
	}
	
	public LevelStep getCurrentHelp() 
	{
		return primary;
	}

        public Objectives getObjectives ()
        {
                return objectives;
        }
}

