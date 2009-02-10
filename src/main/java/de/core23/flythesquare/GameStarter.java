package de.core23.flythesquare;

import javax.swing.SwingUtilities;

public class GameStarter {
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new GameFrame();				
			}
		});
	}
}
