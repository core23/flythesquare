package de.core23.flythesquare;

import de.core23.flythesquare.component.Copter;
import de.core23.flythesquare.component.LevelBar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private LinkedList<LevelBar> bars = new LinkedList<LevelBar>();
	private Random rnd = new Random();
	private Timer tmrGame = new Timer(); 
	private Timer tmrDisco = new Timer();
	
	protected int barPadding= 10;
	protected int blockSize	= 50;
	protected int holeSize 	= 250;
	protected int barLeft  	= 0;
	protected int barWidth 	= 25;	
	protected int barOffset = 10;
	protected int barCount 	= 15;	
	
	private Copter copter;
	protected boolean start = false;
	protected boolean fly = false;

	protected int score = 0;
	protected int highscore = 0;
	
	private Color colBackground = Color.BLACK;
	private Color colForeground = Color.GREEN;
	private Color colCopter 	= Color.DARK_GRAY;
	
	public GamePanel() {		
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {		
				fly = true;
			}
			public void mouseReleased(MouseEvent arg0) {
				fly = false;
			}
			public void mouseClicked(MouseEvent arg0) {
				if (!start)
					startGame();
			}
		});
	}
	
	public void reset() {
		setTimer(false);
		
		bars.clear();

		barLeft  = 0;
		barCount = 15;
		
		score = 0;
		start = false;
		
		holeSize 	= 250;
		barPadding 	= 20;
		barWidth 	= 25;		
		barOffset 	= 10;
		blockSize	= 50;
		
		int count = (int) Math.ceil(getWidth() / (double)barWidth);		
		for (int i=0;i<=count;i++) 
			addBar();

		copter = new Copter(barWidth/2, getHeight()/3, barWidth*2, barWidth);
				
		repaint();
	}	
	
	private void endGame() {
		setTimer(false);
		fly = false;
		
		repaint();
		
		highscore = Math.max(score, highscore);
		
		JOptionPane.showMessageDialog(this, "Game Over", "Fly The Square", JOptionPane.ERROR_MESSAGE);
		
		reset();
	}
		
	public void startGame() {	
		setTimer(true);
	}
		
	private void addBar() {
		int parentTop 	= (bars.size()==0 ? (getHeight()-holeSize)/2 : ((LevelBar) bars.getLast()).getHoleTop());
		int shift 		= rnd.nextInt(barOffset)*(rnd.nextBoolean() ? -1 : 1);
		int tunnelTop	= Math.min(getHeight()-holeSize-30, Math.max(15, parentTop+shift));
		 		
		if (barCount-- < 0) {
			barCount = 10;
			
			bars.add( new LevelBar(tunnelTop, holeSize,  blockSize, tunnelTop+rnd.nextInt(holeSize+20-blockSize)-10) );
		} else {
			bars.add( new LevelBar(tunnelTop, holeSize) );
		}
	}

	public void setStyle(int style) {
		tmrDisco.cancel();
		tmrDisco = new Timer();
				
		switch (style) {
		case 0:				
			tmrDisco.schedule(new TimerTask() { 
				public void run() { 
					colBackground 	= Color.getHSBColor( rnd.nextFloat(), 1.0F, 1.0F );
					colForeground 	= Color.getHSBColor( rnd.nextFloat(), 1.0F, 1.0F );		
					colCopter 		= Color.getHSBColor( rnd.nextFloat(), 1.0F, 1.0F );		
				}
			}, 0, 1000);	
			break;
			
		case 2:
			colBackground 	= Color.WHITE;
			colForeground 	= Color.BLACK;
			colCopter 		= Color.DARK_GRAY;
			break;
			
		case 3:
			colBackground 	= Color.RED;
			colForeground 	= Color.YELLOW;
			colCopter 		= Color.ORANGE;			
			break;
			
		case 4:
			colBackground 	= Color.DARK_GRAY;
			colForeground 	= Color.LIGHT_GRAY;
			colCopter 		= Color.GRAY;			
			break;

		default:
			colBackground 	= Color.BLACK;
			colForeground 	= Color.GREEN;
			colCopter 		= Color.DARK_GRAY;		
			break;
		}
		
		repaint();
	}
	
	/*
	 * Timer
	 */
	private void tmrMoveCopter() {
		if (fly) {
			copter.fly(+3);
		} else {
			copter.fly(-4);
		}


		for (int i=0;i<bars.size();i++) {
			if (copter.getLeft()+copter.getWidth() < -barLeft+barWidth*i)
				break;

			if (copter.intersect(-barLeft+barWidth*i, 0, barWidth, bars.get(i).getHoleTop()) ||
				copter.intersect(-barLeft+barWidth*i, bars.get(i).getHoleTop()+bars.get(i).getHoleSize(), barWidth, getHeight()-bars.get(i).getHoleTop()+bars.get(i).getHoleSize()) ||
				copter.intersect(-barLeft+barWidth*i, bars.get(i).getBlockTop(), barWidth, bars.get(i).getBlockSize())) {
				endGame();
			}
		}		
	}
	
	private void tmrMoveBars() {
    	barLeft+=5;
    	
    	if (barLeft > barWidth) {
    		// Remove Left Bar
    		barLeft = 0;
    		bars.removeFirst();

    		// Add New Bar
			addBar(); 

			// Inc Score
	    	score+=10;
    	}
    	
		repaint();
	};	
	
	private void tmrIncShift() {
		barOffset = Math.min(barOffset+5, 60);
	}
	
	private void tmrDecHole() {
		holeSize = Math.max(holeSize-10, 200);		
	}
	
	private void setTimer(boolean status) {	
		start = status;	
		if (status) {			
			tmrGame.schedule(new TimerTask() { 
				public void run() { 
					tmrMoveBars();
				}
			}, 25, 25);			
			tmrGame.schedule(new TimerTask() { 
				public void run() { 
					tmrMoveCopter();
				}
			}, 25, 25);		
			tmrGame.schedule(new TimerTask() { 
				public void run() { 
					tmrIncShift();
				}
			}, 15000, 15000);	
			tmrGame.schedule(new TimerTask() { 
				public void run() { 
					tmrDecHole();
				}
			}, 30000, 30000);
		} else {
			tmrGame.cancel();
			tmrGame = new Timer();
		}
	}
		
	/*
	 * Repaint Method
	 */		
	public void paint(Graphics g) {
		// Background
		g.setColor(colBackground);
		g.fillRect(0, 0, getWidth(), getHeight());

		// Level
		g.setColor(colForeground);
		for (int i=0;i<bars.size();i++) {
			// Paint Top
			g.fillRect(-barLeft+barWidth*i, 0, barWidth, bars.get(i).getHoleTop());
			
			// Paint Block
			if (bars.get(i).getHoleSize()>0) 
				g.fillRect(-barLeft+barWidth*i, bars.get(i).getBlockTop(), barWidth, bars.get(i).getBlockSize());	
			
			// Paint Bottom
			g.fillRect(-barLeft+barWidth*i, bars.get(i).getHoleTop()+bars.get(i).getHoleSize(), barWidth, getHeight()-bars.get(i).getHoleTop()+bars.get(i).getHoleSize());
		}			

		// Copter
		g.setColor(colCopter);
		g.fillRect(copter.getLeft(), copter.getTop(), copter.getWidth(), copter.getHeight());	

		// Score
		g.setColor(colBackground);
		g.setFont(new Font("Helvetica", Font.BOLD,  14));
		Rectangle2D rect = g.getFont().getStringBounds(Integer.toString(score), g.getFontMetrics().getFontRenderContext());
		g.drawString("Score: ", 10, getHeight()-10);	
		g.drawString(Integer.toString(score), 130-(int) rect.getWidth(), getHeight()-10);
		
		// Highscore
		g.drawString("Highscore: ", getWidth()-150, getHeight()-10);		
		rect = g.getFont().getStringBounds(Integer.toString(highscore), g.getFontMetrics().getFontRenderContext());
		g.drawString(Integer.toString(highscore), (int) (this.getWidth() - rect.getWidth())-10,	getHeight()-10);
	}
}
