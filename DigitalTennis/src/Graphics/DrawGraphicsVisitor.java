package Graphics;

import java.awt.Color;
import java.awt.Graphics;

import GameComponents.Ball;
import GameComponents.Blade;
import GameComponents.Obstacle;
/**
 * 
 * @author Safwan Salehjee
 * @version Practical X
 *
 */
public class DrawGraphicsVisitor implements IDrawVisitor
{
	private Graphics graphic;

	
	public void setGraphics(Graphics graphic)
	{
		this.graphic = graphic;
	}
	public Graphics getgraphic()
	{
		return graphic;
	}
	@Override
	public void visit(Ball ball) 
	{
		// TODO Auto-generated method stub
		 	graphic.setColor(Color.YELLOW);
	        graphic.fillOval(ball.getPoint_Xcoordinates(), ball.getPoint_Ycoordinates(), 20, 20);
	}

	@Override
	public void visit(Blade blade) 
	{
		// TODO Auto-generated method stub
		 	graphic.setColor(Color.GRAY);
	        graphic.fillRect(blade.getPoint_Xcoordinates(), blade.getPoint_Ycoordinates(), 150, 20);
	        graphic.setColor(Color.DARK_GRAY);
	        graphic.drawRect(blade.getPoint_Xcoordinates(), blade.getPoint_Ycoordinates(), 150, 20);
	}

	@Override
	public void visit(Obstacle obstacles) 
	{
		graphic.setColor(Color.red);
    	graphic.fillRect(obstacles.getPoint_Xcoordinates(), obstacles.getPoint_Ycoordinates(), 100, 45);
    	graphic.setColor(Color.BLACK);
    	graphic.drawRect(obstacles.getPoint_Xcoordinates(), obstacles.getPoint_Ycoordinates(), 100, 45);

	}
	
}
