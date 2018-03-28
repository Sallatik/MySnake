import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;
public class Test {

	JFrame frame;
	JTextField field;
	JLabel label;
	JPanel panel;
	public static void main(String[] args) {
		Test test = new Test();
		test.go();
	}
	
	public void go(){
		frame = new JFrame("Test");
		/*field =  new JTextField();
		field.addKeyListener(new Listener());
		frame.getContentPane().add(BorderLayout.SOUTH, field); */
		panel = new JPanel();
		panel.addKeyListener(new Listener());
		panel.setBackground(Color.BLUE);
		frame.getContentPane().add(BorderLayout.SOUTH, panel);
		label = new JLabel();
		frame.getContentPane().add(BorderLayout.NORTH, label);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(new Listener());
		frame.setSize(200,100);
		frame.setVisible(true);
	}
	
	private class Listener extends KeyAdapter {
		public void keyPressed(KeyEvent k) {
			label.setText("You pressed a key!" + k.getKeyCode());
		}
	}

}
