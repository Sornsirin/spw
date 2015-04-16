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


public class GameEngine implements KeyListener, GameReporter{
	GamePanel gp;
		
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();	
	private ArrayList<Item> item = new ArrayList<Item>();
	private SpaceShip v;	
	
	private Timer timer;
	private long scoreTotal = 0;
	private long scoreItem = 0;
	private long score = 0;
	private double difficulty = 0.1;
	
	public GameEngine(GamePanel gp, SpaceShip v) {
		this.gp = gp;
		this.v = v;		
		
		gp.sprites.add(v);
		
		timer = new Timer(50, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				process();
				processItem();
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

	private void generateItem(){
		Item it = new Item((int)(Math.random()*390), 30);
		gp.sprites.add(it);
		item.add(it);
	}	

	private void process(){
		if(Math.random() < difficulty){
			generateEnemy();
		}
	
		Iterator<Enemy> e_iter = enemies.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
				score += 200;
			}
		}

	

		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double er;
		for(Enemy e : enemies){
			er = e.getRectangle();
			if(er.intersects(vr)){
				die();
				return;
			}
		}
	}
	private void processItem(){
		if(Math.random() < difficulty/10){
			generateItem();
		}
		
		Iterator<Item> it_iter = item.iterator();
		while(it_iter.hasNext()){
			Item it = it_iter.next();
			it.proceed();
			
			if(!it.isAlive()){
				it_iter.remove();			
			}
		}
		//gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double tr;
		for(Item it : item){
			tr = it.getRectangle();
			if(tr.intersects(vr)){	
				gp.sprites.remove(it);
				scoreItem += 500;
			}
		}
	}
		
	public void die(){
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
		}
	}

	public long getScore(){
		return score;
	}

	public void setScore(long score){
		this.score = score;
	}

	public long getScoreItem(){
		return scoreItem;
	}

	public void setScoreItem(long scoreItem){
		this.scoreItem = scoreItem;
	}

	public long getScoreTotal(){
		scoreTotal = scoreItem + score;
		return scoreTotal;
	}

	public void setScoreTotal(long scoreTotal){
		this.scoreTotal = scoreTotal;
	}

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
