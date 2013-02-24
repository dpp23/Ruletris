package ruletris;

// Each level has a set of objectives required to be met for it to be deemed completed by the user.
public class Objectives
{
        // Use -1 to ignore a metric. If you update this list, remember to update parseLevelFile().
        // Note: setting all metrics to -1 will unconditionally call objectivesMet().
        public int numHoles=-1, 
                   height=-1,
                   heightUnder=-1;
}
