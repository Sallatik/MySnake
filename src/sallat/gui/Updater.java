package sallat.gui;
import javax.swing.JFrame;
public class Updater implements Runnable{
	private long delay;
	private JFrame frame;
	private boolean stop;
	
	public void stop() {
		stop = true;
	}
	public Updater(int fps) {
		delay = 1000 / fps;
		stop = false;
	}
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	public void run(){
		while(!stop) {
			try {
				Thread.sleep(delay);
			} catch (Exception ex) {
				ex.printStackTrace();
			} 
			if (frame != null)
			frame.repaint();
		}
	}
}
