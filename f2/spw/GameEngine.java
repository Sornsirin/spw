//package f2.spw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;

import javax.swing.JOptionPane;
import javax.swing.JFrame;
import java.awt.*;
import javax.swing.*;


public class GameEngine implements KeyListener, GameReporter{
	GamePanel gp;
		
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();

	private ArrayList<Enemy2> enemies2 = new ArrayList<Enemy2>();

	private ArrayList<Item> item = new ArrayList<Item>();

	private ArrayList<Icream> icreams = new ArrayList<Icream>();


	private SpaceShip v;	

	
	private Timer timer;
	private long scoreTotal = 0;
	private long scoreItem = 0;
	private long score = 0;
	private int countCake = 0;
	private int countIcream = 0;
	private boolean check = true;
	private double difficulty = 0.1;
	private int hp = 100;
	private int heart = 3;
	
	public GameEngine(GamePanel gp, SpaceShip v) {
		this.gp = gp;
		this.v = v;		
		
		gp.sprites.add(v);
		

		timer = new Timer(50, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(check)
					process();
				if(check)
					process2();
				if(check)
					processItem();
				if(check)
					processIcream();
				
					//checkSpaceShip();
			}
		});
		timer.setRepeats(true);
		
	}
	
	public void start(){
		timer.start();
	}
	
	private void generateEnemy(){
		Enemy e = new Enemy((int)(Math.random()*390), 30);
		gp.sprites.add(e);
		enemies.add(e);
	}

	private void generateEnemy2(){
		Enemy2 f = new Enemy2((int)(Math.random()*390), 30);
		gp.sprites.add(f);
		enemies2.add(f);
	}

	private void generateItem(){
		Item it = new Item((int)(Math.random()*390), 30);
		gp.sprites.add(it);
		item.add(it);
	}	

	private void generateIcream(){
		Icream ic = new Icream((int)(Math.random()*390), 30);
		gp.sprites.add(ic);
		icreams.add(ic);
	}	

	private void process(){
		if(Math.random() < difficulty/3){
			generateEnemy();
		}
	
		Iterator<Enemy> e_iter = enemies.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
			}
		}

		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double er;
		for(Enemy e : enemies){
			er = e.getRectangle();
			if(er.intersects(vr)){
				hp();
				//hit();
				//v.hp();
				e.hit();
				return;
			}
		}
	}

	private void process2(){
		if(Math.random() < difficulty/5){
			generateEnemy2();
		}
	
		Iterator<Enemy2> f_iter = enemies2.iterator();
		while(f_iter.hasNext()){
			Enemy2 f = f_iter.next();
			f.proceed();
			
			if(!f.isAlive()){
				f_iter.remove();
				gp.sprites.remove(f);
				
			}
		}
		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double fr;
		for(Enemy2 f : enemies2){
			fr = f.getRectangle();
			if(fr.intersects(vr)){
				hp();
				//hit();
				//v.hp();
				f.hit();
				return;
			}
		}
	}

	private void processItem(){
		if(Math.random() < difficulty/2){
			generateItem();
		}
		
		Iterator<Item> it_iter = item.iterator();
		while(it_iter.hasNext()){
			Item it = it_iter.next();
			it.proceed();
			
			if(!it.isAlive()){
				gp.sprites.remove(it);
				it_iter.remove();			
			}
		}
		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double tr;
		for(Item it : item){
			tr = it.getRectangle();
			if(tr.intersects(vr)){	
				score += 500;
				countCake++;
				it.pickUp();
			}
		}
	}

	private void processIcream(){
		if(Math.random() < difficulty/2){
			generateIcream();
		}
		
		Iterator<Icream> ic_iter = icreams.iterator();
		while(ic_iter.hasNext()){
			Icream ic = ic_iter.next();
			ic.proceed();
			
			if(!ic.isAlive()){
				gp.sprites.remove(ic);
				ic_iter.remove();			
			}
		}
		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double cr;
		for(Icream ic : icreams){
			cr = ic.getRectangle();
			if(cr.intersects(vr)){	
				score += 300;
				countIcream++;
				ic.pickUp();
			}
		}
	}

	/*public void checkSpaceShip() {
		if(!v.isAlive()) 
			die();
	}*/
	public void hp(){
		if(hp > 25)
			hp -= 25;
		else 
			hit();		
	}

	public void hit() {
		hp = 100;
		heart--;
		if(heart < 0 ) {
			hp = 0;
			//v.pickUp();
			die();
		}		
	}
	public void restart(){
		for(Enemy e : enemies){
			gp.sprites.remove(e);
			e.hit();
		}
		for(Enemy2 f : enemies2){
			gp.sprites.remove(f);
			f.hit();
		}
		for(Item it : item){
			gp.sprites.remove(it);
			it.pickUp();
		}
		for(Icream ic : icreams){
			gp.sprites.remove(ic);
			ic.pickUp();
		}
		//checkSpaceShip();
		//v.isAlive();
		start();
		check = true;
		
		score = 0;
		countCake = 0;
		countIcream = 0;
		hp = 100;
		heart = 3;
		
		v.x = 180;
		v.y = 550;
		
	}
	
	
	public void die(){
		timer.stop();
		check = false;
		JFrame frame = new JFrame();
		//String name = JOptionPane.showInputDialog("Plase Input Your Name : ");
		//JOptionPane.showMessageDialog(frame,name +": MAXSCORE : " + getScore());
		JOptionPane.showMessageDialog(frame, "MAXSCORE : " + getScore());
	}
	public void stop(){
		timer.stop();	
	}

	
	void controlVehicle(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			v.move(0,-1);
			break;
		case KeyEvent.VK_DOWN:
			v.move(0,1);
			break;
		case KeyEvent.VK_LEFT:
			v.move(-1,0);
			break;
		case KeyEvent.VK_RIGHT:
			v.move(1,0);
			break;
		case KeyEvent.VK_D:
			difficulty += 0.1;
			break;
		case KeyEvent.VK_R:
			restart();
			break;
		}
	}

	public int getheart() {
		return heart;
	}

	public int gethp() {
		return hp;
	}
	public void setheart(int heart) {
		this.heart = heart;
	}

	public void sethp(int hp) {
		this.hp = hp;
	}

	public int getCountCake(){
		return countCake;
	}
	public void setCountCake(int countCake){
		this.countCake = countCake;
	}

	public int getCountIcream(){
		return countIcream;
	}
	public void setCountIcream(int countIcream){
		this.countIcream = countIcream;
	}

	public long getScore(){
		return score;
	}

	public void setScore(long score){
		this.score = score;
	}

	/*public int getSpaceShip() {
		return v.getheart();
	}

	public int getHP() {
		return v.gethp();
	}*/

	@Override
	public void keyPressed(KeyEvent e) {
		controlVehicle(e);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//do nothing
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//do nothing		
	}
}
