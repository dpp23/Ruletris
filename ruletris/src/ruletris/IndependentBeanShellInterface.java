package ruletris;

import bsh.EvalError;
import bsh.Interpreter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
	
	
	/*
	 * The class provides an interface between user-generated BeanShell script and
	 * Jetris implementation.
	 */

public class IndependentBeanShellInterface {
	     /*
	     * Initializes the game and for each subsequent piece executes supplied
	     * script until the game is over.
	     */
		private static boolean isOver = false;
		//private static String errFile = "error";

        private static GameGenerator parentGameGenerator = null; // parentGameGenerator is notified whenever a level is complete. Ignored if null.

        public static void setParent (GameGenerator parent) {
                parentGameGenerator = parent;
        }
	
	    public static void run(String scriptFileName, World currentWorld) {
	        World world = currentWorld;
            WorldUI worldUI = world;
	        Interpreter inter = new Interpreter();    //BeanShell interpreter.
	        
	        /*
	         * Expose world to the user in the script. No exceptions are expected
	         * to show up here.
	         */
	        try {
	            inter.set("world", worldUI);
	        } catch (EvalError ex) {
	            Logger.getLogger(BeanShellInterface.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        
	        /*
	         * Execute the script and get the next piece.
	         * The script is assumed to be written correctly.
	         */
	        while(!isOver) {
	            try {
	                inter.source(scriptFileName);
	            } catch (FileNotFoundException ex) {
	                Logger.getLogger(BeanShellInterface.class.getName()).log(Level.SEVERE, null, ex);
	            } catch (IOException ex) {
	                Logger.getLogger(BeanShellInterface.class.getName()).log(Level.SEVERE, null, ex);
	            } catch (EvalError ex) {
	            	world.errorOutput(ex.getMessage());
	            	
	            	break;
	            }
	            
	            world.nextPieceOnGrid();

                    // Check if objectives met (i.e., if level complete)
                    if (parentGameGenerator != null) {
                        Levels level = parentGameGenerator.getCurrentLevels();
                        if (level != null) {
                                Objectives o = level.getObjectives();
                                if(o != null)//TODO: Is this correct? Dimitar
	                                if (   (o.numHoles==-1 || o.numHoles == WorldUtils.numHoles(world))
	                                    && (o.height==-1 || o.height == WorldUtils.height(world))
	                                    && (o.heightUnder==-1 || WorldUtils.height(world) < o.heightUnder) )
	                                {
	                                        parentGameGenerator.objectivesMet();
	                                        break;
	                                }
                        }
                    }

	        }
	        isOver = false;
	    }

		public void isOver() 
		{
			isOver = true;// TODO Auto-generated method stub	
		}

		public void simulateWorld(String scriptFileName, World currentworld)
		{
			 World world = currentworld;
	         WorldUI worldUI = world;
		     Interpreter inter = new Interpreter();
		     try
		     {
		         inter.set("world", worldUI);
		         inter.source(scriptFileName);
		     }
		     catch (FileNotFoundException ex)
		     {
		         Logger.getLogger(BeanShellInterface.class.getName()).log(Level.SEVERE, null, ex);
		     }
		     catch (IOException ex)
		     {
		         Logger.getLogger(BeanShellInterface.class.getName()).log(Level.SEVERE, null, ex);
		     }
		     catch (EvalError ex)
		     {
		         world.errorOutput(ex.getMessage());
		         return;
		     }
		     world.errorOutput("No Errors! :)");
		}
	}
