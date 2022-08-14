import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;

public class Fireball extends FlappyObject {

	public static BufferedImage image;
	public static boolean needImage = true;
	public static boolean gotImage = false;
	boolean hasTurned = false;
	double xSpeed = 5;
	double ySpeed = 5;
	int xTarget = 5;
	int yTarget = 5;
	int fireballType = 0;

	Fireball(int x, int y) {

		super(x, y, 100, 100);
		speed = 1;
		jumpHeight = 1;
		if (needImage) {
			loadImage("fireball.png");

		}
		
fireballType = new Random().nextInt(3);
		if (fireballType == 0) {
			xSpeed = new Random().nextInt(5) + 2;
			ySpeed = 0;
			xTarget = x + FlappyMario.WIDTH;
			yTarget = y;
		}
		if (fireballType == 1 || fireballType == 2) {
			xTarget = (int) FlappyManager.mario.x;
			yTarget = (int) FlappyManager.mario.y;
			int xDif = (xTarget - x);
			int yDif = (yTarget - y);
			xTarget += 1 * xDif;
			yTarget += 1 * yDif;
			xSpeed = xDif / 70;
			ySpeed = yDif / 70;
			double hypotenuse = Math.sqrt(yDif * yDif + xDif * xDif);
			double angle = -Math.tan(yDif / xDif);
		}

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
		int xSpeedMod = 0;
		if( Mario.areDashing == true) {
			xSpeedMod = -2;
		}
		x += xSpeed + xSpeedMod;
		y += ySpeed;
		//System.out.println(x + ">" + (xTarget + 200));

		if (fireballType == 2 && x > xTarget + 200 && hasTurned == false) {
			System.out.println("turning around");
			xTarget = (int) FlappyManager.mario.x;
			yTarget = (int) FlappyManager.mario.y;
			int xDif = (int) ((xTarget - x));
			int yDif = (int) ((yTarget - y));
			xTarget += 4 * xDif;
			yTarget += 4 * yDif;
			xSpeed = xDif / 80;
			ySpeed = yDif / 80;
			double hypotenuse = Math.sqrt(yDif * yDif + xDif * xDif);
			double angle = -Math.tan(yDif / xDif);
			hasTurned = true;
		}

		super.update();
	}

	void draw(Graphics g) {
		if (gotImage) {
			g.drawImage(image, (int) x, (int) y, width, height, null);
		} else {
			g.setColor(Color.BLUE);
			g.fillRect((int) x, (int) y, width, height);
		}

	}

	void setTarget(int x, int y) {
		xTarget = x;
		yTarget = y;
	}
}
