import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Pipe extends FlappyObject {
	public static BufferedImage image;
	public static boolean needImage = true;
	public static boolean gotImage = false;	
	Pipe(int x, int y, int width, int height) {
		super( x, y, width, height);

		
		speed = 1;
		if (needImage) {
		    loadImage ("invertedpipe.jpg");
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
	x-=speed;
	super.update();
}
void draw(Graphics g) {
	if (gotImage) {
		g.drawImage(image, x, y, width, height, null);
	} else {
		g.setColor(Color.ORANGE);
		g.fillRect(x, y, width, height);
	}
}

}

