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
        world.rotatePiece(2);
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        world.moveLeft(2);
        world.dropPiece();
        world.dropPiece();
        temp = world.getCurrentPiece();
    	for(int k=0;k<4; k++)
    	{
    		for(int p=0;p<4;p++)
    			System.out.print(temp[k][p]);
    		System.out.println();
    	}
    	System.out.println();
        //world.rotatePiece(3);
        try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        world.nextPieceOnGrid();
        //world.dropPiece();
    }

}
