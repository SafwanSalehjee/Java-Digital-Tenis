package Graphics;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import GameComponents.Ball;
import GameComponents.Blade;
import GameComponents.Obstacle;

/**
 * 
 * @author Safwan Salehjee
 * @version Practical X
 *
 */
public class GameFrame extends JFrame
{
	private CourtPanel court;
	private Controller control;
	private InformationPanel Information;
	
	 	private Obstacle[] obstacle;
	    private Ball ball;
	    private Blade Userblade;
	    private Blade ComBlade;
	    
	    private boolean User = false;
	    private boolean Com = true;
	
	public GameFrame()
	{
		this("Digital Tennis");
	}
	
	public GameFrame(String title)
	{
		super(title);
		
		JMenuBar menu = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenuItem open = new JMenuItem("Open");
		JMenuItem save = new JMenuItem("Save");
		JMenuItem exit = new JMenuItem("Exit");
		final JFileChooser jfc = new JFileChooser(".");
		
		open.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int returnVal = jfc.showOpenDialog(GameFrame.this);
				
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					File file = jfc.getSelectedFile();
					
					GameFrame.this.revalidate();
				}
				else
				{
					JOptionPane.showMessageDialog(GameFrame.this, "No File Chosen");
				}
				
			}
		});
		
		save.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int returnVal = jfc.showSaveDialog(GameFrame.this);
				
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					File file = jfc.getSelectedFile();
					PrintWriter fout = null;
					try
					{
						fout = new PrintWriter(file);
						
					}
					catch (IOException ex)
					{
						ex.printStackTrace();
					}
					finally
					{
						if (fout != null) fout.close();
					}
					
				}
				else
				{
					JOptionPane.showMessageDialog(GameFrame.this, "No File Chosen");
				}
				
			}
		});
		
		exit.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		
		file.add(open);
		file.add(save);
		file.add(new JSeparator());
		file.add(exit);
		menu.add(file);
		setJMenuBar(menu);
		
	}
	//initiates the frame
    public void initialiseFrame(){
    	
    	//Basic Frame
    	setSize(new Dimension(900,700));
        
  
        //creates bricks and puts them in an array for the game
        obstacle = new Obstacle[8];
        Random ir = new Random();
        
        for(int i = 0; i < 16; i++){
        	Obstacle temp;
            if(i < 8){
            	int irand = ir.nextInt(6)+1;
                temp = new Obstacle(i*50, irand*50);
                obstacle[i] = temp;
            }
        }

        //creates an instance of the ball for the game
        ball = ball.getBall(400, 598);

        //creates Blade for game
        Userblade = new Blade(335, 620,User);
        ComBlade = new Blade(ball.getPoint_Xcoordinates(),0, Com);
        //instantiates panels for program
        Information = new InformationPanel();
        court = new CourtPanel(obstacle, ball, Userblade,ComBlade);
        control = new Controller(Information, court, obstacle, ball, Userblade,ComBlade);

        

       

        //sets up panel for the Information panel and control panels
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.add(Information, BorderLayout.NORTH);
        infoPanel.add(control, BorderLayout.SOUTH);
        infoPanel.setBackground(Color.BLUE);
        
        //sets up main panel of the Frame
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(infoPanel, BorderLayout.EAST);
        mainPanel.add(court, BorderLayout.CENTER);
        add(mainPanel);
        
    }

}
