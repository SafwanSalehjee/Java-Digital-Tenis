package GameComponents;

import Graphics.IDrawVisitor;

/**
 * 
 * @author Safwan Salehjee
 *@version Practical X- Digital Tennis
 */
public final class Ball extends Components 
{
	private int SpeedX;
	private int SpeedY;
	
	private static Ball INSTANCE=  null;
	/**
	 * 
	 * @param x
	 * @param y
	 */
	private Ball(int x, int y)
	{
		super(x,y);
	}
	
	public static Ball getBall(int X, int Y)
	{
		if(INSTANCE == null)
		{
			INSTANCE = new Ball(X,Y);
		}
		return INSTANCE;
		
	}
	
	//Sets and gets
	public int getspeedX()
	{
		return SpeedX;
	}
	public void setSpeedX(int Sx)
	{
		SpeedX = Sx;
	}
	public int getSpeedY()
	{
		return SpeedY;
	}
	public void setSpeedY(int Sy)
	{
		SpeedY = Sy;
	}
	
	//Implementing the visitor design Pattern
	public void accept(IDrawVisitor visitor)
	{
		visitor.visit(this);
	}

}
