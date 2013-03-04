package ruletris;

public class LevelStep {

	private  String helpText;
	private  String codeText;
	private  boolean[] allowedBlocks; 
	private  int[][] presetLayout;
	private  boolean last;
	private  boolean first;
	private  LevelStep next; 
	private  LevelStep prev;
	
	public LevelStep()
	{
		helpText = "";
		codeText = ""; 
		allowedBlocks = new boolean[7];
		for (int i = 0; i < 7; i++) { allowedBlocks[i] = true; }
		presetLayout = new int[20][10];
		for (int i = 0; i < 20; i++) { for (int j = 0; j < 10; j++) { presetLayout[i][j] = 0; } }
		first = false;
		last = false;
		next = null; 
		prev = null;
	}

	public void setNext(LevelStep node) { next = node; }
	public LevelStep getNext()          { return next; }	
	public void setPrev(LevelStep node) { prev = node; }
	public LevelStep getPrev()          { return prev; }	
	
	public void setLast(boolean l)      { last = l; }
	public void setFirst(boolean f)     { first = f; }	
	public boolean isLast()      { return last; }
	public boolean isFirst()     { return first; }

	public void addHelpText(String text)    { helpText += text; }
	public void addInjectCode(String code)  { codeText += code; }
	
	public void addBlockSpecification(boolean[] blocks) 
	{ 
		if(blocks.length == 7) 
			allowedBlocks = blocks;
		else 
			System.err.println("Incorrect length of block specification.");
	}
	
	public void addPresetLayout(int[][] layout) 
	{
		if(layout.length == 20 && layout[0].length == 10) 
		{
			presetLayout = layout;
		}
		else
			System.err.println("Incorrect dimensions of preset layout.");
	}
	
	public String getInjectCode()
	{
		return codeText;
	}
	
	public String getHelpText()
	{
		return helpText;
	}
	
	public boolean[] getAllowedBlocks() 
	{
		return allowedBlocks;
	}
	
	public int[][] getPresetLayout()
	{
		return presetLayout;
	}
}
