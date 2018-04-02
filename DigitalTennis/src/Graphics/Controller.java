package Graphics;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.*;
import java.math.MathContext;
import java.text.*;

import javax.swing.*;

import GameComponents.Ball;
import GameComponents.Blade;
import GameComponents.Obstacle;
import Network.Server;
import Network.Client;

/**
 * 
 * @author Safwan Salehjee
 *@version Practical X- Digital Tennis
 */
public class Controller extends JPanel {

    //declare buttons & button handler
    private JButton restart, pause, help;
    ButtonHandler ButtonClick;

    //declare key listener for the directions in which the user key moves.
    DirectionListener direction;

    //declare panels
    private InformationPanel Information;
    private CourtPanel Court;

    //declare bricks array, ball, paddle
    private Obstacle[] obstatcls;
    public Ball ball;
    public Blade Userblade;
    public Blade ComBlade;

    //declare number formatter to display time
    NumberFormat formatter;

    //declare timer, timer handler, time delay
     Timer mainTimer;
     TimerHandler timer;
     final int TIMER_DELAY = 10;
     
     //declare time, score, lives
     int time, timeDisplay, score;
     int lives = 3;


     
     /**
      * constructor takes in copies of the TextfieldPanel and ImagePanel as well as
     copies of the brick array, ball, & paddle
      * @param text
      * @param image
      * @param bricks
      * @param ball
      * @param paddle
      */
     public Controller(InformationPanel info, CourtPanel  court, Obstacle[] obs, Ball ball,Blade blade, Blade ComBlade ){

        //instantiating panels and objects
        obstatcls = obs;
        this.ball = ball;
        Userblade = blade;
        Information = info;
        Court = court;
        this.ComBlade = ComBlade;
        
        //instantiate action listeners
        ButtonClick = new ButtonHandler();
        direction = new DirectionListener();
        timer = new TimerHandler();
        
        //instantiate formatter for time
        formatter = new DecimalFormat("00");
       
        //create timer
        mainTimer = new Timer(TIMER_DELAY, timer);
        time = 0;
        timeDisplay = 0;
        
        //add key listener
        Court.addKeyListener (direction);
        Court.requestFocusInWindow();
        
        //create buttons and add button listeners
        restart = new JButton("RESTART");
        restart.addActionListener(ButtonClick);
        pause = new JButton("PAUSE");
        pause.addActionListener(ButtonClick);
        help = new JButton("HELP");
        help.addActionListener(ButtonClick);
        
        //setting up panel with grid layout
        this.setLayout(new GridLayout(3,1));
        this.add(help);
        this.add(restart);
        this.add(pause);

    }

    //button listener for restart and pause buttons
    class ButtonHandler implements ActionListener{
    	//Fix the try catch
        public void actionPerformed(ActionEvent e){

            if(e.getActionCommand().equals("RESTART")){
               
                //resets every brick to being not hit
                for(int i = 0; i < obstatcls.length; i++){
                	obstatcls[i].setHit(false);
                }
                
                //resets every textfield and variable back to 0
                lives = 3;
                score = 0;
                time = 0;
                timeDisplay = 0;
                Information.setTime("" + formatter.format(timeDisplay/60) + ":" +
                formatter.format(timeDisplay%60));
                Information.setLives("" + lives);
                Information.setScore("" + score);
                
                //resets position of ball and paddle to initial position
                ball.setPointX(400);
                ball.setPointY(698);
                ball.setSpeedX(0);
                ball.setSpeedY(0);
                Userblade.setPointX(335);
                Userblade.setPointY(720);
                ComBlade.setPointX(ball.getPoint_Xcoordinates()-60);
                ComBlade.setPointY(0);
                
                //stops timer resets up Court Panel
                mainTimer.stop();
                Court.requestFocusInWindow();
                Court.updateBoard(); // repaints the image
            }
            else if(e.getActionCommand().equals("PAUSE")){
                
                //stops timer
                if(mainTimer.isRunning()){
                  try {
					mainTimer.wait();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                  Court.requestFocusInWindow();}
                else{ //if pause already
                  mainTimer.start();
                  Court.requestFocusInWindow();
                }
            }
            else if(e.getActionCommand().equals("HELP"))
            {
                            JOptionPane.showMessageDialog (null,  "Press space bar to start the game, Try to beat the opposition by making the ball hit on the other boundry. Beware of the Obstacle", "INSTRUCTIONS", JOptionPane.PLAIN_MESSAGE );
            }
        }

    }

    class DirectionListener implements KeyListener{

        public void keyPressed(KeyEvent event){
           switch(event.getKeyCode()){
                case KeyEvent.VK_LEFT:
                   //Check if Move is Possible then move the User Blade
                   if(Userblade.getPoint_Xcoordinates() > 0 && mainTimer.isRunning()) {
                	   Userblade.setPointX(Userblade.getPoint_Xcoordinates() - 50);
                   }
                   break;
                case KeyEvent.VK_RIGHT:
                	//Check if Move is Possible then move the User Blade 
                   if(Userblade.getPoint_Xcoordinates() + 150 < 800 && mainTimer.isRunning()) {
                	   Userblade.setPointX(Userblade.getPoint_Xcoordinates() + 50);
                   }
                   break;
                case KeyEvent.VK_SPACE:
                   //starts timer and releases the ball
                   mainTimer.start();
                   if(ball.getPoint_Xcoordinates() == 400 && ball.getPoint_Ycoordinates() == 598){
                   ball.setSpeedY(-3);
                   ball.setSpeedX(-3);}
                   break;
               
            }
        }

        public void keyTyped(KeyEvent event) {}
        public void keyReleased(KeyEvent event) {}
    }

    class TimerHandler implements ActionListener{

        public void actionPerformed(ActionEvent e) {

            //time is increased once timer begins
            time = time +1;
            //used for displaying time
            if(time % 45 == 0){
                timeDisplay++;
            }
            score =  timeDisplay;
            int psec = 10;
            int sec = (timeDisplay%60)%10;
            if ( sec == 2 && sec> psec )
            {
            	psec = sec;
            	ball.setSpeedX(ball.getspeedX()*2);
                ball.setSpeedY(ball.getSpeedY()*2);
            }
            //sets text fields
            Information.setTime("" + formatter.format(timeDisplay/60) + ":" +
                 formatter.format(timeDisplay%60));
            Information.setScore("" + score);
            Information.setLives("" + lives);
            
            //checks if ball has hit anything and sets velocities; repaint
            collision_handling();
            ball.setPointX(ball.getPoint_Xcoordinates() + ball.getspeedX());
            ball.setPointY(ball.getPoint_Ycoordinates() + ball.getSpeedY());
            if(Server.ServerApp == true)
            {
            ComBlade.setPointX(Server.oppXCor);
            }else
            	ComBlade.setPointX(Client.OppXCor);
            if (timeDisplay% 3 != 1)
            {ComBlade.setPointX(ball.getPoint_Xcoordinates()-60);}
            else ComBlade.setPointX(ball.getPoint_Xcoordinates()-100);
            ComBlade.setPointY(0);
            Court.updateBoard();
        }

    }

    private void collision_handling()
    {
        //Ball cannot cross the boundries
        if(ball.getPoint_Xcoordinates() + 20 >= 800 || ball.getPoint_Xcoordinates() <= 0)
        {
            ball.setSpeedX(-1 * ball.getspeedX());
        }
        
        //checks if ball touches User Blade; if so allows ball to go in reverse y velocity
        if(ball.getPoint_Ycoordinates() + 20 >= Userblade.getPoint_Ycoordinates() && (ball.getPoint_Xcoordinates() >= Userblade.getPoint_Xcoordinates() && ball.getPoint_Xcoordinates() <= (Userblade.getPoint_Xcoordinates() + 150))&& (!Userblade.isComputer()))
        {
            if(ball.getPoint_Ycoordinates() + 20 <= Userblade.getPoint_Ycoordinates() + 9){
            ball.setSpeedY(-1 * ball.getSpeedY());
            }
        }
      //checks if ball touches Computer Blade; then Change Direction
        if(ball.getPoint_Ycoordinates() <= ComBlade.getPoint_Ycoordinates()+20 && (ball.getPoint_Xcoordinates() >= ComBlade.getPoint_Xcoordinates() && ball.getPoint_Xcoordinates() <= (ComBlade.getPoint_Xcoordinates() + 150))&& (ComBlade.isComputer()))
        {
            if(ball.getPoint_Ycoordinates() +9 >= ComBlade.getPoint_Ycoordinates() + 20){
            ball.setSpeedY(-1 * ball.getSpeedY());
            }
        }

        //if the ball drops below the User Blade loses a life and resets to initial position
        if(ball.getPoint_Ycoordinates() + 20 > Userblade.getPoint_Ycoordinates() + 20)
        {
            lives--;
            Information.setLives("" + lives);
            mainTimer.stop();
            ball.setPointX(400);
            ball.setPointY(698);
            ball.setSpeedX(0);
            ball.setSpeedY(0);
            Userblade.setPointX(335);
            Userblade.setPointY(720);
            ComBlade.setPointX(ball.getPoint_Xcoordinates());
            ComBlade.setPointY(0);
        }
      //if the ball beats the Computer Blade then user wins
        if(ball.getPoint_Ycoordinates() < ComBlade.getPoint_Ycoordinates()+20)
        {
            mainTimer.stop();
            JOptionPane.showMessageDialog(null, "User Wins! User Score: "+ String.valueOf(score));
        }
       //checks if ball hits a obstracal; 
        for(int i = 0; i < obstatcls.length; i++)
        {
            if(ball.getPoint_Xcoordinates() + 20 >= obstatcls[i].getPoint_Xcoordinates() && ball.getPoint_Xcoordinates() <= obstatcls[i].getPoint_Xcoordinates() + 100)
            {
                if(ball.getPoint_Ycoordinates() + 20 >= obstatcls[i].getPoint_Ycoordinates() && ball.getPoint_Ycoordinates() <= obstatcls[i].getPoint_Ycoordinates() + 45)
                {
                    if(!obstatcls[i].isHit())
                    {
                        if(ball.getPoint_Xcoordinates() + 20 - ball.getspeedX() <= obstatcls[i].getPoint_Xcoordinates() || ball.getPoint_Xcoordinates() - ball.getspeedX() >= obstatcls[i].getPoint_Xcoordinates() + 100)
                        {
                            ball.setSpeedX(-1 * ball.getspeedX());
                            obstatcls[i].setHit(true);
                            score += 5;
                        }
                        else
                        {
                        ball.setSpeedY(-1 * ball.getSpeedY());
                        obstatcls[i].setHit(true);
                        score += 5;
                        }
                    }
                }
            }
            
        }
        
        
        //if loses all lives resets everything
        if(lives == 0){
            mainTimer.stop();
            for(int i = 0; i < obstatcls.length; i++){
            	obstatcls[i].setHit(false);
            }
            lives = 3;
            time = 0;
            Information.setLives("" + lives);
            score = 0;
            timeDisplay = 0;

            Information.setTime("" + formatter.format(timeDisplay/60) + ":" +
                 formatter.format(timeDisplay%60));
            Information.setScore("" + score);
            
            ball.setPointX(400);
            ball.setPointY(698);
            ball.setSpeedX(0);
            ball.setSpeedY(0);
            Userblade.setPointX(335);
            Userblade.setPointY(720);
            ComBlade.setPointX(ball.getPoint_Xcoordinates());
            ComBlade.setPointY(0);
        }

        
    }
    
}
