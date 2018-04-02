package Network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;

import Graphics.GameFrame;

public class Server {

	public ServerSocket server;
	public DataOutputStream Sender;
	public BufferedReader Reciever;
	public int PORT;
	public static int oppXCor;
	private String S;
	public static Boolean ServerApp = false;
	public Server(int Port)
	{
		
		GameFrame b = new GameFrame();
		b.initialiseFrame();
		b.setLocationRelativeTo(null);
        b.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        b.setVisible(true);
		PORT = Port;
		try {
			server = new ServerSocket(PORT);
			Socket Connection = server.accept();
			Sender =  new DataOutputStream(Connection.getOutputStream());
			Reciever = new BufferedReader(new InputStreamReader(Connection.getInputStream()));
			while(true)
			{
			 S= Reciever.readLine();
			System.out.append("Server Received: " + S);
			oppXCor = Integer.parseInt(S);
			
			//Sender.writeBytes(ball+'\n');
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Server(1235);
		Server.ServerApp = true;
	}

}
