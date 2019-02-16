package game;

import java.io.Serializable;

import javafx.scene.image.Image;

public class Sprite implements Serializable
{
	private static final long serialVersionUID = 9190247812115625938L;

	private String name;
	private String imgFileName;
	private Image image;
	private double x;
	private double y;
	private double width;
	private double height;
	private double faceAngle;
	
	private boolean visible;
	
	public Sprite(String name, String imgFileName, double x, double y, double width, double height, double faceAngle)
	{
		this.name = name;
		this.imgFileName = imgFileName;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		visible = true;
	}
	
	

	public void createImage()
	{
		image = new Image(imgFileName, width, height, false, true);
	}

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
	public String getName(){return name;}
	public void setName(String name) {this.name = name;}
	public boolean isVisible() {return visible;}
	public void setVisible(boolean visible) {this.visible = visible;}
}
