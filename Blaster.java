import java.awt.Polygon;
import java.awt.Rectangle;

public class Blaster extends Polygon{

	// this is the board that the blaster particles can be in
	int GameWidth = GameBoard.boardWidth;
	int GameHeight = GameBoard.boardHeight;
	
	// location of the blaster particle
	private double centerX=0;
	private double centerY=0;
	
	// shape of the blaster particle
	public static int[] PolygonArrayX = {-2,2,2,-2,-2};
	public static int[] PolygonArrayY = {-2,-2,2,2,-2};
	
	// how big the blaster is
	private int blasterWidth = 4;
	private int blasterHeight = 4;
	
	// is the blaster on screen?
	public boolean onScreen = false;
	
	// the angle of the blaster movement
	private double blasterAngle = 0;
	
	// how fast the blaster is moving
	private double velocityX = 0;
	private double velocityY = 0;
	
	public Blaster(double centerX, double centerY, double angle)
	{
		super(PolygonArrayX, PolygonArrayY, 5);
		
		this.centerX = centerX;
		this.centerY = centerY;
		this.blasterAngle = angle;
		this.onScreen = true;
		
		this.setVelocityX(this.blasterMoveAngleX(blasterAngle)*10);
		this.setVelocityY(this.blasterMoveAngleY(blasterAngle)*10);
	}
	
	public double getCenterX() { return centerX; }
	public double getCenterY() { return centerY; }
	public void setCenterX(double centX) { this.centerX = centX; }
	public void setCenterY(double centY) { this.centerY = centY; }
	public void moveCenterX(double incriment) { this.centerX += incriment; }
	public void moveCenterY(double incriment) { this.centerY += incriment; }
	
	public void setVelocityX(double velX) {this.velocityX = velX;}
	public void setVelocityY(double velY) {this.velocityY = velY;}
	public double getVelocityX() { return velocityX; }
	public double getVelocityY() { return velocityY; }
	
	public double blasterMoveAngleX(double angle) 	{ return (double)(Math.cos(angle*Math.PI/180));}
	public double blasterMoveAngleY(double angle) 	{ return (double)(Math.sin(angle*Math.PI/180));}
	
	public int getWidth() {return blasterWidth; }
	public int getHeight() {return blasterHeight; }
	
	public void setBlasterAngle(double angle) {this.blasterAngle = angle;}
	public double getBlasterAngle() {return blasterAngle;}
	
	public Rectangle getBounds() 
	{
		return new Rectangle((int)getCenterX()-4 , (int)getCenterY()-4 ,getWidth(), getHeight() );
	}
	
	public void move() 
	{
		if (this.onScreen)
		{
			this.moveCenterX(this.getVelocityX());
			
			if (this.getCenterX() < 0 || this.getCenterX() > GameWidth)
			{
				this.onScreen = false;
			}
			
			this.moveCenterY(this.getVelocityY());
			
			if (this.getCenterY() < 0 || this.getCenterY() > GameHeight)
			{
				this.onScreen = false;
			}
		}
	}
}



