
//Page 1 of 6
//UDPFileClient.java 2014/08/26, 13:25
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.net.*;
/*
* This class is a UDP file sharing client that issues commands with a GUI
* @author Dustin van der Haar
*/
public class UDPFileClient extends JFrame implements ActionListener
{
private DatagramSocket dSocket;
private int serverPort = 1235;
private InetAddress serverAddr;
private static UDPFileClient frame;
private Vector<String> fileListData = new Vector<String>();
private JList<String> fileList;
private JPanel leecherPanel;
private JPanel seederPanel;
private JButton btnConnect;
private JButton btnRetrieveList;
private JButton btnDownload;
private JButton btnAdd;
private JButton btnRemove;
private JButton btnHost;
JLabel lblConnection = new JLabel("Host:");
JTextField txtConnection = new JTextField(10);
JLabel lblPort = new JLabel("Port:");
JTextField txtPort = new JTextField(4);
private DroneThread DroneThread;
private String message = "";
public static void main(String[] args)
{
frame = new UDPFileClient();
frame.setTitle("A UDP Peer-to-Peer Sharing Client");
frame.setSize(700,500);
frame.setVisible(true);
frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
}
public String sendTextMessage(String request){
String response = "";
try
{
byte[] buffer = request.getBytes();
System.out.println("Sending request: "+request+" to "+serverAddr+":"+
serverPort);
DatagramPacket packet = new DatagramPacket(buffer,
buffer.length,
serverAddr,
serverPort);
dSocket.send(packet);
//Page 2 of 6
//UDPFileClient.java 2014/08/26, 13:25
buffer = new byte[1024];
packet = new DatagramPacket(buffer,
buffer.length);
dSocket.receive(packet);
response = new String(buffer).trim();
}
catch (NoSuchElementException nseEx)
{
System.out.println(nseEx);
}
catch(IOException IOex)
{
System.out.println(IOex);
//Error occured during sending or receiving
}
return response;
}
public UDPFileClient()
{
leecherPanel = new JPanel();
btnConnect = new JButton("Connect");
btnConnect.addActionListener(this);
btnRetrieveList = new JButton("Retrieve List");
btnRetrieveList.addActionListener(this);
btnDownload = new JButton("Download");
btnDownload.addActionListener(this);
leecherPanel.add(lblConnection);
leecherPanel.add(txtConnection);
leecherPanel.add(lblPort);
leecherPanel.add(txtPort);
leecherPanel.add(btnConnect);
leecherPanel.add(btnRetrieveList);
leecherPanel.add(btnDownload);
add(leecherPanel,BorderLayout.NORTH);
fileList = new JList<String>(fileListData);
add(new JScrollPane(fileList),BorderLayout.CENTER);
seederPanel = new JPanel();
seederPanel.setLayout(new BorderLayout());
btnAdd = new JButton("Add File");
btnAdd.addActionListener(this);
btnRemove = new JButton("Remove File");
btnRemove.addActionListener(this);
btnHost = new JButton("Host Files");
btnHost.addActionListener(this);
//Page 3 of 6
//UDPFileClient.java 2014/08/26, 13:25
seederPanel.add(btnAdd, BorderLayout.WEST);
seederPanel.add(btnHost,BorderLayout.CENTER);
seederPanel.add(btnRemove, BorderLayout.EAST);
add(seederPanel, BorderLayout.SOUTH);
DroneThread = new DroneThread();
DroneThread.start();
}
public void actionPerformed(ActionEvent event)
{
/*
* Leecher Commands
*/
if (event.getSource() == btnConnect)
{
try {
serverAddr = InetAddress.getByName(txtConnection.getText());
serverPort = Integer.parseInt(txtPort.getText());
dSocket = new DatagramSocket();
System.out.println("Started on port "+dSocket.getLocalPort());
System.out.println("Connected");
}
catch (SocketException e) {
e.printStackTrace();
}
catch (UnknownHostException e) {
e.printStackTrace();
}
String response = sendTextMessage("CONNECT");
System.out.println(response);
}
else if(event.getSource() ==btnRetrieveList){
String list = sendTextMessage("LIST");
/*
* Populate JList with retrieved list
*/
Vector<String> fList = new Vector<String>();
for(String item:list.split("|")){
fList.add(item);
}
fileListData = fList;
fileList.setListData(fileListData);
}
else if(event.getSource() == btnDownload){
String request = "FILE 1";
if(fileList.getSelectedIndex()!=-1){
request = "FILE "+fileList.getSelectedIndex();
}
try
{
byte[] buffer = request.getBytes();
System.out.println("Requesting File: "+request);
//Page 4 of 6
//UDPFileClient.java 2014/08/26, 13:25
DatagramPacket packet = new DatagramPacket(buffer,
buffer.length,
serverAddr,
serverPort);
dSocket.send(packet);
/*
* File name and size can be retrived before this
*/
buffer = new byte[1024];
packet = new DatagramPacket(buffer,
buffer.length);
dSocket.receive(packet);
File f = new File("File "+fileList.getSelectedIndex());
DataOutputStream dos = new DataOutputStream(new
FileOutputStream(f));
dos.write(buffer);
dos.close();
}
catch (NoSuchElementException nseEx)
{
System.out.println(nseEx);
}
catch(IOException IOex)
{
System.out.println(IOex);
//Error occured during sending or receiving
}
}
/*
* Seeder Commands
*/
else if(event.getSource() == btnAdd){
JFileChooser jfc = new JFileChooser();
if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
String path = jfc.getSelectedFile().getAbsolutePath();
fileListData.add(path);
fileList.setListData(fileListData);
}
}
else if(event.getSource() == btnRemove){
if(fileList.getSelectedIndex()!=-1){
fileListData.remove(fileList.getSelectedIndex());
fileList.setListData(fileListData);
}
}
else if(event.getSource() == btnHost){
System.out.println("Ready to send files");
try
{
/*
* Get connection ready
*/
serverAddr = InetAddress.getLocalHost();
dSocket = new DatagramSocket(serverPort);
//Page 5 of 6
//UDPFileClient.java 2014/08/26, 13:25
System.out.println("Started on port "+dSocket.getLocalPort
());
while(true)
{
byte[] buffer = new byte[1024];
DatagramPacket packet = new DatagramPacket(buffer,
buffer.length);
dSocket.receive(packet);
String request = new String(buffer).trim();
if (request.toLowerCase().startsWith("connect")){
String response = "HELLO";
byte[] resBuffer = response.getBytes();
DatagramPacket resPacket = new DatagramPacket
(resBuffer,
resBuffer.length,
packet.getAddress(),
packet.getPort());
dSocket.send(resPacket);
}
else if(request.toLowerCase().startsWith("list")){
String list = "";
for(String item:fileListData){
list+=item+"|";
}
byte[] resBuffer = list.getBytes();
DatagramPacket resPacket = new DatagramPacket
(resBuffer,
resBuffer.length,
packet.getAddress(),
packet.getPort());
dSocket.send(resPacket);
}
else if(request.toLowerCase().startsWith("file")){
System.out.println("Request received:"+request);
int fileNo = Integer.parseInt(request.split(" ")[1
]);
File f = new File(fileListData.get(fileNo));
buffer = new byte[(int)f.length()];
DataInputStream dis = new DataInputStream(new
FileInputStream(f));
dis.readFully(buffer);
dis.close();
System.out.println("Sending file: "+fileNo);
DatagramPacket filePacket = new DatagramPacket
(buffer,
buffer.length,
packet.
getAddress(),
packet.
getPort());
dSocket.send(filePacket);
}
}
}
catch(IOException ioe){
//Page 6 of 6
//UDPFileClient.java 2014/08/26, 13:25
System.err.println(ioe);
}
}
}
class DroneThread extends Thread
{
public void run()
{
String handle = JOptionPane.showInputDialog(frame,"Enter whichmode you want to start in Seeder (S) or Leecher (L): ","EnterMode",JOptionPane.PLAIN_MESSAGE);
try
{
if(handle.toLowerCase().equals("s")){
leecherPanel.setVisible(false);
}
else{
seederPanel.setVisible(false);
}
}
catch(NullPointerException npe){
seederPanel.setVisible(false);
}
}
}
}
