package ruletris;

// Provides some utilities on the world which we don't want to expose via BeanShell.
public class WorldUtils {

        public static int numHoles (World world) {
                int[][] grid = world.getCurrentGrid();
                int n=0;
                for (int x=0; x<grid[0].length; x++) {
                        int blocked = 0;
                        for (int y=0; y<grid.length; y++)
                                if (grid[y][x]==1) blocked=1;
                                else if (blocked==1) n++;
                }
                return n;
        }

        public static int height (World world) {
                int[][] grid = world.getCurrentGrid();
                int n=0;
                for (int y=grid.length-1; y>=0; y--) {
                        boolean found = false;
                        for (int x=0; x<grid[y].length; x++)
                                if (grid[y][x]==1) {n++; found = true; break;}
                        if (!found) break;
                }
                return n;
        }   
}
