// this is the layout used by JPanel
import java.awt.BorderLayout;

// this allows us to define the colors for the rocks and ship and such
import java.awt.Color;

import java.awt.geom.AffineTransform;

// these allow me to draw and render the shapes and components
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

// used to detect keystrokes happening
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// this allows me to store all my objects
import java.util.ArrayList;

// allows me to put in delays, so the program runs smoothly
import java.util.concurrent.ScheduledThreadPoolExecutor;

// allows me to define units of time
import java.util.concurrent.TimeUnit;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class GameBoard extends JFrame {

	public static int numberOfRocks = 30;
	
	public static int boardWidth = 1000;
	public static int boardHeight = 1000;
	
	// keep track if a key is held down and which key it is
	public static boolean keyHeld = false;
	public static int keyHeldCode;
	
	public static void main(String[] args)
	{
		new GameBoard();
	}
	
	public static ArrayList<Blaster> blasters = new ArrayList<Blaster>();
		
	
	public GameBoard(){
		
		// define all the defaults for the JFrame
		this.setSize(boardWidth, boardHeight);
		this.setTitle("Asteroids!");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		addKeyListener(new KeyListener() {
			
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void keyPressed(KeyEvent e) 
			{

				/* keycodes
				w: 87
				a: 65
				s: 83
				d: 68
				*/
				
				// move forward
				if (e.getKeyCode() == 87)
				{
					System.out.println("Forward!");
					keyHeldCode = e.getKeyCode();
					keyHeld = true;
				}
				
				// move left 
				if (e.getKeyCode() == 65)
				{
					System.out.println("left!");
					keyHeldCode = e.getKeyCode();
					keyHeld = true;
				}
				
				// move right
				if (e.getKeyCode() == 68)
				{
					System.out.println("Right!");
					keyHeldCode = e.getKeyCode();
					keyHeld = true;
				}
				
				// move backwards
				if (e.getKeyCode() == 83)
				{
					System.out.println("Back!");
					keyHeldCode = e.getKeyCode();
					keyHeld = true;
				}
				
				// FIRE
				if (e.getKeyCode() == KeyEvent.VK_SPACE)
				{
					System.out.println("FIRE!!");
					
					blasters.add(new Blaster(GameDrawingPanel.ship.getShipNoseX(), 
							GameDrawingPanel.ship.getShipNoseY(), 
							GameDrawingPanel.ship.getRotationAngle()));
					keyHeldCode = e.getKeyCode();
					keyHeld = true;
				}
				
				
			}
			
			public void keyReleased(KeyEvent e) {
		
				keyHeld = false;
					
			}
			
		});  // end keyListener
		
		GameDrawingPanel gamePanel = new GameDrawingPanel();
		
		this.add(gamePanel, BorderLayout.CENTER);
		
		ScheduledThreadPoolExecutor executer = new ScheduledThreadPoolExecutor(5);
		
		executer.scheduleAtFixedRate(new repaint(this), 0, 20L, TimeUnit.MILLISECONDS);
		
	}
	
	//this is what I will draw the game on
	static class GameDrawingPanel extends JComponent {
		
		// holds the rocks I create
		public ArrayList<Rock> rocks = new ArrayList<Rock>();
		
		
		// get the original X and Y for the polygon
		int[] XcoordArray = Rock.polygonXArray;
		int[] YcoordArray = Rock.polygonYArray;
		
		static Spaceship ship = new Spaceship();
		
		// get the height and width of the gameboard
		int width = GameBoard.boardWidth;
		int height = GameBoard.boardHeight;
		
		
		// create the rocks
		public GameDrawingPanel(){
			
			for(int i = 0;i < numberOfRocks; i++)
			{
				
				// create a random starting point for the rocks
				int RandomX = (int)(Math.random()*(GameBoard.boardWidth-40+1));
				int RandomY = (int)(Math.random()*(GameBoard.boardHeight-40+1));
				
				// add the rocks to an arrayList to keep track of them all
				rocks.add(new Rock(Rock.getPolygonXArray(RandomX),Rock.getPolygonYArray(RandomY), 13, RandomX,RandomY ));
				Rock.rocks = rocks;
			}
		}
		
		public void paint(Graphics g) {
			
			// this is where I define the settings and changes in the settings of the graphics
			Graphics2D graphicSettings = (Graphics2D)g;
			AffineTransform identity = new AffineTransform();
			
			// draw a rectangle that is the size of the GameBoard
			graphicSettings.setColor(Color.BLACK);
			graphicSettings.fillRect(0, 0, getWidth(), getHeight());
			
			// set the rules of rendoring (Anti-Aliasing is important)
			graphicSettings.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
			// make the color white for the asteroids
			graphicSettings.setPaint(Color.WHITE);
			
			// Cycle through all of the rock objects
			for(Rock rock : rocks) {
				

				
				// Draw the rock if its supposed to be onScreen
				if (rock.onScreen)
				{
					// the rock moves
					rock.move(ship, GameBoard.blasters);
					
					graphicSettings.draw(rock);
				}
				
				
			}
					
			
			// press 'd'
			if (GameBoard.keyHeld == true && GameBoard.keyHeldCode == 68 )
			{
				ship.increaseRotationAngle();
				System.out.println("angle Decreased to " + ship.getRotationAngle());
			}
			
			// press 'a'
			if (GameBoard.keyHeld == true && GameBoard.keyHeldCode == 65 )
			{
				ship.decreaseRotationAngle();
				System.out.println("angle Increased to " + ship.getRotationAngle());
			}
			
			// press the 'w'
			if (GameBoard.keyHeld == true && GameBoard.keyHeldCode == 87 )
			{
				ship.setMoveAngle(ship.getRotationAngle());
				
				ship.IncreaseVelocityX(ship.MoveAngleX(ship.getMoveAngle())*.1);
				ship.IncreaseVelocityY(ship.MoveAngleY(ship.getMoveAngle())*.1);
				
				System.out.println("move speed Increased");
			}
			
			// press the 's'
			if (GameBoard.keyHeld == true && GameBoard.keyHeldCode == 83 )
			{
				ship.setMoveAngle(ship.getRotationAngle());
				
				ship.IncreaseVelocityX(-ship.MoveAngleX(ship.getMoveAngle())*.1);
				ship.IncreaseVelocityY(-ship.MoveAngleY(ship.getMoveAngle())*.1);
				
				System.out.println("move speed decreased");
			}
			
			

			ship.move();
			
			// set the origin to the screen so that the rotation of the ship occurs properly
			graphicSettings.setTransform(identity);
			
			// move the ship
			graphicSettings.translate(ship.getCenterX(), ship.getCenterY());
			
			// rotate the ship
			graphicSettings.rotate(Math.toRadians(ship.getRotationAngle()));
			
			// keep the ship drawn on the screen
			graphicSettings.draw(ship);
			
			// draw blasters
			for(Blaster blasters: GameBoard.blasters)
			{
				blasters.move();
				
				// if the blasters not on screen don't calculate for the blaster
				if (blasters.onScreen)
				{
					graphicSettings.setTransform(identity);
					graphicSettings.translate(blasters.getCenterX(), blasters.getCenterY());
					graphicSettings.draw(blasters);
				}
			}
			
		}
		
	}
	
	// repaint the board with the new location of all the polygons
	class repaint implements Runnable {
		
		GameBoard theBoard;
		
		public repaint(GameBoard theBoard) {
			this.theBoard = theBoard;
		}

		public void run() {
			theBoard.repaint();
			
		}	
	}
	
	
}
