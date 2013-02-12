package ruletris;



public class Start {
	private static World world;
    public static void main(String[] args)
    {
        world = new World();
        
    	int [][] temp = world.getCurrentPiece();
    	for(int k=0;k<4; k++)
    	{
    		for(int p=0;p<4;p++)
    			System.out.print(temp[k][p]);
    		System.out.println();
    	}
    	System.out.println();
       
        try {
        	world.moveLeft(4);
        	Thread.sleep(2000);
			world.rotatePiece(1);
			Thread.sleep(2000);
			world.dropPiece();
		    Thread.sleep(1000);
		    world.nextPieceOnGrid();
		    Thread.sleep(1000);
		    world.moveLeft(4);
        	Thread.sleep(2000);
			world.rotatePiece(1);
			Thread.sleep(2000);
			world.dropPiece();
		    Thread.sleep(1000);
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
