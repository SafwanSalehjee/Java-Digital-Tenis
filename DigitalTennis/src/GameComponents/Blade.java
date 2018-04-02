package GameComponents;

import Graphics.IDrawVisitor;

/**
 * 
 * @author Safwan Salehjee
 *@version Practical X- Digital Tennis
 */
public class Blade extends Components
{
	private boolean Computer;
	

	public Blade(int x,int y, boolean Com)
	{
		super(x, y);
		Computer = Com;
	}
	
	//Setters and Getters
	public boolean isComputer() {
		return Computer;
	}

	public void setComputer(boolean computer) {
		Computer = computer;
	}
	//Implementing the visitor design Pattern
		public void accept(IDrawVisitor visitor)
		{
			visitor.visit(this);
		}

}
