import java.awt.*;

public class Spaceship extends Polygon{

	// the direction the ship is moving
	private double velocityX = 0;
	private double velocityY = 0;
	
	// the board that she ship can be in
	int GameWidth = GameBoard.boardWidth;
	int GameHeight = GameBoard.boardHeight;
	
	// the center of the ship
	private double centerX = GameWidth/2;
	private double centerY = GameHeight/2;
	
	// the points of the ship so it can be drawn
	public static int[] polygonArrayX = {-15,25,-15,-5,-15};
	public static int[] polygonArrayY = {-15,0,15,0,-15};
	
	// the dimentions of the ship
	private int shipWidth = 30;
	private int shipHeight = 30;
	
	// the rotation data for the ship
	private double rotationAngle = 0;
	private double moveAngle = 0;
	
	// make the spaceship
	public Spaceship() 
	{
		super(polygonArrayX, polygonArrayY, 5);
	}
	
	// get center points
	public double getCenterX() { return centerX; }
	public double getCenterY() { return centerY; }
	
	// set center points
	public void setCenterX(double xcenter) { this.centerX = xcenter; }
	public void setCenterY(double ycenter) { this.centerY = ycenter; }
	
	// move the spaceship
	public void increaseXPosition(double amount) {this.centerX += amount; }
	public void increaseYPosition(double amount) {this.centerY += amount; }
	
	// get the ship dimentions
	public int getShipWidth() { return shipWidth; }
	public int getShipHeight() { return shipHeight; }
	
	// get the ship velocity
	public double getVelocityX() { return velocityX; }
	public double getVelocityY() { return velocityY; }
	
	// set the velocity of the ship
	public void setVelocityX(double newVelocityX) { velocityX = newVelocityX; }
	public void setVelocityY(double newVelocityY) { velocityY = newVelocityY; }
	
	// change velocity of the ship
	public void IncreaseVelocityX (double velocityIncreaseX)  { velocityX += velocityIncreaseX; }
	public void IncreaseVelocityY (double velocityIncreaseY)  { velocityY += velocityIncreaseY; }
	
	// set and change the moving angle of the ship
	public void setMoveAngle(double newMoveAngle) {this.moveAngle = newMoveAngle; }
	public void increaseMoveAngle(double addedMoveAngle) {this.moveAngle += addedMoveAngle; }
	public double getMoveAngle() {return moveAngle; }
	
	
	// get the move angle in terms of Cos
	public double MoveAngleX(double moveAngleX) 
	{
		return (double)(Math.cos(moveAngleX*Math.PI/180));
	}
	
	// get the move angle in terms of Sin
	public double MoveAngleY(double moveAngleY) 
	{
		return (double)(Math.sin(moveAngleY*Math.PI/180));
	}
	
	// get the rotation angle
	public double getRotationAngle() {return rotationAngle;}
	
	// turn the ship clickwise
	public void increaseRotationAngle()
	{
		if(getRotationAngle() > 355) 
		{
			rotationAngle = 0;
		}
		else
		{
			rotationAngle +=5;
		}
	}
	
	// turn the ship counterclockwise
	public void decreaseRotationAngle()
	{
		if(getRotationAngle() < 0) 
		{
			rotationAngle = 355;
		}
		else
		{
			rotationAngle -=5;
		}
	}
	
	// make a rectangle for collision detection of the spaceship
	public Rectangle getBounds(){
		return new Rectangle((int)getCenterX()-15, (int)getCenterY()-15, getShipWidth(), getShipHeight());
	}
	
	// get the vector of the Spaceship Nose
	public double getShipNoseX()
	{
		return this.getCenterX() + Math.cos(rotationAngle*Math.PI/180)*15;
	}
	
	// get the location for the blaster to be created
	public double getShipNoseY()
	{
		return this.getCenterY() + Math.sin(rotationAngle*Math.PI/180)*15;
	}
	
	// move the ship!
	public void move()
	{
		
		this.increaseXPosition(this.getVelocityX());
		this.increaseYPosition(this.getVelocityY());
		
		// if the ship goes off screen,  remake it on the other side of the screen
		if (this.getCenterX() < 0)
		{
			this.setCenterX(GameWidth);
		}
		else if (this.getCenterX() > GameWidth)
		{
			this.setCenterX(0);
		}
		
		// if the ship goes off screen,  remake it on the other side of the screen
		if (this.getCenterY() < 0)
		{
			this.setCenterY(GameHeight);
		}
		else if (this.getCenterY() > GameHeight)
		{
			this.setCenterY(0);
		}
		
	}
	
}

