/**
 * 
 * @author Safwan Salehjee
 *@version Practical X- Digital Tennis
 */
package GameComponents;

import Graphics.IDrawVisitor;

public class Obstacle extends Components
{
	//boolean to determine if the Obstacle has been hit 
    boolean Hit;

    /**
     * 
     * @param x
     * @param y
     */
    public Obstacle(int x, int y)
    {
    	//Constructor Chaining
    	super(x, y);
        Hit = false;
    }

    //if hit returns true otherwise returns false
    public boolean isHit() {
        return Hit;
    }

    //set if the brick is hit or not
    public void setHit(boolean hit) {
        Hit = hit;
    }
    
    @Override
	public void accept(IDrawVisitor visitor) 
    {
		visitor.visit(this);
	}
  
 
}
