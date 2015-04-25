import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import java.awt.Toolkit;
import java.awt.Image;


public class Enemy2 extends Sprite{
	public static final int Y_TO_FADE = 400;
	public static final int Y_TO_DIE = 600;
	
	private int step = 9;
	private boolean alive = true;
	
	public Enemy2(int x, int y) {
		super(x, y, 100, 100);
		
	}

	@Override
	public void draw(Graphics2D g) {
		if(y < Y_TO_FADE)
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		else{
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 
					(float)(Y_TO_DIE - y)/(Y_TO_DIE - Y_TO_FADE)));
		}
		
		    Image img = Toolkit.getDefaultToolkit().getImage("cat1.gif");
            g.drawImage(img, x, y, width, height, null);	
		
	}
	
	public void proceed(){
		y += step;
		if(y > Y_TO_DIE){
			alive = false;
		}
	}

	public void hit() {
		alive = false;
	}
	
	public boolean isAlive(){
		return alive;
	}
}