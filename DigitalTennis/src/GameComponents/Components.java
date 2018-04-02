package GameComponents;

import java.awt.Point;

import Graphics.IDrawable;
/**
 * 
 * @author Safwan Salehjee
 * @version Practical X- Digital Tennis
 *
 */
public abstract class Components implements IDrawable
{
	Point point;
	
	protected Components(int x, int y)
	{
		point = new Point(x,y);
	}
	
	//Gets and Sets
		public Point getPoint()
		{
			return point;
		}
		public void setPoint(Point point)
		{
			this.point = point;
		}
		public void setPointX(int x)
		{
			point.x = x;
		}
		public void setPointY(int y)
		{
			point.y = y;
		}
		public int getPoint_Xcoordinates()
		{
			return point.x;
		}
		public int getPoint_Ycoordinates()
		{
			return point.y;
		}

}
