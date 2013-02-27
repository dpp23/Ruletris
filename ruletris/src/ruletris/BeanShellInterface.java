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
    
    /*
     * Initializes the game and for each subsequent piece executes supplied
     * script until the game is over.
     */
    public static void run(String scriptFileName, World world) {
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
        while(true) {
            try {
                inter.source(scriptFileName);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(BeanShellInterface.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(BeanShellInterface.class.getName()).log(Level.SEVERE, null, ex);
            } catch (EvalError ex) {
                //TODO: Direct this message to a place visible by user.
                System.out.println(ex.getMessage());
            }
            
            world.nextPieceOnGrid();
        }
    }
    
    /*
     * Starts the program with the script specified as the fist argument.
     */
    public static void main (String args[]) {
        if(args.length < 1) {
            System.err.println("missing script filename");
            return;
        }
       // run(args[0]);
    }
    
}
