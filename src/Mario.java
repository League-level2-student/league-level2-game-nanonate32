import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.Timer;

import javax.imageio.ImageIO;

public class Mario  extends FlappyObject implements ActionListener  {

		public static BufferedImage image;
		public static boolean needImage = true;
		public static boolean gotImage = false;	
		boolean canDash = true;
		public static boolean areDashing = false;
		int dashCounter = 0;
		Timer dashTimer;
		Mario(int x, int y, int width, int height) {
			super(x, y, width, height);
			speed = 1;
			jumpHeight = 100;
			if (needImage) {
			    loadImage ("MarioKart.png");
			}
			dashTimer = new Timer(1000, this);
			dashTimer.start();
		}
		void loadImage(String imageFile) {
		    if (needImage) {
		        try {
		            image = ImageIO.read(this.getClass().getResourceAsStream(imageFile));
			    gotImage = true;
		        } catch (Exception e) {
		            
		        }
		        needImage = false;
		    }
		}
	void update() {
		
		y+=velocity;
		
		velocity+=0.1;
		
		super.update();
	}
	
	void draw(Graphics g) {
		if (gotImage) {
			g.drawImage(image, (int) x,(int) y, width, height, null);
		} else {
			g.setColor(Color.BLUE);
			g.fillRect((int) x, (int) y, width, height);
		}
	}
	void dash() {
		if(canDash == true) {
			FlappyManager.setPipeSpeed(3);
			
			areDashing = true;
			canDash = false;
			
			dashCounter = 10;
		}
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		dashCounter -=1;
		if(dashCounter <= 0) {
			canDash = true;
			
			dashCounter = 0;
		}
		if(dashCounter == 8) {
			FlappyManager.setPipeSpeed(1);
			
			areDashing = false;
		}
		
		
	}

	}


	


