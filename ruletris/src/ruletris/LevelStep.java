package ruletris;

public class LevelStep {

	private  String helpText;
	private  String codeText;
	private  boolean last;
	private  boolean first;
	private  LevelStep next; 
	private  LevelStep prev;
	
	public LevelStep()
	{
		helpText = "";
		codeText = ""; 
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
	
	public String getInjectCode()
	{
		return codeText;
	}
	
	public String getHelpText()
	{
		return helpText;
	}	
}
