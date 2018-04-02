package Network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;

import Graphics.GameFrame;

public class Client {
	
	private Socket client;
	private int PORT;
	private DataOutputStream Sender;
	private BufferedReader Reciever;
	public static int OppXCor;
	private String S;
	public Client(int Port)
	{
		this.PORT = Port;
		GameFrame b = new GameFrame();
		b.initialiseFrame();
		b.setLocationRelativeTo(null);
        b.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        b.setVisible(true);
		try {
			
			client = new Socket("LocalHost", PORT);
			Sender = new DataOutputStream(client.getOutputStream());
			Reciever = new BufferedReader(new InputStreamReader(client.getInputStream()));
			while(true)
		{Sender.writeBytes("Hey" + '\n');
			S = Reciever.readLine();
			System.out.println("Client Received: " + S);}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			new Client(1235);
	}
	

}
