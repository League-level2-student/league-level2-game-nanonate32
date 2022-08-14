
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class FlappyPanel extends JPanel implements ActionListener, KeyListener {
	public static BufferedImage image;
	public static boolean needImage = true;
	public static boolean gotImage = false;
	
	boolean shotFired = false;
	FlappyManager manager;
	Mario mario;

	final static int MENU = 0;
	final static int TUTORIAL = 1;
	final static int GAME = 2;
	final static int END = 3;
	Font titleFont;
	Font menuFont;
	static int currentState = MENU;
	static Timer pipeSpawn;
	static Timer fireballSpawn;
	Timer frameDraw;

	public FlappyPanel() {
		if (needImage) {
			loadImage("MarioBackground-scrolling-top.png");

		}
		mario = new Mario(250, 200, 50, 50);
		manager = new FlappyManager(mario);
		titleFont = new Font("Comic Sans", Font.PLAIN, 48);
		menuFont = new Font("Comic Sans", Font.PLAIN, 24);
		frameDraw = new Timer(1000 / 60, this);
		frameDraw.start();

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

	public void paintComponent(Graphics g) {
		if (currentState == MENU) {
			drawMenuState(g);
		} else if (currentState == TUTORIAL) {
			drawTutorialState(g);
		} else if (currentState == GAME) {
			drawGameState(g);
		} else if (currentState == END) {
			drawEndState(g);
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (currentState == END) {
				currentState = MENU;
				pipeSpawn.stop();

			} else if (currentState == MENU || currentState == TUTORIAL) {
				startGame();
				currentState = GAME;
			} else {
				currentState++;

			}
		}

		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			if (currentState == MENU) {
				currentState = TUTORIAL;
			} else {
				mario.velocity = -4;
			}
		}

		if (currentState == GAME) {

			if (e.getKeyCode() == KeyEvent.VK_F) {
				mario.dash();
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (currentState == MENU) {
			updateMenuState();
		} else if (currentState == GAME) {
			updateGameState();
		} else if (currentState == END) {
			updateEndState();
		}
		// System.out.println("action");

		repaint();
	}

	void startGame() {
		FlappyManager.pipes.clear();
		FlappyManager.fireballs.clear();
		pipeSpawn = new Timer(7000, manager);
		manager.addPipes();
		pipeSpawn.start();
		fireballSpawn = new Timer(10000, manager);
		fireballSpawn.start();
		mario.dashCounter = 0;
		manager.setScore(0);
		mario = new Mario(250, 200, 50, 50);
		FlappyManager.mario = mario;
		
		
	}

	void updateMenuState() {

	}

	void updateGameState() {
		manager.update();
		if (mario.isActive == false) {
			currentState = END;
		}
	}

	void updateEndState() {
	}

	void drawMenuState(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, FlappyMario.WIDTH, FlappyMario.HEIGHT);
		g.setFont(titleFont);
		g.setColor(Color.YELLOW);
		g.drawString("Flappy Mario", 40, 50);
		g.setFont(menuFont);
		g.drawString("Press ENTER to start", 125, 300);
		g.drawString("Press SPACE for tutorial", 125, 100);

	}

	void drawTutorialState(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, FlappyMario.WIDTH, FlappyMario.HEIGHT);
		g.setFont(titleFont);
		g.setColor(Color.CYAN);
		g.drawString("TUTORIAL", 40, 50);
		g.setFont(menuFont);
		g.drawString("Press SPACE to jump", 125, 300);
		g.drawString("DON'T GET BURNED", 125, 225);
		g.drawString("Press ENTER to start", 125, 150);
		g.drawString("Press F to dash", 125, 375);

	}

	void drawGameState(Graphics g) {
		g.drawImage(image, 0, 0, FlappyMario.WIDTH, FlappyMario.HEIGHT, null);
		manager.draw(g);
		g.drawString("score:" + manager.getScore(), 100, 100);
		if (mario.canDash == true) {
			g.drawString("Dash ready", 400, 700);
		} else {
			g.drawString("cooldown" + mario.dashCounter, 400, 700);
		}
	}

	void drawEndState(Graphics g) {
		g.setColor(Color.ORANGE);

		g.fillRect(0, 0, FlappyMario.WIDTH, FlappyMario.HEIGHT);
		g.setColor(Color.BLUE);
		g.setFont(titleFont);
		g.drawString("Your final score is " + manager.getScore(), 40, 400);
		g.setFont(menuFont);
		g.drawString("Press ENTER to return to menu", 40, 500);
	}
}
