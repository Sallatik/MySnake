package sallat.gui;
import java.awt.BorderLayout;

import javax.swing.*;
public class GameWindow {
	private JFrame frame;
	private Updater updater;
	public GameWindow(int width, int height, String title) {
		frame = new JFrame(title);
		frame.setSize(width,height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public void setUpdater(Updater updater){
		updater.setFrame(frame);
		if (this.updater != null) 
			this.updater.stop();
		
		this.updater = updater;
		Thread updaterThread = new Thread(updater);
		updaterThread.start();
		
	}
	
	public void setJPanel(JPanel panel) {
		java.awt.Container contentPane = frame.getContentPane();
		contentPane.removeAll();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setFocusable(true);
		frame.revalidate();
		frame.repaint();
		panel.requestFocusInWindow();
	}
	
}
