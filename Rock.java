import java.awt.Polygon;
import java.util.ArrayList;
import java.awt.Rectangle;

public class Rock extends Polygon {

	// keep track of where the rock is (top left corner)
	int upperLeftXPosition;
	int upperLeftYPosition;
	
	// keep track of how fast the rock is moving
	int xDirection = 0;
	int yDirection = 0;
	
	// keep track of where the rock can go
	int width = GameBoard.boardWidth;
	int height = GameBoard.boardHeight;
	
	// these are all of the points that make up the complex polygon of the rock
	int[] XcoordArray;
	int[] YcoordArray;
	
	// is the rock supposed to be displayed?
	public boolean onScreen = true;
	
	// these are some pre-defined rock points (maybe ill randomize them later)
	public static int[] polygonXArray = {10,17,26,34,27,36,26,14,8,1,5,1,10};
	public static int[] polygonYArray = {0,5,1,8,13,20,31,28,31,22,16,7,0};
	public static ArrayList<Rock> rocks; 
	
	// constructor to actually build the rock
	public Rock(int[] polygonXArray, int[] polygonYArray, int pointsInPolygon, int startX, int startY)
	{
		// recall a rock is derived from Polygon, so we can make a polygon thats a rock
		super(polygonXArray, polygonYArray, pointsInPolygon);
		
		// this makes the rocks not all move the same direction or speed
		while (this.xDirection == 0 && this.yDirection == 0 )
		{
		this.xDirection = (int)(Math.random()*10-5);
		this.yDirection = (int)(Math.random()*10-5);
		}
		
		// this tells us where the rock will appear on the GameBoard
		this.upperLeftXPosition = startX;
		this.upperLeftYPosition = startY;
	}

	
	// this is the bounds of the rock that we will use for collisions
	public Rectangle getBounds() {
		
		return new Rectangle(super.xpoints[0], super.ypoints[0],35,35);
	}
	
	// rocks can move :)
	public void move(Spaceship ship, ArrayList<Blaster> blasters){
		
		
		//  this is the rock that we are checking to see if it bounces
		Rectangle rockToCheck = this.getBounds();
		
		// cycle through all rocks 
		for(Rock rock: rocks){
			Rectangle otherRock = rock.getBounds();
			
			// we dont want to check the rock to see if its bouncing off of itself
			if (rock != this && otherRock.intersects(rockToCheck) && rock.onScreen == true)
			{
				int tempXDirection = this.xDirection;
				int tempYDirection = this.yDirection;
				
				this.xDirection = rock.xDirection;
				this.yDirection = rock.yDirection;
				
				rock.xDirection = tempXDirection;
				rock.yDirection = tempYDirection;
			}
			
			// this is the bounds of the ship (used to detect collisions with rocks)
			Rectangle shipBox = ship.getBounds();
			
			// detecting a collision with the ship
			if(otherRock.intersects(shipBox) && rock.onScreen == true)
			{
				ship.setCenterX(width/2);
				ship.setCenterY(height/2);
				
				ship.setVelocityX(0);
				ship.setVelocityY(0);
				
			}
			
			// detecting a collision with a blaster
			for (Blaster blaster : blasters){
				if (blaster.onScreen)
				{
					if (otherRock.contains(blaster.getCenterX(),blaster.getCenterY()) && rock.onScreen == true && blaster.onScreen == true)
					{
						
						// remove the rock and blaster from the screen
						rock.onScreen = false;
						blaster.onScreen = false;
					}
				}
			}
			
		}
		
		// keep track of the top left position of the rocks
		int upperLeftXPosition = super.xpoints[0];
		int upperLeftYPosition = super.ypoints[0];
	
		// if the rock goes out of the screen,  bounce the rock off the wall
		if (upperLeftXPosition < 0 || upperLeftXPosition + 25 > width)
		{
			xDirection = -xDirection;
		}
		
		// if the rock goes out of the screen,  bounce the rock off the wall
		if (upperLeftYPosition < 0 || upperLeftYPosition + 50 > height)
		{
			yDirection = -yDirection;
		}
		
		// move all points on the asteroid
		for(int i = 0; i < super.xpoints.length;i++)
		{
			super.xpoints[i] += xDirection;
			super.ypoints[i] += yDirection;
		}

	}
	
	// get the X coordinents of all points on the asteroid
	public static int[] getPolygonXArray(int startX)
	{
		int[] tempXArray = (int[]) polygonXArray.clone();
		
		for(int i = 0; i < tempXArray.length;i++)
		{
			tempXArray[i] += startX;
		}
		
		return tempXArray;
	}
	
	// get the Y coordinents of all points on the asteroid
	public static int[] getPolygonYArray(int startY)
	{
		int[] tempYArray = (int[]) polygonYArray.clone();
		
		for(int i = 0; i < tempYArray.length;i++)
		{
			tempYArray[i] += startY;
		}
		
		return tempYArray;
	}
	
	
}
