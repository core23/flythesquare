package de.core23.flythesquare.component;

public class Copter {
	private int top = 0;
	private int left = 30;
	private int width = 40;
	private int height= 20;

	public Copter(int top) {
		this.top = top;
	}
	
	public Copter(int left, int top, int width, int height) {
		this.left 	= left;
		this.top 	= top;
		this.width 	= width;
		this.height = height;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public int getLeft() {
		return left;
	}

	public int getTop() {
		return top;
	}

	public void fly(int top) {
		this.top-= top;
	}
	
	public boolean intersect(int left, int top, int width, int height) {
		if (width==0 || height==0)
			return false;
		
		return (! ( this.left > left+width
				|| this.left+this.width < left
				|| this.top > top+height
				|| this.top+this.height < top
				) );
	}
}
