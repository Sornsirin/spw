//package f2.spw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import java.awt.Toolkit;
import java.awt.Image;

public class GamePanel extends JPanel {
	
	private BufferedImage bi;	
	Graphics2D big;
		private Image Background;
        private Image Alives;
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();

	public GamePanel() {
		Background = Toolkit.getDefaultToolkit().getImage("bg2.jpg");
		Alives = Toolkit.getDefaultToolkit().getImage("love.gif");
		bi = new BufferedImage(400, 600, BufferedImage.TYPE_INT_ARGB);
		big = (Graphics2D) bi.getGraphics();
		//big.setBackground(Color.BLACK);
		big.drawImage(Background, 0, 0, 400, 600,null);
		big.drawImage(Alives, 5, 5, 20, 20, null);
	}

	public void updateGameUI(GameReporter reporter){
		//big.clearRect(0, 0, 400, 600);
		big.drawImage(Background, 0, 0, 400, 600,null);
		big.drawImage(Alives, 35, 5, 25, 25, null);
		big.setColor(Color.BLACK);		
		big.drawString(String.format("Score:%08d", reporter.getScore()), 290, 20);
		big.setColor(Color.BLACK);
		big.drawString(String.format("ICREAM:%5d", reporter.getCountIcream()), 110, 20);
		big.setColor(Color.RED);
		big.drawString(String.format("HP:%02d", reporter.gethp()), 60, 20);
		big.setColor(Color.BLACK);
		//big.drawString(String.format("Alive:   %2d", reporter.getSpaceShip()), 3, 20);
		big.drawString(String.format("Alive:   %2d", reporter.getheart()), 3, 20);

		big.setColor(Color.BLACK);
		big.drawString(String.format("CAKE:%5d", reporter.getCountCake()), 200, 20);
		for(Sprite s : sprites){
			s.draw(big);
		}
		
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bi, null, 0, 0);
	}
	
}
