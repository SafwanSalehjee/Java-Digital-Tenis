/**
 * 
 * @author Safwan Salehjee
 *@version Practical X- Digital Tennis
 */
package Graphics;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import GameComponents.Ball;
import GameComponents.Blade;
import GameComponents.Obstacle;


public class CourtPanel extends JPanel
{
	  //declares array of obstacle, ball, & Blade(racquets) to be drawn
    private Obstacle[] obstacle;
    private Ball ball;
    private Blade blade;
    private Blade ComBlade;
    private DrawGraphicsVisitor visitor = new DrawGraphicsVisitor();

    //constructor of panel takes in copies of the Game Components
    public CourtPanel(Obstacle[] obstacle, Ball ball, Blade blade, Blade ComBlade){

        this.obstacle = obstacle;
        this.ball = ball;
        this.blade = blade;
        this.ComBlade = ComBlade;
        
        //sets border of panel and background color
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        setBackground(Color.GREEN);

        //sets focusable of panel to allow key press
        this.setFocusable(true);

        //paints initial position upon instantiation
        repaint();
    }

    public void paintComponent(Graphics g){

        super.paintComponent(g); //Make Components in its Original Form
        visitor.setGraphics(g);
 
      //paints  bricks only if they are not hit
        for(int i = 0; i < obstacle.length; i++){
            if(!obstacle[i].isHit())
            {	
            	obstacle[i].accept(visitor);
            	
            }
        }

        //paints Blade of the User
       blade.accept(visitor);
       
       //Paint Blade of the Computer
       ComBlade.accept(visitor);
        //paints ball
       ball.accept(visitor);
    }

    //method that calls the repaint method
    public void updateBoard()
    {
        repaint();
    }

}
