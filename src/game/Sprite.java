package game;

import java.io.Serializable;

import javafx.scene.image.Image;

public abstract class Sprite implements Serializable
{
	private static final long serialVersionUID = 9190247812115625938L;

	private String name;
	private Image image;
	private double x;
	private double y;
	private double width;
	private double height;
	private double faceAngle;
	
	private double originalX;
	private double originalY;
	private double originalWidth;
	private double originalHeight;
	private double originalFaceAngle;
	
	private boolean visible;
	private boolean animating;
	
	public Sprite(String name, Image image, double x, double y, double width, double height, double faceAngle)
	{
		this.name = name;
		this.image = image;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		originalX = x;
		originalY = y;
		originalWidth = width;
		originalHeight = height;
		originalFaceAngle = faceAngle;
		visible = true;
	}

	public double getOriginalX() {return originalX;}
	public void setOriginalX(double originalX) {this.originalX = originalX;}
	public double getOriginalY() {return originalY;}
	public void setOriginalY(double originalY) {this.originalY = originalY;}
	public double getOriginalWidth() {return originalWidth;}
	public void setOriginalWidth(double originalWidth) {this.originalWidth = originalWidth;}
	public double getOriginalHeight() {return originalHeight;}
	public void setOriginalHeight(double originalHeight) {this.originalHeight = originalHeight;}
	public double getOriginalFaceAngle() {return originalFaceAngle;}
	public void setOriginalFaceAngle(double originalFaceAngle) {this.originalFaceAngle = originalFaceAngle;}

	//getters and setters
	public double getX() {return x;}
	public double getY(){return y;}
	public double getWidth(){return width;}
	public double getHeight(){return height;}
	public void setX(double x){this.x = x;}
	public void setY(double y){this.y = y;}
	public void setWidth(double width){this.width = width;}
	public void setHeight(double height){this.height = height;}
	public double getFaceAngle(){return faceAngle;}	
	public void setFaceAngle(double faceAngle) {this.faceAngle = faceAngle;}
	public Image getImage() {return image;}
	public void setImage(Image image) {this.image = image;}
	public String getName(){return name;}
	public void setName(String name) {this.name = name;}
	public boolean isVisible() {return visible;}
	public void setVisible(boolean visible) {this.visible = visible;}
	public boolean isAnimating() {return animating;}
	public void setAnimating(boolean animating) {this.animating = animating;}

	public abstract Sprite getCopy();
	
	public void updateOriginalLocations()
	{
		originalX = x;
		originalY = y;
		originalWidth = width;
		originalHeight = height;
		originalFaceAngle = faceAngle;
	}
}
