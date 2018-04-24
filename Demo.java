package tball2;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;
/**
 * the ball moves with a constant speed,it changes its direction when
 * it touch walls or the paddle
 * each time it touches the paddle,you got 1 score up
 * for every 5 scores,the ball will speed up by 5.
 * when the ball is below the paddle ,game over.
 * 
 * menu functions:start a new game
 *                pause
 *                resume
 *                save and exit
 *                 
 *
 */
public class Demo  extends JFrame implements ActionListener{
	private MyPanel mp=null;
	private JMenuBar jmb=null;
	private JMenu jm=null;
	private JMenuItem jmi1=null;
	private JMenuItem jmi2=null;
	private JMenuItem jmi3=null;
	private JMenuItem jmi4=null;
	private IPanel ip=null;
    
	public static void main(String[] args) {
		Demo d=new Demo();  
		
	}
	
	public Demo(){
		ip=new IPanel();
		jmi1=new JMenuItem("New Game(N)");
		jmi2=new JMenuItem("Save and Exit(E)");
		jmi3=new JMenuItem("Pause(P)");
		jmi4=new JMenuItem("Resume(R)");
		jmi1.setMnemonic('N');
		jmi2.setMnemonic('E');
		jmi3.setMnemonic('P');
		jmi4.setMnemonic('R');
		jmi1.addActionListener(this);
		jmi1.setActionCommand("new");
		jmi4.addActionListener(this);
		jmi4.setActionCommand("resume");
		jmi2.setActionCommand("exit");
		jmi2.addActionListener(this);
		jmi3.addActionListener(this);
		jmi3.setActionCommand("pause");
		jmb=new JMenuBar();
		jm=new JMenu("Game(G)");
		jm.setMnemonic('G');
		jm.add(jmi1);
		jm.add(jmi4);
		jm.add(jmi2);
		jm.add(jmi3);
		jmb.add(jm);
		this.setJMenuBar(jmb);
		this.add(ip);
	    this.setSize(350,600);
	    this.setVisible(true);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("new")){
			Recorder.setSCORE(0);
			this.getContentPane().removeAll();
			mp=new MyPanel();
			Thread t2=new Thread(mp);
			t2.start();		
			this.addKeyListener(mp);
			this.remove(ip);
			this.add(mp);
			this.setVisible(true);
		}
		if(e.getActionCommand().equals("exit")){
			Recorder.setX_Pos(this.mp.b.getX());
			Recorder.setY_Pos(this.mp.b.getY());
			Recorder.setN(this.mp.b.getN());
			Recorder.setP_X(this.mp.p.getX());
			Recorder.setP_Y(this.mp.p.getY());
			Recorder.keepRecord();
			System.exit(0);
		}
		if(e.getActionCommand().equals("pause")){
			this.mp.p.setSpeed(0);
			this.mp.b.setSpeed(0);
			this.repaint();
			Recorder.setX_Pos(this.mp.b.getX());
			Recorder.setY_Pos(this.mp.b.getY());
			Recorder.setN(this.mp.b.getN());
			System.out.println(this.mp.b.getX()+"    "+this.mp.b.getY());
			Recorder.setP_X(this.mp.p.getX());
			Recorder.setP_Y(this.mp.p.getY());
			Recorder.keepRecord();
			this.mp.enable(false);
			
		}
		else if(e.getActionCommand().equals("resume")){
			//恢复记录
			System.out.println(this.mp.b.getX()+"    "+this.mp.b.getY());
		    Recorder.getRecord();
		    int a=Recorder.getX_Pos();
		    int b=Recorder.getY_Pos();
		    int c=Recorder.getP_X();
		    int d=Recorder.getP_Y();
		    int f=Recorder.getN();
		    this.getContentPane().removeAll();	
		    mp=new MyPanel();
			Thread t2=new Thread(mp);
			mp.b.setX(a);
			mp.b.setY(b);
			mp.b.setN(f);
			mp.p.setX(c);
			mp.p.setY(d);
			t2.start();
			this.addKeyListener(mp);
			this.remove(ip);
			this.add(mp);
			this.setVisible(true);
		}
	}

	
class IPanel extends JPanel{
	
	public IPanel(){
		
	}
	public void paint(Graphics g){
		super.paint(g);
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 350, 600);
		g.setColor(Color.blue);
		Font f=new Font("", Font.BOLD, 50);
		g.drawString("WELCOME", 150, 300);
	}
	
 
}
class MyPanel extends JPanel implements KeyListener,Runnable {
	Ball b;
	Paddle p;
	
	JOptionPane pane;
	JDialog dialog;
    
	
	public MyPanel()
	{   
		b=new Ball(10,10);
		p=new Paddle(0,590);
	    
	    Thread t1=new Thread(b);
	    t1.start();
	    Thread t3=new Thread(p);
	    t3.start();
	    
	}
	public void showM(){
	     pane = new JOptionPane("Your Score is "+Recorder.getSCORE());
		 dialog = pane.createDialog("Game Over!");
		dialog.setVisible(true);
	}
	 private ActionListener closeJDialog = new ActionListener() {		 
		         public void actionPerformed(ActionEvent e) {		 
		             if (dialog.isShowing()) {	 
		                 dialog.dispose();		 
		             }		 
		         }
		 
	 };

	public void paint(Graphics g){
		super.paint(g);
		
		g.setColor(Color.yellow);
        g.fillRect(0, 0, 350, 600);
        if(this.b.isAlive==true){
       this.drawBall(this.b.getX(), this.b.getY(), 0, g);
       }
        if(p.getX()+80>=350){
        	this.drawPaddle(270, this.p.getY(), g);
        }else{
        this.drawPaddle(this.p.getX(), this.p.getY(), g);}
	   g.setColor(Color.black);
	   Font f=new Font("", Font.BOLD,50);
	   g.drawString(Integer.toString(Recorder.getSCORE()), 175, 300);
	}
	
	public void drawBall(int x,int y,int direction,Graphics g){
		
		g.setColor(Color.blue);
        g.fillOval(x, y, 10, 10);
	}
	public void drawPaddle(int x,int y,Graphics g){
		g.setColor(Color.blue);
		g.fill3DRect(x, y, 80, 10, false);
		
	}
	public void isAlive(){
		if((b.getX()+10<p.getX()&&b.getY()+10>=p.getY())||(b.getX()>p.getX()+80&&b.getY()+10>=p.getY())){
			b.isAlive=false;
		   this.showM();
		}else b.isAlive=true;
	}
	public void pAlive(){
		if(p.getY()<=10||b.isAlive==false){
			p.isAlive=false;
			this.p.setSpeed(0);
			b.isAlive=false;
		}
		else p.isAlive=true;
	}
	public void hitWall()
	{
		if(b.getY()<=0){
			b.setY(0);
			int n=(int) (Math.random()*2);
        	if(n==0){
        	b.setN(0);;}
        	else if(n==1){
        		b.setN(2);;
        	}
		}
		//touch the left wall
		if(b.getX()<=0){
			b.setX(0);
			int n=(int) (Math.random()*2);
        	if(n==0){
        	b.setN(1);}
        	else if(n==1){
        		b.setN(0);
        	}
		}
		//touch the right wall
		if(b.getX()>=350){
			b.setX(340);
			int n=(int) (Math.random()*2);
        	if(n==0){
        	b.setN(2);}
        	else if(n==1){
        		b.setN(3);;
        	}
		}
		//touch the paddle
	        if(b.getY()+10>=p.getY()&&b.getX()+10>=p.getX()&&b.getX()<=p.getX()+90){
	        	  b.setY(p.getY()-11);
	        	int n=(int) (Math.random()*2);
	        	if(n==0){
	        	b.setN(1);;
	        	}
	        	else if(n==1){
	        		b.setN(3);;
	        	}
	        	Recorder.ScoreUP();
	        }	
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		
			
		    //control the paddle 
	 if(e.getKeyCode()==KeyEvent.VK_D){
			//right
			this.p.moveRight();
		}else if(e.getKeyCode()==KeyEvent.VK_A){
			//left
			this.p.moveLeft();
		}
	 this.repaint();
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.isAlive();
			this.pAlive();
			this.hitWall();
	    	this.repaint();
	    	if(Recorder.getSCORE()==5){
	    		this.b.setSpeed(this.b.getSpeed()+5);
	    	}
	    	if(this.b.isAlive==false){
	    		break;
	    	}
	  
	}}}}