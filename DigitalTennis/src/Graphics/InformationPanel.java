package Graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 * 
 * @author Safwan Salehjee
 * @version Practical X
 *
 */
public final class InformationPanel extends JPanel{

    //declares text fields to display information
    private static JTextField timeTF, scoreTF, livesTF;

    //declare labels for text fields
    private static JLabel timeLabel, scoreLabel, livesLabel;

    //Default constructor
    public InformationPanel(){

        //creates text field for displaying time,Score and lives
    	timeTF = new JTextField(8);
    	setTF(timeTF);
    	scoreTF = new JTextField(8);
    	setTF(scoreTF);
    	livesTF = new JTextField(8);
    	setTF(livesTF);
        
        
        timeTF.setText("00:00");
        scoreTF.setText("0");
        livesTF.setText("3");
        
        //save All the panels in a grid form in this Information Panel
        this.setLayout(new GridLayout(3,1));
        
        
        setLb(timeLabel, timeTF, "Time:     ");
		setLb(scoreLabel, scoreTF, "Score:    ");
        
        setLb(livesLabel, livesTF, "Lives:    ");
        

    }
    
    //Helper function
    private void setTF(JTextField TF)
    {
        TF.setEditable(false);
        TF.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        TF.setBackground(Color.YELLOW);
        TF.setHorizontalAlignment(JTextField.CENTER);
        
        
    }
    private void setLb(JLabel Lb, JTextField TF, String Caption)
    {
    	JPanel TPanel = new JPanel();
        Lb = new JLabel();
        Lb.setForeground(Color.WHITE);
        Lb.setText(Caption);
        TPanel.add(Lb);
        TPanel.add(TF);
        TPanel.setBackground(Color.BLUE);
        this.add(TPanel);
    }
    //gets the number of lives left from text field
    public String getLives() {
        return livesTF.getText();
    }

    //sets the lives text field
    public void setLives(String lives) {
        this.livesTF.setText(lives);
    }

    //gets current score from text field
    public String getScore() {
        return scoreTF.getText();
    }

    //sets the score text field
    public void setScore(String score) {
        this.scoreTF.setText(score);
    }

    //gets the time from text field
    public JTextField getTime() {
        return timeTF;
    }

    //sets the time text field
    public void setTime(String time) {
        this.timeTF.setText(time);
    }
}

