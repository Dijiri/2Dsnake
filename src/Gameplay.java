import java.awt.Color; // allows us colors for our background
import java.awt.Font; // allows different fonts for text on screen
import java.awt.Graphics; // allows us to paint graphics
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener; // tracks keystrokes
import java.util.Random; // random integer for enemy position
import javax.swing.ImageIcon; // allows us to export image files for the assets of game
import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; // I don't know what this is, but Eclipse likes to yell at me if I delete it so keep this in
	// snake position on screen
	private int[] snakexlength = new int[750];
	private int[] snakeylength = new int[750];
	
	// arrow key movement
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;
	
	// png images of snake head
	private ImageIcon rightmouth;
	private ImageIcon upmouth;
	private ImageIcon downmouth;
	private ImageIcon leftmouth;
	
	// snake length
	private int lengthofsnake = 3;
	
	// timer
	private Timer timer;
	private int delay = 100;
	private ImageIcon snakeimage;
	
	// places where enemies can spawn on the screen
	private int [] enemyxpos = {25,50,75,100,125,150,175,200,225,250,275,300,325,
	                            350,375,400,425,450,475,500,525,550,575,600,625,650,
	                            675,700,725,750,775,800,825,850};
	private int [] enemyypos = {75,100,125,150,175,200,225,250,275,300,325,
	                            350,375,400,425,450,475,500,525,550,575,600,625};
	
	// enemy image picture
	private ImageIcon enemyimage;
	
	// random positions for enemies
	private Random random = new Random();
	
	private int xpos = random.nextInt(34); // 34 is number of values in enemyxpos array
	private int ypos = random.nextInt(23);
	
	private int score = 0;
	private int highScore = 0;
	
	private int moves = 0;
	
	// title image picture
	private ImageIcon titleImage;
	
	// gameplay method which is called in main() - allows it to track keys and starts the timer for game
	public Gameplay() {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}
	
	public void paint(Graphics graphic) {
		
		// set intial snake length of 3
		if(moves == 0) {
			snakexlength[2] = 50;
			snakexlength[1] = 75;
			snakexlength[0] = 100;
			
			snakeylength[2] = 100;
			snakeylength[1] = 100;
			snakeylength[0] = 100;
		}
		
		// draw title image border
		graphic.setColor(Color.white);
		graphic.drawRect(24, 10, 851, 5);
		
		
		// draw the title image
		titleImage = new ImageIcon("snaketitle.jpg");
		titleImage.paintIcon(this, graphic, 25, 11);
		
		// draw border for gameplay
		graphic.setColor(Color.WHITE);
		graphic.drawRect(24, 74, 851, 577);
		
		// draw background for the gameplay
		graphic.setColor(Color.black);
		graphic.fillRect(25, 75, 850, 575);
		
		// draw score
		graphic.setColor(Color.white);
		graphic.setFont(new Font("arial", Font.PLAIN, 14));
		graphic.drawString("Score: " + score, 780, 30);
		
		// draw length of snake
		graphic.setColor(Color.white);
		graphic.setFont(new Font("arial", Font.PLAIN, 14));
		graphic.drawString("Length: " + lengthofsnake, 780, 50);
		
		// draw high score
		graphic.setColor(Color.white);
		graphic.setFont(new Font("arial", Font.PLAIN, 14));
		graphic.drawString("High Score: " + highScore, 670, 30);
		
		
		rightmouth = new ImageIcon("rightmouth.png");
		rightmouth.paintIcon(this, graphic, snakexlength[0], snakeylength[0]);
		
		
		// display the different parts of the snake
		for(int a = 0; a < lengthofsnake; a++) {
			if(a == 0 && right) {
				rightmouth = new ImageIcon("rightmouth.png");
				rightmouth.paintIcon(this, graphic, snakexlength[a], snakeylength[a]);
			}
			if(a == 0 && left) {
				leftmouth = new ImageIcon("leftmouth.png");
				leftmouth.paintIcon(this, graphic, snakexlength[a], snakeylength[a]);
			}
			if(a == 0 && down) {
				downmouth = new ImageIcon("downmouth.png");
				downmouth.paintIcon(this, graphic, snakexlength[a], snakeylength[a]);
			}
			if(a == 0 && up) {
				upmouth = new ImageIcon("upmouth.png");
				upmouth.paintIcon(this, graphic, snakexlength[a], snakeylength[a]);
			}
			if(a != 0) {
				snakeimage = new ImageIcon("snakeimage.png");
				snakeimage.paintIcon(this, graphic, snakexlength[a], snakeylength[a]);
			}
		}
		
		// draw the enemy and allow player to eat it, spawning a new enemy
		enemyimage = new ImageIcon("enemy.png");
		
		if((enemyxpos[xpos] == snakexlength[0]) && enemyypos[ypos] == snakeylength[0]) {
			score++;
			lengthofsnake++;
			
			// TODO: fix enemy spawn locations, still not working
			for(int a = 0; a < lengthofsnake; a++) {
				xpos = random.nextInt(34); 
					if((xpos == snakexlength[a]) || (ypos == snakeylength[a])) {
					xpos = random.nextInt(34);
				}
				ypos = random.nextInt(23);
					if((xpos == snakexlength[a]) || (ypos == snakeylength[a])) {
				    ypos = random.nextInt(34);
				}
			}
		}
		
		enemyimage.paintIcon(this, graphic, enemyxpos[xpos], enemyypos[ypos]);
		
		// detect collision of head with body
		for(int b = 1; b < lengthofsnake; b++) {
			if(snakexlength[b] == snakexlength[0] && snakeylength[b] == snakeylength[0]) {
				right = false;
				left = false;
				up = false;
				down = false;
				
				// display game over screen and space to restart
				graphic.setColor(Color.white);
				graphic.setFont(new Font("arial", Font.BOLD, 50));
				graphic.drawString("Game Over", 300, 300);
				
				graphic.setFont(new Font("arial", Font.BOLD, 20));
				graphic.drawString("Space to RESTART", 350, 340);
			}
		}
		
		graphic.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		// allow snake to move across the screen - body parts will follow
		timer.start();
		if(right) {
			for(int r = lengthofsnake - 1; r >= 0; r--) {
				snakeylength[r + 1] = snakeylength[r];
			}
			for(int r = lengthofsnake; r >= 0; r--) {
				if(r == 0) {
					snakexlength[r] = snakexlength[r] + 25;
				} else {
					snakexlength[r] = snakexlength[r - 1];
				}
				if(snakexlength[r] > 850) {
					snakexlength[r] = 25;
				}
			}
			
			repaint();
		}
		if(left) {
			for(int r = lengthofsnake - 1; r >= 0; r--) {
				snakeylength[r + 1] = snakeylength[r];
			}
			for(int r = lengthofsnake; r >= 0; r--) {
				if(r == 0) {
					snakexlength[r] = snakexlength[r] - 25;
				} else {
					snakexlength[r] = snakexlength[r - 1];
				}
				if(snakexlength[r] < 25) {
					snakexlength[r] = 850;
				}
			}
			
			repaint();
		}
		if(up) {
			for(int r = lengthofsnake - 1; r >= 0; r--) {
				snakexlength[r + 1] = snakexlength[r];
			}
			for(int r = lengthofsnake; r >= 0; r--) {
				if(r == 0) {
					snakeylength[r] = snakeylength[r] - 25;
				} else {
					snakeylength[r] = snakeylength[r - 1];
				}
				if(snakeylength[r] < 75) {
					snakeylength[r] = 625;
				}
			}
			
			repaint();
		}
		if(down) {
			for(int r = lengthofsnake - 1; r >= 0; r--) {
				snakexlength[r + 1] = snakexlength[r];
			}
			for(int r = lengthofsnake; r >= 0; r--) {
				if(r == 0) {
					snakeylength[r] = snakeylength[r] + 25;
				} else {
					snakeylength[r] = snakeylength[r - 1];
				}
				if(snakeylength[r] > 625) {
					snakeylength[r] = 75;
				}
			}
			
			repaint();
		}



		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		// detect space key press for restart and change high score
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			moves = 0;
			if (score > highScore) {
				highScore = score;
			}
			score = 0;
			lengthofsnake = 3;
			repaint();
		}
		
		// detect arrow key presses from player
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			
			moves++;
			right = true;
			if (!left) {
				right = true;
			} else {
				right = false;
				left = true;
			}
			
			up = false;
			down = false;
			
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			
			moves++;
			left = true;
			if (!right) {
				left = true;
			} else {
				left = false;
				right = true;
			}
			
			up = false;
			down = false;
			
		}
		if(e.getKeyCode() == KeyEvent.VK_UP) {
	
			moves++;
			up = true;
			if (!down) {
				up = true;
			} else {
				up = false;
				down = true;
		}
	
			left = false;
			right = false;
	
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
	
			moves++;
			down = true;
			if (!up) {
				down = true;
			} else {
				down = false;
				up = true;
			}
	
			left = false;
			right = false;
	
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
