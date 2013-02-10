package ruletris;

public class Start {
	private static World world;
    public static void main(String[] args)
    {
        world = new World();
        world.rotatePiece(2);
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        world.moveLeft(2);
        world.dropPiece();
        
        //world.rotatePiece(3);
        try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        world.nextPieceOnGrid();
    }

}
