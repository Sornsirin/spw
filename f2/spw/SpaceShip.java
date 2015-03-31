package f2.spw;

import java.awt.Color;
import java.awt.Graphics2D;

public class SpaceShip extends Sprite{

	int step = 8;
	
	public SpaceShip(int x, int y, int width, int height) {
		super(x, y, width, height);
		
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.BLUE);
		g.fillRect(x, y, width, height);
		
	}

	public void move(int directionX, int directionY){
		if(directionX != 0 && directionY == 0 ){
			x += (step * directionX);
			if(x < 0)
				x = 0;
			if(x > 400 - width)
				x = 400 - width;
		}
		if(directionX == 0 && directionY != 0 )
			y += (step * directionY);
			if(y < 0)
				y = 0;
			if(y > 600 - height)
				y = 600	- height;
		}	
	}

}
