package sallat.gui;
import java.awt.BorderLayout;

import javax.swing.*;
public class GameWindow {
	private JFrame frame;
	private Timer timer;

	public GameWindow(int width, int height, String title) {
		frame = new JFrame(title);
		frame.setSize(width,height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public void setFPS(int fps){
		if(timer != null)
			timer.stop();
		timer = new Timer(1000/fps, e -> frame.repaint());
		timer.start();
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
