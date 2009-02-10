package de.core23.flythesquare.component;

public class LevelBar {
	private int holeSize 	= 250;
	private int holeTop		= 10;
	private int blockSize	= 0;
	private int blockTop 	= 0;

	public LevelBar(int holeTop, int holeSize) {
		this.holeSize	= holeSize;
		this.holeTop 	= holeTop;
	}
	
	public LevelBar(int holeTop, int holeSize, int blockSize, int blockTop) {
		this.holeSize	= holeSize;
		this.holeTop 	= holeTop;
		this.blockSize	= blockSize;
		this.blockTop	= blockTop;
	}
	
	public int getBlockSize() {
		return blockSize;
	}
	public int getBlockTop() {
		return blockTop;
	}

	public int getHoleSize() {
		return holeSize;
	}
	
	public int getHoleTop() {
		return holeTop;
	}
}
