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
public class BeanShellInterface {
    
    private static int delay = 500;  //Delay between subsequent drops in microseconds.
    
    /*
     * Initializes the game and for each subsequent piece executes supplied
     * script until the game is over.
     */
    public static void run(String scriptFileName) {
        World world = new World();
        Interpreter inter = new Interpreter();    //BeanShell interpreter.
        
        /*
         * Expose world to the user in the script. No exceptions are expected
         * to show up here.
         */
        try {
            inter.set("world", world);
        } catch (EvalError ex) {
            Logger.getLogger(BeanShellInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /*
         * Execute the script and get the next piece.
         * The script is assumed to be written correctly.
         * TODO: Prevent the user from getting next piece manually by hiding
         * method world.nextPieceOnGrid() from him.
         * TODO: Gracefully handle the end of the game.
         */
        while(true) {
            try {
                inter.source(scriptFileName);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(BeanShellInterface.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(BeanShellInterface.class.getName()).log(Level.SEVERE, null, ex);
            } catch (EvalError ex) {
                Logger.getLogger(BeanShellInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            world.nextPieceOnGrid();
        }
    }
    
    /*
     * The class is provided to enable testing algorithms.
     * It provides the same functionality as run(), only instead of reading a 
     * script it calls method algorithm().
     */
    public static void runJava () {
        World world = new World();
        
        while(true) {
            algorithm(world);
            world.dropPiece();
            world.nextPieceOnGrid();
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                Logger.getLogger(BeanShellInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /*
     * The algorithm to be executed. Should be written just as the bsh script.
     */
    private static void algorithm (World world) {
        
    }
    
    /*
     * Starts the program with the script specified as the fist argument.
     */
    public static void main (String args[]) {
        
        runJava();
    }
    
}
