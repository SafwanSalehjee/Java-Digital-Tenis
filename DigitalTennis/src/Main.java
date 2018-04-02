import javax.swing.JFrame;

import Graphics.GameFrame;


public class Main {

	public static void main(String[] args) 
	{
	
		// TODO Auto-generated method stub
		GameFrame b = new GameFrame();
		b.initialiseFrame();
		b.setLocationRelativeTo(null);
        b.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        b.setVisible(true);
	}

}
