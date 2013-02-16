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
