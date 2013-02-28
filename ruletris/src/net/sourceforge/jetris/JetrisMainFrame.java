package net.sourceforge.jetris;
/* MainFrame created on 14.09.2006 */
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.io.*;
import javax.swing.text.*;
import ruletris.GameGenerator;
import ruletris.LevelStep;
import ruletris.World;


import net.miginfocom.swing.MigLayout;
import net.sourceforge.jetris.io.PublishHiScore;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;

public class JetrisMainFrame extends JFrame implements ActionListener  {
    
	 /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private static final String NAME = "RULETRIS 0.1";
	    private static final int CELL_H = 24;
	    
	    private Font font;
	    private JPanel playPanel;
	    private JLabel score;
	    private JLabel lines;
	    private JLabel time;
	    private JLabel[] statsF;
	    private JLabel[] statsL;
	    private JLabel levelLabel;
	    private JLabel hiScoreLabel;
	    private JPanel[][] cells;
	    private TetrisGrid tg;
	    private JPanel[][] next;
	    private int nextX;
	    private int nextY;
	    private Figure f;
	    private Figure fNext;
	    public FigureFactory ff;
	    private boolean isNewFigureDroped;
	    private boolean isGameOver;
	    private boolean isPause;
	    private boolean singleLine;
	    private boolean singleLiner;
	    private Color nextBg;
	    private TimeThread tt;
	    private KeyListener keyHandler;
	    private World world;
	   
	    private JPanel about;
	    
	    //MENU
	    private JMenuItem jetrisRestart;
	    private JMenuItem jetrisPause;
	    private JMenuItem jetrisHiScore;
	    private JMenuItem jetrisExit;
	    
	    private JMenuItem helpAbout;
	    private JMenuItem helpJetris;
	    
	    private HelpDialog helpDialog;
	    
	    private JPanel hiScorePanel;
	    private PublishHandler pH;
	    private int count =0;
	    
	    
	    /*
	     *  ------------- Added by oeb21 -------------
	     */
	    
	    
	    class OutputBox extends JDialog {
	    	
	    	private boolean isHidden;
	    	private JTextArea text = new JTextArea("");
	    	private JScrollPane scroll;
	    	private JPanel panel = new JPanel(new BorderLayout())  
	    	{
	    		public Insets getInsets() {
	    			return new Insets(5, 10, 5, 5);
	    		}
	    	};
	    	
	    	
	    	public OutputBox() {
	    		super((Frame)null, "Error!!!");
	    		
	    		Dimension size = new Dimension(500, 50); 
	    		text.setPreferredSize(size); //(15,100)
	    		text.setMaximumSize(size);
	    		text.setMinimumSize(size);
	    		
	    		text.setEditable(false);
	  
	    		
	    		
	    		
	    		scroll =  new JScrollPane(text);
	    		panel.add(scroll);
	    		getContentPane().add(panel, BorderLayout.CENTER);

	    		addWindowListener(new WindowAdapter() {
	    			public void windowClosing(WindowEvent e) {
	    				setVisible(false);
	    				isHidden = true;
	    			}
	    		});
	    		
	    		pack();
	    		setVisible(false);
	    		isHidden = true;
	    	}
	    	
	    	public void Show()
	    	{
	    		setVisible(true);
	    		isHidden = false;
	    	}
	    	
	    	public void Hide()
	    	{
	    		setVisible(false);
	    		isHidden = true;
	    	}
	    	
	    	public boolean isShown()
	    	{
	    		return !isHidden;
	    	}
	    	
	    	public void setText(String newOutput)
	    	{
	    		text.setText(text.getText()+newOutput);
	    	}
	    	
	    	public void clearText()
	    	{
	    		text.setText("");
	    	}
	    }
	    
	    private GameGenerator parent;
	    
		/* This is the game panel for the tetris */
	    JPanel all = new JPanel(new BorderLayout());
	    JPanel panel = new JPanel(new MigLayout("fillx,insets 3"));

	    
	    private OutputBox outputWindow = new OutputBox();
	    
	    
	    private String currentFile = "Untitled";
		private boolean changed = false;

		
		private JEditorPane editArea = new JEditorPane();
		private JEditorPane helpArea = new JEditorPane();
		
		
	    JButton prevHelpButton = new JButton("Prev");
	    JButton nextHelpButton = new JButton("Next");
	    JButton nextLevelButton = new JButton("Next Level");
		
		private JFileChooser dialog = new JFileChooser(System.getProperty("user.dir"));
		private String currentDoc = "default";
		private boolean fileChanged = false;
			
		/*
		 * -----------end of added by Ollie----------------
		 */
		
		
		
		Action New = new AbstractAction("New", new ImageIcon("new.gif")) {
			public void actionPerformed(ActionEvent e) 
			{
					newFile();
			}
		};
		
		Action Open = new AbstractAction("Open", new ImageIcon("open.gif")) {
			public void actionPerformed(ActionEvent e) {
				saveOld();
				if(dialog.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
					readInFile(dialog.getSelectedFile().getAbsolutePath());
				}
				SaveAs.setEnabled(true);
			}
		};
		
		
		Action Save = new AbstractAction("Save", new ImageIcon("save.gif")) {
			public void actionPerformed(ActionEvent e) {
				if(!currentFile.equals("Untitled"))
					saveFile(currentFile);
				else
					saveFileAs();
			}
		};
		

		
		Action SaveAs = new AbstractAction("Save as...") {
			public void actionPerformed(ActionEvent e) {
				saveFileAs();
			}
		};
		
		Action errorOutput = new AbstractAction("Check for errors") {
			public void actionPerformed(ActionEvent e) {
				errorCheck();
			}
		};
		
		Action Compile = new AbstractAction("Compile") {
			public void actionPerformed(ActionEvent e) {
				compileCode();
			}
		};
		
		Action Hide = new AbstractAction("Toggle Output") {
			public void actionPerformed(ActionEvent e) {
				hideTetris();
			}
		};
		
		
		Action nextHelp = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				getNextHelp();
			}
		};
		
		Action prevHelp = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				getPrevHelp();
			}
		};
		Action nextLevel = new AbstractAction("Next Level") {
			public void actionPerformed(ActionEvent e) {
				getNextLevel();
			}
		};
		
		
		ActionMap m = editArea.getActionMap();
		Action Cut = m.get(DefaultEditorKit.cutAction);
		Action Copy = m.get(DefaultEditorKit.copyAction);
		Action Paste = m.get(DefaultEditorKit.pasteAction);
		
		
		/*
		 * -------------------------------------------
		 */
	   
    
   
    
    private class TimeThread extends Thread {
        
        private int hours;
        private int min;
        private int sec;
        
        private int count;
        
        private void incSec() {
            sec++;
            if(sec == 60) {
                sec = 0;
                min++;
            }
            if(min == 60) {
                min = 0;
                hours++;
            } 
        }
        
        private void resetTime() {
            hours = min = sec = 0;
        }
        
        public void run() {
            try {
                while (true) {
                    Thread.sleep(50);
                    if (isGameOver) {
                        Graphics g = playPanel.getGraphics();
                        Font font = new Font(g.getFont().getFontName(), Font.BOLD, 24);
                        g.setFont(font);
                        g.drawString("GAME OVER", 47, 250);

                    } else if(isPause) {
                        time.setText("PAUSED");
                    } else if(count >= 1000) {
                        count = 0;
                        incSec();
                        time.setText(this.toString());
                    } else {
                        count+=50;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } 
        }
        
        public String toString() {
            StringBuffer sb = new StringBuffer();
            if(hours < 10) {
                sb.append('0');
            }
            sb.append(hours);
            
            sb.append(':');
            
            if(min < 10) {
                sb.append('0');
            }
            sb.append(min);
            
            sb.append(':');
            
            if(sec < 10) {
                sb.append('0');
            }
            sb.append(sec);
            
            return sb.toString();
        }
    }
    public boolean isGameOver()
    {
    	return isGameOver;
    }
    public void updateGrid(int n)
    {
    	while (n>0)
        {
    		n--;
    		if (isGameOver || isPause)
    		{
                        //Thread.sleep(50);
            } 
    		else 
    		{
    			 paintTG();
    			 //paintNewPosition();
    			 nextMove();
           
            }
        }
    }
    public JetrisMainFrame(GameGenerator gameGenerator) {
        super(NAME);
        
        singleLiner = false;
       
        parent = gameGenerator; 
        SplashScreen sp = new SplashScreen();
        
        setIconImage(loadImage("jetris16x16.png"));
        
        keyHandler = new KeyAdapter(){

            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
                    moveLeft();
                } else if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
                    moveRight();
                } else if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                    moveDown();
                } else if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                    rotation();
                } else if(code == KeyEvent.VK_SPACE) {
                    moveDrop();
                } /*else if(code == KeyEvent.VK_R) { //Only for the applet needed
                    restart();
                } else if(code == KeyEvent.VK_P) {
                    pause();
                } */
            }
        };
        //addKeyListener(keyHandler); Manual control ommited
        
        //pH = new PublishHandler();
        
        font = new Font("Dialog", Font.PLAIN, 12);
        tg = new TetrisGrid();
        ff = new FigureFactory();
        nextBg = new Color(238,238,238);
        
        initMenu();

     // GAME PANEL 
        all.add(getStatPanel(), BorderLayout.WEST);
        all.add(getPlayPanel(), BorderLayout.CENTER);
        all.add(getMenuPanel(), BorderLayout.EAST);
        all.add(getCopyrightPanel(), BorderLayout.SOUTH);
        


/*
 * ------------------------------ TEXT PANEL -------------------------------
 */

        JPanel buttonEditPanel = new JPanel(new MigLayout("wrap 3"));
        buttonEditPanel.setBackground(Color.GRAY);
           
        JPanel textEditorPanel = new JPanel(new BorderLayout());
        textEditorPanel.setBackground(Color.GRAY);
        
    //    editArea.setLineWrap(false);
        editArea.setFont(new Font("Monospaced",Font.PLAIN,12));
        
        JScrollPane scroll = new JScrollPane(editArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        
        JToolBar tool = new JToolBar();
		
		JButton new_b = tool.add(New), 
				ope_b = tool.add(Open),
				sav_b = tool.add(Save);
		
		tool.addSeparator();
		
		JButton cut_b = tool.add(Cut), 
				cop_b = tool.add(Copy),
				pas_b = tool.add(Paste);
		
		
		new_b.setText(null); new_b.setIcon(new ImageIcon("icons/new.png"));
		ope_b.setText(null); ope_b.setIcon(new ImageIcon("icons/open.png"));
		sav_b.setText(null); sav_b.setIcon(new ImageIcon("icons/save.png"));
			
		cut_b.setText(null); cut_b.setIcon(new ImageIcon("icons/cut.png"));
		cop_b.setText(null); cop_b.setIcon(new ImageIcon("icons/copy.png"));
		pas_b.setText(null); pas_b.setIcon(new ImageIcon("icons/paste.png"));
		
		Save.setEnabled(false);
		SaveAs.setEnabled(false);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		editArea.addKeyListener(k1);

		textEditorPanel.add(tool,BorderLayout.NORTH);
		textEditorPanel.add(scroll,BorderLayout.CENTER);
		
		
		// ....................ALL BUTTONS FROM HERE ON........................
        
        JButton bugButton = new JButton("Check For Errors");
        bugButton.setAction(errorOutput);
        //Cancel button
        JButton compileButton = new JButton("Compile");
        //Cancel button
        JButton hideButton = new JButton("Toggle Output");
        
        buttonEditPanel.add(bugButton);   // Wrap to next row
        buttonEditPanel.add(compileButton)  ; // Wrap to next row
        buttonEditPanel.add(hideButton, "wrap");   // Wrap to next row
		
        hideButton.setAction(Hide);
        compileButton.setAction(Compile);
        
        
        bugButton.setIcon(new ImageIcon("icons/bug.png"));
        compileButton.setIcon(new ImageIcon("icons/compile.png"));
        hideButton.setIcon(new ImageIcon("icons/hide.png"));
        
		textEditorPanel.add(buttonEditPanel,BorderLayout.SOUTH);

/*
 * --------------------------------------------------------------------------
 */
		
		//Help Panel....
		JPanel HelpPanel = new JPanel(new MigLayout("wrap 1"));
		JPanel helpButtonPanel = new JPanel(new MigLayout("wrap 1"));

		helpButtonPanel.add(prevHelpButton, "cell 0 0");
		helpButtonPanel.add(nextHelpButton, "cell 2 0");   // Wrap to next row
		helpButtonPanel.add(nextLevelButton, "cell 1 1 1 1"); 
		
		
		prevHelpButton.setAction(prevHelp);
		nextHelpButton.setAction(nextHelp);
		nextLevelButton.setAction(nextLevel);
		
		prevHelpButton.setEnabled(false);
		
		
		prevHelpButton.setIcon(new ImageIcon("icons/left.png"));
		nextHelpButton.setIcon(new ImageIcon("icons/right.png"));
        
		helpArea.setEditable(false);   // This greys out the whole thing which does not look very nice. 
		helpArea.setForeground(Color.black);
		Dimension size = new Dimension(270, 300); 
		helpArea.setPreferredSize(size); //(15,100)
		helpArea.setMaximumSize(size);
		helpArea.setMinimumSize(size);
		
        helpArea.setFont(new Font("Monospaced",Font.ROMAN_BASELINE,15));
		helpArea.setContentType( "text/html" );  
        helpArea.setForeground(Color.black);
		
        JScrollPane helpscroll = new JScrollPane(helpArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        HelpPanel.add(helpscroll, "span");   // Span without "count" means span whole row.
		HelpPanel.add(helpButtonPanel)  ;    // Wrap to next row
		
		
        // Putting it all togethher. 
        JPanel panel = new JPanel(new MigLayout("fillx,insets 3"));

        panel.add(all);
        panel.add(textEditorPanel, "width :500:,grow,push");  // or grow
        //  panel.add(EditPanel, "width :500:,grow,push");  // or grow
        panel.add(HelpPanel, "width :200:");  // or grow
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().add(panel, BorderLayout.CENTER);
        pack();
        this.setResizable(true);
        
        fNext = ff.getFigure(-1);
        isNewFigureDroped = true;
        
        getFirstHint();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width / 2 - getWidth() / 2, screenSize.height / 2 - getHeight() / 2);
        setVisible(true);
        sp.setVisible(false);
        sp.dispose();
        
    }
    private KeyListener k1 = new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			changed = true;
			Save.setEnabled(true);
			SaveAs.setEnabled(true);
		}
	};
    
	private void saveFileAs() {
		if(dialog.showSaveDialog(null)==JFileChooser.APPROVE_OPTION)
			saveFile(dialog.getSelectedFile().getAbsolutePath());
	}
	
	private void saveOld() {
		if(changed) {
			if(JOptionPane.showConfirmDialog(this, "Would you like to save "+ currentFile +" ?","Save",JOptionPane.YES_NO_OPTION)== JOptionPane.YES_OPTION)
				saveFile(currentFile);
		}
	}
	
	private void readInFile(String fileName) {
		try {
			FileReader r = new FileReader(fileName);
			editArea.read(r,null);
			r.close();
			currentFile = fileName;
			setTitle(currentFile);
			changed = false;
		}
		catch(IOException e) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(this,"Editor can't find the file called "+fileName);
		}
	}
	
	private void saveFile(String fileName) {
		try {
			FileWriter w = new FileWriter(fileName);
			editArea.write(w);
			w.close();
			currentFile = fileName;
			setTitle(currentFile);
			changed = false;
			Save.setEnabled(false);
		}
		catch(IOException e) {
		}
	}
	
	
	private void newFile()
	{
		if(JOptionPane.showConfirmDialog(this, "Sure you want to create a new document?"   ,"Yes" ,JOptionPane.YES_NO_OPTION)== JOptionPane.YES_OPTION)
		{	
			saveFileAs();
			editArea.setText("");
			SaveAs.setEnabled(true);
			currentFile = "default";
			setTitle(currentFile);	
		}
	}
	
	private void compileCode() {
			if(currentFile == "default")
			{
				JOptionPane.showMessageDialog(this, "You must save the file before compiling");
				saveFileAs();
			}
			else 
			{
				saveOld();
			}
			//outputWindow.clearText();
			//outputWindow.Show();
			restart();
			parent.runFullWorld(currentFile);
	}
    
	private void getNextHelp() {

		LevelStep newHelp = parent.getNextHelp();
		
		if(newHelp != null)
		{
			helpArea.setText("");
			helpArea.setText(newHelp.getHelpText());
			
			
			String[] lines = newHelp.getInjectCode().split("@");
			for(String s : lines)
			{
				editArea.setText(editArea.getText()+s+"\n");
			}

			if(newHelp.isLast())
			{
				nextHelpButton.setEnabled(false);
				nextLevelButton.setEnabled(true);
			}
			if(!newHelp.isFirst())
			{
				prevHelpButton.setEnabled(true);
			}
		}
	}
	
	public void hideTetris()
	{
	
		if(outputWindow.isShown())
		{
			outputWindow.Hide();
		}
		else
		{
			outputWindow.Show();
		}
	}
	
	
	public void setOutput(String newOutput)
	{
		outputWindow.setText(newOutput);	
	}
	
	public void clearOutput()
	{
		outputWindow.clearText();
	}
	
	private void getPrevHelp() 
	{		
		LevelStep newHelp = parent.getPrevHelp();
	
		if(newHelp != null)
		{
			helpArea.setText("");
			helpArea.setText(newHelp.getHelpText());
		
			if(newHelp.isFirst())
			{
				prevHelpButton.setEnabled(false);
			}
			if(!newHelp.isLast())
			{
				nextHelpButton.setEnabled(true);
				nextLevelButton.setEnabled(false);
			}
		}
	}
	
	private void getFirstHint()
	{
		
		LevelStep first = parent.getCurrentHelp();
		if (first != null)
		{
			helpArea.setText(first.getHelpText());
			editArea.setText(editArea.getText()+first.getInjectCode());
			if(first.isLast())
			{
				nextHelpButton.setEnabled(false);
				nextLevelButton.setEnabled(true);
			}
			else
			{
				nextLevelButton.setEnabled(false);
			}
			
		}
		else
		{
			helpArea.setText("There is no help for this level. You are on your own!");
			nextLevelButton.setEnabled(true);
		}
		
	}
	
	private void errorCheck()
	{
		if(currentFile == "default")
		{
			JOptionPane.showMessageDialog(this, "You must save the file before compiling");
			saveFileAs();
		}
		else 
		{
			saveOld();
		}
		//outputWindow.clearText();
		//outputWindow.Show();
		//restart();
		world.setVisibility(false);
		parent.runSimulationWorld(currentFile);
	}
	
	
	private void getNextLevel()
	{
		if(parent.getNewLevel())
		{
			LevelStep first = parent.getCurrentHelp();
			if (first != null)
			{
				outputWindow.clearText();
				outputWindow.Hide();
				helpArea.setText(first.getHelpText());
				prevHelpButton.setEnabled(false);
				if(first.isLast())
				{
					nextHelpButton.setEnabled(false);
					nextLevelButton.setEnabled(true);
				}
				else
				{
					nextLevelButton.setEnabled(false);
					nextHelpButton.setEnabled(true);
				}
			}
			else
			{
				nextLevelButton.setEnabled(true);
				helpArea.setText("There is no help for this level. You are on your own!");
			}	
		}
		else
		{
			helpArea.setText("There are no more levels.");
			nextLevelButton.setEnabled(false);
			nextHelpButton.setEnabled(false);
			prevHelpButton.setEnabled(false);
		}
	}
    public Figure getFigure()
    {
    	return fNext;
    }
    public void setNextFigure(Figure a)
    {
    	fNext = a;
    	showNext(fNext);
    }
    public void setCurrentFigure(Figure a)
    {
    	f = a;
    }
    public Figure getCurrentFigure()
    {
    	return f;
    }
    public TetrisGrid getGrid()
    {
    	return tg;
    }
    
    private void initMenu() {
        
        MenuHandler mH = new MenuHandler();
        
        JMenuBar menu = new JMenuBar(); 
        setJMenuBar(menu);
        
        JMenu mJetris = new JMenu();
        menu.add(mJetris);
        mJetris.setText("Jetris");
        mJetris.setMnemonic('J');
        {
            jetrisRestart = new JMenuItem("Restart");
            mJetris.add(jetrisRestart);
            setKeyAcceleratorMenu(jetrisRestart, 'R',0);
            jetrisRestart.addActionListener(mH);
            jetrisRestart.setMnemonic('R');
            
            jetrisPause = new JMenuItem("Pause");
            mJetris.add(jetrisPause);
            setKeyAcceleratorMenu(jetrisPause, 'P',0);
            jetrisPause.addActionListener(mH);
            jetrisPause.setMnemonic('P');
            
            mJetris.addSeparator();
            
            jetrisHiScore = new JMenuItem("HiScore...");
            mJetris.add(jetrisHiScore);
            setKeyAcceleratorMenu(jetrisHiScore, 'H',0);
            jetrisHiScore.addActionListener(mH);
            jetrisHiScore.setMnemonic('H');
            
            mJetris.addSeparator();
            
            jetrisExit = new JMenuItem("Exit");
            mJetris.add(jetrisExit);
            setKeyAcceleratorMenu(jetrisExit, KeyEvent.VK_ESCAPE, 0);
            jetrisExit.addActionListener(mH);
            jetrisExit.setMnemonic('X');
        }
        
        JMenu mHelp = new JMenu();
        menu.add(mHelp);
        mHelp.setText("Help");
        mHelp.setMnemonic('H');
        {
            helpJetris = new JMenuItem("Jetris Help");
            mHelp.add(helpJetris);
            setKeyAcceleratorMenu(helpJetris, KeyEvent.VK_F1 ,0);
            helpJetris.addActionListener(mH);
            helpJetris.setMnemonic('J');
            
            helpAbout = new JMenuItem("About");
            mHelp.add(helpAbout);
            helpAbout.addActionListener(mH);
            helpAbout.setMnemonic('A');
        }
    }
    
    private void setKeyAcceleratorMenu(JMenuItem mi, int keyCode, int mask) {
        KeyStroke ks = KeyStroke.getKeyStroke(keyCode, mask);
        mi.setAccelerator(ks);
    }

    private JPanel getPlayPanel() {
        playPanel = new JPanel();
        playPanel.setLayout(new GridLayout(20,10));
        playPanel.setPreferredSize(new Dimension(10*CELL_H, 20*CELL_H));

        cells = new JPanel[20][10];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                cells[i][j] = new JPanel();
                cells[i][j].setBackground(Color.WHITE);
                cells[i][j].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                playPanel.add(cells[i][j]);
            }
        }
        return playPanel;
    }
    
    private JPanel getMenuPanel() {
        JPanel r = new JPanel();
        BoxLayout rL = new BoxLayout(r,BoxLayout.Y_AXIS);
        r.setLayout(rL);
        r.setBorder(new EtchedBorder());
        Dimension ra = new Dimension(5, 0);
        next = new JPanel[4][4];
        JPanel nextP = new JPanel();
        nextP.setLayout(new GridLayout(4,4));
        Dimension d = new Dimension(4*18, 4*18);
        nextP.setMinimumSize(d);
        nextP.setPreferredSize(d);
        nextP.setMaximumSize(d);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                next[i][j] = new JPanel();
                nextP.add(next[i][j]);
            }
        }
        
        JPanel jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.LINE_AXIS));
        jp.add(Box.createRigidArea(ra));
        jp.add(new JLabel("NEXT:"));
        jp.add(Box.createHorizontalGlue());
        r.add(jp);
        r.add(nextP);
        
        r.add(Box.createRigidArea(new Dimension(100, 10)));
        
        jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.LINE_AXIS));
        jp.add(Box.createRigidArea(ra));
        jp.add(new JLabel("HI-SCORE:"));
        jp.add(Box.createHorizontalGlue());
        r.add(jp);
        
        hiScoreLabel = new JLabel(""+tg.hiScore[0].score);
        hiScoreLabel.setForeground(Color.RED);
        
        
        jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.LINE_AXIS));
        jp.add(Box.createRigidArea(ra));
        jp.add(hiScoreLabel);
        jp.add(Box.createHorizontalGlue());
        r.add(jp);
        
        r.add(Box.createVerticalStrut(5));
        
        jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.LINE_AXIS));
        jp.add(Box.createRigidArea(ra));
        jp.add(new JLabel("SCORE:"));
        jp.add(Box.createHorizontalGlue());
        r.add(jp);
        
        score = new JLabel("0");
        score.setForeground(Color.BLUE);
        
        jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.LINE_AXIS));
        jp.add(Box.createRigidArea(ra));
        jp.add(score);
        jp.add(Box.createHorizontalGlue());
        r.add(jp);
        
        jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.LINE_AXIS));
        jp.add(Box.createRigidArea(ra));
        jp.add(new JLabel("LINES:"));
        jp.add(Box.createHorizontalGlue());
        r.add(jp);
        
        lines = new JLabel("0");
        lines.setForeground(Color.BLUE);
        
        jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.LINE_AXIS));
        jp.add(Box.createRigidArea(ra));
        jp.add(lines);
        jp.add(Box.createHorizontalGlue());
        r.add(jp);
        
        jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.LINE_AXIS));
        jp.add(Box.createRigidArea(ra));
        jp.add(new JLabel("LEVEL:"));
        jp.add(Box.createHorizontalGlue());
        r.add(jp);
        
        levelLabel = new JLabel("1");
        levelLabel.setForeground(Color.BLUE);
        
        jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.LINE_AXIS));
        jp.add(Box.createRigidArea(ra));
        jp.add(levelLabel);
        jp.add(Box.createHorizontalGlue());
        r.add(jp);
        
       // jp = new JPanel();
       // jp.setLayout(new BoxLayout(jp, BoxLayout.LINE_AXIS));
       // jp.add(Box.createRigidArea(ra));
       // jp.add(new JLabel("TIME:"));
       // jp.add(Box.createHorizontalGlue());
       // r.add(jp);
        
       // time = new JLabel("00:00:00");
       // time.setForeground(Color.BLUE);
        
        jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.LINE_AXIS));
        jp.add(Box.createRigidArea(ra));
      //  jp.add(time);
        jp.add(Box.createHorizontalGlue());
        r.add(jp);
        
        r.add(Box.createVerticalGlue());
        
    //    r.add(addHelpPanel("A or \u2190 - Left"));
    //    r.add(addHelpPanel("D or \u2192 - Right"));
    //    r.add(addHelpPanel("W or \u2191 - Rotate"));
    //    r.add(addHelpPanel("S or \u2193 - Down"));
    //    r.add(addHelpPanel("Space - Drop"));
        
        //BUTTONS
        r.add(Box.createRigidArea(new Dimension(0, 10)));
        
        jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.LINE_AXIS));
        jp.add(Box.createRigidArea(ra));
        JButton restartBut = new JButton("Restart");
        restartBut.setToolTipText("Press 'R'");
        restartBut.setFocusable(false);
        restartBut.addKeyListener(keyHandler);
        restartBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                //restart();
            }
        });
        d = new Dimension(90, 30);
        restartBut.setMinimumSize(d);
        restartBut.setPreferredSize(d);
        restartBut.setMaximumSize(d);
        jp.add(restartBut);
        jp.add(Box.createHorizontalGlue());
        r.add(jp);
        
        r.add(Box.createRigidArea(new Dimension(0, 5)));
        
        jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.LINE_AXIS));
        jp.add(Box.createRigidArea(ra));
        JButton pauseBut = new JButton("Pause");
        pauseBut.setToolTipText("Press 'P'");
        pauseBut.setFocusable(false);
        pauseBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                //pause();
            }
        });
        pauseBut.setMinimumSize(d);
        pauseBut.setPreferredSize(d);
        pauseBut.setMaximumSize(d);
        jp.add(pauseBut);
        jp.add(Box.createHorizontalGlue());
        r.add(jp);

        return r;
    }
    
    private JPanel addHelpPanel(String help) {
        JPanel jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.LINE_AXIS));
        jp.add(Box.createRigidArea(new Dimension(5,0)));
        JLabel jL = new JLabel(help);
        jL.setFont(font);
        jL.setForeground(Color.GRAY);
        jp.add(jL);
        jp.add(Box.createHorizontalGlue());
        return jp;
    }
    
    private JPanel getCopyrightPanel() {
    	/*
        JPanel r = new JPanel(new BorderLayout());
        BoxLayout rL = new BoxLayout(r,BoxLayout.X_AXIS);
        r.setLayout(rL);
        r.setBorder(new EtchedBorder());
        r.add(Box.createRigidArea(new Dimension(32,0)));
        
        JLabel jL = new JLabel("Copyright (c) 2006 Nikolay G. Georgiev ");
        jL.setFont(font);
        HTMLLink email = new HTMLLink("ngg@users.sourceforge.net", true);
        email.setFont(font);
        
        r.add(jL);
        r.add(email);
        
        return r;
        */
    	return new JPanel();
    }
    
    private JPanel getStatPanel() {
        int h = 12;
        JPanel r = new JPanel();
        BoxLayout rL = new BoxLayout(r,BoxLayout.Y_AXIS);
        r.setLayout(rL);
        r.setBorder(new EtchedBorder());
        
        JPanel[][] fig;
        JPanel figP, statFP;
        Dimension d = new Dimension(4*h, 4*h);
        Figure f;
        statsF = new JLabel[7];
        statsL = new JLabel[4];
        
        JPanel jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.LINE_AXIS));
        jp.add(Box.createRigidArea(new Dimension(5,0)));
        jp.add(new JLabel("STATISTICS: "));
        jp.add(Box.createHorizontalGlue());
        r.add(jp);
        
        r.add(Box.createRigidArea(new Dimension(0, 5)));
        
        for (int k = 0; k < 7; k++) {
            fig = new JPanel[4][4];
            figP = new JPanel();
            statFP = new JPanel();
            statFP.setLayout(new BoxLayout(statFP, BoxLayout.LINE_AXIS));
            figP.setLayout(new GridLayout(4,4));
            figP.setMinimumSize(d);
            figP.setPreferredSize(d);
            figP.setMaximumSize(d);
            
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    fig[i][j] = new JPanel();
                    fig[i][j].setBackground(nextBg);
                    figP.add(fig[i][j]);
                }
            }
 
            switch (k+1) {
            case Figure.I: f = new FigureI(); f.setOffset(2,0); break;
            case Figure.T: f = new FigureT(); f.setOffset(1,1); break;
            case Figure.O: f = new FigureO(); f.setOffset(1,1); break;
            case Figure.J: f = new FigureJ(); f.setOffset(1,1); break;
            case Figure.L: f = new FigureL(); f.setOffset(1,1); break;
            case Figure.S: f = new FigureS(); f.setOffset(1,1); break;
            default: f = new FigureZ(); f.setOffset(1,1); break;
            }
            
            for (int i = 0; i < 4; i++) {
                fig[f.arrY[i]+f.offsetY][f.arrX[i]+f.offsetX].setBackground(f.getGolor());
                fig[f.arrY[i]+f.offsetY][f.arrX[i]+f.offsetX].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            }
            statFP.add(figP);
            statFP.add(new JLabel("  X  "));
            
            statsF[k] = new JLabel("0");
            statsF[k].setForeground(Color.BLUE);
            statFP.add(statsF[k]);
            r.add(statFP);
        }

        r.add(Box.createRigidArea(new Dimension(100, 15)));
        
        for (int i = 0; i < statsL.length; i++) {
            statFP = new JPanel();
            statFP.setLayout(new BoxLayout(statFP, BoxLayout.LINE_AXIS));
            switch (i) {
            case 0: statFP.add(new JLabel ("  Single  X  ")); break;
            case 1: statFP.add(new JLabel   ("Double  X  ")); break;
            case 2: statFP.add(new JLabel ("  Triple  X  ")); break;
            default: statFP.add(new JLabel("  Tetris  X  ")); break;
            }
            statsL[i] = new JLabel("0");
            statsL[i].setForeground(Color.BLUE);
            statFP.add(statsL[i]);
            r.add(statFP);
            r.add(Box.createRigidArea(new Dimension(0, 5)));
        }
        return r;
    }
    
    static Image loadImage(String imageName) {
        try {
            Image im = ImageIO.read(new BufferedInputStream(
                    new ResClass().getClass().getResourceAsStream(imageName)));
            return im;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
    }
    //returns true if the piece falls to the ground
    public boolean nextMove() {
    	boolean res = false;
    	if(f.toArray()[0][0]==-1)return true; 
       
        if(singleLiner)
        	if(tg.addFigureSingleLine(f)) {
        		paintNewPosition();
                return true;
            } else {
                //clearOldPosition();
            }
        else
        {
        	f.setOffset(nextX, nextY);
	        if(tg.addFigure(f)) {
	        	return true;
	        } else {
	            clearOldPosition();
	        }
        }    
        paintNewPosition();
        singleLiner = false;
        res = true;
        return res;
    }
    
    private void clearOldPosition() {
        for (int j = 0; j < 4; j++) {
            cells[f.arrY[j]+f.offsetYLast][f.arrX[j]+f.offsetXLast].setBackground(Color.WHITE);
            //cells[f.arrY[j]+f.offsetYLast][f.arrX[j]+f.offsetXLast].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        }
    }
    
    private void paintNewPosition() {
        for (int j = 0; j < 4; j++) {
            cells[f.arrY[j]+f.offsetY][f.arrX[j]+f.offsetX].setBackground(f.getGolor());
            //cells[f.arrY[j]+f.offsetY][f.arrX[j]+f.offsetX].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        } 
    }
    
    public void paintTG() {
        int i = 0;
        Color c;
        for (int[] arr : tg.gLines) {
            for (int j = 0; j < arr.length; j++) {
                if(arr[j]!= 0) {
                    switch (arr[j]) {
                    case Figure.I: c = Figure.COL_I; break;
                    case Figure.T: c = Figure.COL_T; break;
                    case Figure.O: c = Figure.COL_O; break;
                    case Figure.J: c = Figure.COL_J; break;
                    case Figure.L: c = Figure.COL_L; break;
                    case Figure.S: c = Figure.COL_S; break;
                    default: c = Figure.COL_Z; break;
                    }
                    cells[i][j].setBackground(c);
                    //cells[i][j].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                } else {
                    cells[i][j].setBackground(Color.WHITE);
                    //cells[i][j].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                } 
            }
            i++;
        }
    }
    private void dropSingleLine(Figure f) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                next[i][j].setBackground(nextBg);
                //next[i][j].setBorder(BorderFactory.createEmptyBorder());
            }
        }
        
        for (int j = 0; j < f.arrX.length; j++) {
            next[f.arrY[j]][f.arrX[j]].setBackground(f.getGolor());
            //next[f.arrY[j]][f.arrX[j]].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        }
    }
    private void showNext(Figure f) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                next[i][j].setBackground(nextBg);
                next[i][j].setBorder(BorderFactory.createEmptyBorder());
            }
        }
        
        for (int j = 0; j < f.arrX.length; j++) {
            next[f.arrY[j]][f.arrX[j]].setBackground(f.getGolor());
            next[f.arrY[j]][f.arrX[j]].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        }
    }
    

    private void dropNext() {
        if(isGameOver)
        {  	
        	//JOptionPane.showMessageDialog(this, "GAME OVER!");
        	parent.gameOver();
        	return;
        }
        nextX = 4;
        nextY = 0;

        score.setText(""+tg.getScore());
        lines.setText(""+tg.getLines());
        levelLabel.setText(tg.getLevel()+" / 20");

        f = fNext;
        fNext = ff.getRandomFigure();
        
        
        isGameOver = tg.isGameOver(f);
        if (isGameOver)
        {
        	isGameOver = tg.isGameOverOver(f);
        	if(!isGameOver) singleLiner = true;
        }
        if(!isGameOver)showNext(fNext);

        isNewFigureDroped = true;
        updateStats();
    }
    
    public void moveLeft() {
        if(isGameOver || isPause) return;
        if(nextX-1 >= 0) {
            if (tg.isNextMoveValid(f,f.offsetX-1,f.offsetY)) {
                nextX--;
                nextMove();
            }
        }
    }
    
    public void moveRight() {
        if(isGameOver || isPause) return;
        if(f.getMaxRightOffset()+1 < 10) {
            if (tg.isNextMoveValid(f,f.offsetX+1,f.offsetY)) {
                nextX++;
                nextMove();
            }
        }
    }
    
    public boolean moveDown()
    {
        if(isGameOver || isPause) return false;
        nextY++;
        return nextMove();
    }
    public void addFigure()
    {
    		 dropNext();
    		 nextMove(); 
    }
    public void moveDrop() {
        if(isGameOver || isPause) return;
        f.offsetYLast = f.offsetY;
        f.offsetXLast = f.offsetX;        
        if(singleLiner)
        {
        	singleLiner = false;
        	if(!tg.isNextMoveValid(f,f.offsetX, f.offsetY))
        	{
        		isGameOver = true;
        		return;
        	}
        }
        clearOldPosition();
        while(tg.isNextMoveValid(f, f.offsetX, f.offsetY)) {
            f.setOffset(f.offsetX, f.offsetY+1);
        }

       
        tg.addFigure(f);
        //paintTG();
         
    }
    
    public boolean rotationTry()
    {
    	f.rotationRight();
        if(!tg.isNextMoveValid(f,f.offsetX,f.offsetY)) {
            f.rotationLeft();
            return false;
        }
        return true;
    }
    private  void rotation() {
        if(isGameOver || isPause) return;
        for (int j = 0; j < f.arrX.length; j++) {
            cells[f.arrY[j]+f.offsetY][f.arrX[j]+f.offsetX].setBackground(Color.WHITE);
            //cells[f.arrY[j]+f.offsetY][f.arrX[j]+f.offsetX].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        }
        f.rotationRight();
        if(!tg.isNextMoveValid(f,f.offsetX,f.offsetY)) {
            f.rotationLeft();
        }
        nextMove();
    }
    
    private  void pause() {
        isPause = !isPause;
    }

    private void restart() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                tg.gLines.get(i)[j] = 0;
                cells[i][j].setBackground(Color.WHITE);
                cells[i][j].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            }
        }
       
        ff.resetCounts();
        isGameOver = false;
        isPause = false;
        fNext = ff.getRandomFigure();
        //tt.resetTime();
        //time.setText("00:00:00");
        tg.resetStats();
        dropNext();
        nextMove();
        world.gameOver(false);
    }
    
    private void updateStats() {
        for (int i = 0; i < statsF.length; i++) {
            statsF[i].setText(""+ff.getCounts()[i]);
        }
        
        for (int i = 0; i < statsL.length; i++) {
            statsL[i].setText(""+tg.getDropLines()[i]);
        }
    }
    
    private void doHelp() {
        if(helpDialog == null) helpDialog = new HelpDialog(this);
        helpDialog.show();
    }
    
    private void doAbout() {
        if(about == null) setAboutPanel();
        JOptionPane.showMessageDialog(this,about,"ABOUT", 
                JOptionPane.PLAIN_MESSAGE, 
                new ImageIcon(loadImage("jetris.png")));
    }
    
    private void setAboutPanel() {
        about = new JPanel();
        about.setLayout(new BoxLayout(about, BoxLayout.Y_AXIS));
        JLabel jl = new JLabel("<HTML><B>"+NAME+"</B> Copyright (c) 2006 Nikolay G. Georgiev</HTML>");
        jl.setFont(font);
        about.add(jl);
        about.add(Box.createVerticalStrut(10));
        
        jl = new JLabel("WEB PAGE:");
        jl.setFont(font);
        about.add(jl);
        HTMLLink hl = new HTMLLink("http://jetris.sf.net", false);
        hl.setFont(font);
        about.add(hl);
        
        about.add(Box.createVerticalStrut(20));
        
        jl = new JLabel("<HTML>This program is released under the Mozilla Public License 1.1 .<BR> A copy of this is included with your copy of JETRIS<BR>and can also be found at:</HTML>");
        jl.setFont(font);
        about.add(jl);
        about.add(jl);
        hl = new HTMLLink("http://www.opensource.org/licenses/mozilla1.1.php", false);
        hl.setFont(font);
        about.add(hl);
    }
    
    private void showHiScore() {
        setHiScorePanel();
        
     //   JOptionPane.showMessageDialog(this,hiScorePanel,"HI SCORE", 
     //           JOptionPane.PLAIN_MESSAGE, 
     //           new ImageIcon(loadImage("jetris32x32.png")));
        
        hiScorePanel = null;
    }

    
    private void setHiScorePanel() {
        hiScorePanel = new JPanel(new BorderLayout());
        
        String[] colNames = {"Place", "Points", "Lines", "Name"};
        String[][] data = new String[tg.hiScore.length+1][colNames.length];
        data[0] = colNames;
        for (int i = 0; i < tg.hiScore.length; i++) {
            data[i+1] = new String[colNames.length];
            data[i+1][0] = (i+1)+".";
            data[i+1][1] = (""+tg.hiScore[i].score);
            data[i+1][2] = (""+tg.hiScore[i].lines);
            data[i+1][3] = (""+tg.hiScore[i].name);
        }
        
        JTable table = new JTable(data, colNames);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setBackground(new Color(230,255,255));
        table.setEnabled(false);
        
        hiScorePanel.add(table,BorderLayout.CENTER);
        JButton jb = new JButton("Publish HiScore Online");
        jb.addActionListener(pH);
        
        hiScorePanel.add(jb, BorderLayout.SOUTH);
    }
    
    
    
    private class PublishHandler implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            JButton jb = (JButton) ae.getSource();
            PublishThread pt = new PublishThread(jb);
            pt.start();
        }
    }
    
    private class PublishThread extends Thread {
        
        private JButton but;
        
        PublishThread(JButton source) {
            super();
            but = source;
        }
        
        public void run() {
            but.setEnabled(false);
            boolean b = false;
            try {
                for (int i = 0; i < tg.hiScore.length; i++) {
                    PublishHiScore.publish(tg.hiScore[i]);
                }
            } catch (Exception e) {
                e.printStackTrace();
                b = true;
                    JOptionPane.showMessageDialog(hiScorePanel,"Could not publish HiScore online!\nTry again later!","ERROR", 
                        JOptionPane.ERROR_MESSAGE);
            }
            if(!b) {
                JOptionPane.showMessageDialog(hiScorePanel,"Publishing HiScore was successfull :)","HI SCORE", 
                        JOptionPane.INFORMATION_MESSAGE);
            }
            but.setEnabled(true);
        }
        
    }


    private class MenuHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            try {
                JMenuItem tmp = (JMenuItem) e.getSource();
                if (tmp == jetrisRestart) {
                    //restart();
                } else if (tmp == jetrisPause) {
                    pause();
                } else if (tmp == jetrisHiScore) {
                    showHiScore();
                } else if (tmp == jetrisExit) {
                    System.exit(0);
                } else if (tmp == helpJetris) {
                    doHelp();
                }else if (tmp == helpAbout) {
                    doAbout();
                }
            } catch (Exception exc) {
                exc.printStackTrace(System.out);
            }
        }
    }
    @Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
    public void setFlag()
    {
    	isNewFigureDroped = true;
    }
    public void setWorld(World x)
    {
    	world = x;
    }
	public void errorOutput(String message)
	{
		OutputBox error = new OutputBox();
		error.setText(message);
		error.Show();		
	}
}
