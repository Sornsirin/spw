//package f2.spw;

import java.awt.Color;
import java.awt.Graphics2D;

import java.awt.Toolkit;
import java.awt.Image;


public class SpaceShip extends Sprite{

	int hp = 3;
	int step = 7;
	boolean alive = true;
	
	public SpaceShip(int x, int y, int width, int height) {
		super(x, y, 40,40);
	}

	@Override
	public void draw(Graphics2D g) {
		Image img = Toolkit.getDefaultToolkit().getImage("pooh.gif");
        g.drawImage(img, x, y, width, height, null);
		
	}

	public void move(int directionX, int directionY){
		if(directionX != 0 && directionY == 0 ){
			x += (step * directionX);
			if(x < 0)
				x = 0;
			if(x > 400 - width)
				x = 400 - width;
		}
		if(directionX == 0 && directionY != 0 ){
			y += (step * directionY);
			if(y < 0)
				y = 0;
			if(y > 600 - height)
				y = 600	- height;
		}	
	}

	public void hit() {
		hp--;
		if(hp <= 0) alive = false;
	}

	public int getHp() {
		return hp;
	}

	public boolean isAlive() {
		return alive;
	}

}
