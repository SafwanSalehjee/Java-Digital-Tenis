package Graphics;

import GameComponents.Ball;
import GameComponents.Blade;
import GameComponents.Obstacle;
/**
 * 
 * @author Safwan Salehjee
 *@version Practical X- Digital Tennis
 */

public interface IDrawVisitor 
{
	public void visit(Ball ball);
	public void visit(Blade blade);
	public void visit(Obstacle obstacle);

}
