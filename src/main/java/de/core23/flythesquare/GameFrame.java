package de.core23.flythesquare;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.Rectangle;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private GamePanel gamePanel = null;
	private JMenuBar jMenuBar = null;
	private JMenu jMenuFile = null;
	private JMenu jMenuHelp = null;
	private JMenuItem jMenuItemAbout = null;
	private JMenuItem jMenuItemNewExit = null;

	public GameFrame() {
		super();
		initialize();
	}

	private void initialize() {
		this.setSize(600, 500);
		this.setResizable(false);
		this.setContentPane(getJContentPane());
		this.setTitle("Fly The Square");
		this.setJMenuBar(getJJMenuBar());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		getGamePanel().reset();

		this.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent e) {
				if (e.getKeyChar() == '1')
					getGamePanel().setStyle(1);
				if (e.getKeyChar() == '2')
					getGamePanel().setStyle(2);
				if (e.getKeyChar() == '3')
					getGamePanel().setStyle(3);
				if (e.getKeyChar() == '4')
					getGamePanel().setStyle(4);
				if (e.getKeyChar() == '0')
					getGamePanel().setStyle(0);
			}
		});
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridLayout(1, 1));
			jContentPane.add(getGamePanel(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes gamePanel
	 * 
	 * @return Game.GamePanel
	 */
	private GamePanel getGamePanel() {
		if (gamePanel == null) {
			gamePanel = new GamePanel();
			gamePanel.setBounds(new Rectangle(0, 0, getJContentPane().getWidth(), getJContentPane().getHeight()));
		}
		return gamePanel;
	}

	private JMenuBar getJJMenuBar() {
		if (jMenuBar == null) {
			jMenuBar = new JMenuBar();
			jMenuBar.add(getJMenuFile());
			jMenuBar.add(getJMenuHelp());
		}
		return jMenuBar;
	}

	private JMenu getJMenuFile() {
		if (jMenuFile == null) {
			jMenuFile = new JMenu();
			jMenuFile.setText("Datei");
			jMenuFile.add(getJMenuItemExit());
		}
		return jMenuFile;
	}

	private JMenu getJMenuHelp() {
		if (jMenuHelp == null) {
			jMenuHelp = new JMenu();
			jMenuHelp.setText("?");
			jMenuHelp.add(getJMenuItemAbout());
		}
		return jMenuHelp;
	}

	private JMenuItem getJMenuItemAbout() {
		if (jMenuItemAbout == null) {
			jMenuItemAbout = new JMenuItem();
			jMenuItemAbout.setText("Info");
			jMenuItemAbout.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JOptionPane.showMessageDialog(null, "Version 1.01\r\n\r\nCopyright 2009 Christian Gripp\r\n\r\nhttps://core23.de",
							"Fly The Square", JOptionPane.INFORMATION_MESSAGE);
				}
			});
		}
		return jMenuItemAbout;
	}

	private JMenuItem getJMenuItemExit() {
		if (jMenuItemNewExit == null) {
			jMenuItemNewExit = new JMenuItem();
			jMenuItemNewExit.setText("Ende");
			jMenuItemNewExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return jMenuItemNewExit;
	}
}
