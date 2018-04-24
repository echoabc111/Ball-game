package tball2;
import java.awt.*;
import java.io.*;

import javax.swing.*;
import java.util.*;
class Recorder{
	private static int SCORE=0;
	private static int X_Pos=0;
	private static int Y_Pos=0;
	private static int B_Speed=0;
	private static int P_X=0;
	private static int P_Y=0;
	private static int N=0;
	
	public static int getP_X() {
		return P_X;
	}
	public static void setP_X(int p_X) {
		P_X = p_X;
	}
	public static int getP_Y() {
		return P_Y;
	}
	public static void setP_Y(int p_Y) {
		P_Y = p_Y;
	}
	public static int getB_Speed() {
		return B_Speed;
	}
	public static void setX_Speed(int b_Speed) {
		B_Speed = b_Speed;
	}
	public static int getX_Pos() {
		return X_Pos;
	}
	public static void setX_Pos(int x_Pos) {
		X_Pos = x_Pos;
	}
	public static int getY_Pos() {
		return Y_Pos;
	}
	public static void setY_Pos(int y_Pos) {
		Y_Pos = y_Pos;
		System.out.println(X_Pos+"   "+Y_Pos);
	}
	public static int getSCORE() {
		return SCORE;
	}
	public static void setSCORE(int sCORE) {
		SCORE = sCORE;
	}
	
	public static void ScoreUP()
	{
		SCORE++;
	}
	
	public static int getN() {
		return N;
	}
	public static void setN(int n) {
		N = n;
	}

	private static FileWriter fw=null;
	private static BufferedWriter bw=null;
	public static void keepRecord(){
		try{
		fw=new FileWriter("/Users/huichunliu/Desktop/game.txt");
		bw=new BufferedWriter(fw);
		bw.write(SCORE+"\r\n");
		bw.write(Y_Pos+"\r\n");
		bw.write(X_Pos+"\r\n");
		bw.write(P_X+"\r\n");
		bw.write(P_Y+"\r\n");}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try {
				bw.close();
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	private static FileReader fr=null;
	private static BufferedReader br=null;
	public static void getRecord(){
		try {
			fr=new FileReader("/Users/huichunliu/Desktop/game.txt");
			br=new BufferedReader(fr);
			String a=br.readLine();
		    SCORE=Integer.parseInt(a);
	        String b=br.readLine();
	        X_Pos=Integer.parseInt(b);
	        String c=br.readLine();
	        Y_Pos=Integer.parseInt(c);
	        String d=br.readLine();
	        P_X=Integer.parseInt(d);
	        String e=br.readLine();
	        P_Y=Integer.parseInt(e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
class Ball implements Runnable{
	private int x;   //x-coordinate of ball
	private int y;   //y-coordinate of ball
	private int speed; //speed of ball
	private int direction;  
    boolean isAlive=true;
	private int n=0;
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getSpeed() {
		return this.speed;
	}

	public void setSpeed(int speed) {
		
     this.speed=speed;
		
	}
	

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public Ball(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.setSpeed(20);
		
	}
	public void moveUpLeft(){
		if(this.y>0&&this.x>0){
			this.y=y-this.speed;
			this.x=x-this.speed;
		}
	}
	public void moveUpRight(){
		if(this.y>0&&this.x<350){
			this.y=y-this.speed;
			this.x=x+this.speed;			
		}
	}
public void moveDownLeft(){
	if(this.y<590&&this.x>0){
		this.x=x-this.speed;
		this.y=y+this.speed;		
	}
	}
public void moveDownRight(){
	if(this.y<590&&this.x<350){
		this.y=y+this.speed;
		this.x=x+this.speed;
	}
}


@Override

public void run() {
	while (true){
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch (n){
		case 0:this.moveDownRight();
		break;
		case 1:this.moveUpRight();
		break;
		case 2:this.moveDownLeft();
		break;
		case 3:
		 this.moveUpLeft();
		break;}
    if(this.isAlive==false){
    	break;
    }
		
	}

}}
class Paddle implements Runnable{
	private int x;
	private int y;
	private int speed=20;
	private int length=90;
	boolean isAlive=true;
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public Paddle(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		
	}
	
	public void moveUp(){
		if(y>0)
		y=y-speed;
	}
	public void moveDown(){
		//y=y+speed;
	}
	public void moveLeft(){
		if(x>0){
		x-=speed;}
	}
	public void moveRight(){
		if(x<600){
		x+=speed;}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				Thread.sleep(15000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.moveUp();
			if(this.isAlive==false){
				break;
			}
		}
	}
}
