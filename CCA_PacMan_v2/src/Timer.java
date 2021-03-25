
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Timer extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	private boolean isRunning = false;

	public static final int WIDTH = 30, HEIGHT = 80;

	private JLabel showcount;
	private JLabel showscore;
	
	public static int count;
	public static int score;

	private Thread thread;

	public Timer(String name) {
		
		JPanel countPanel = this.createCountPanel();
		JPanel scorePanel = this.createScorePanel();
		
		add(countPanel, BorderLayout.NORTH);
		add(scorePanel, BorderLayout.SOUTH);
		
		this.setSize(WIDTH, HEIGHT);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public JPanel createCountPanel() {
		showcount = new JLabel("Start !");
		showscore = new JLabel();
		JPanel panel = new JPanel();
		panel.add(showcount);
		panel.add(showscore);
		return panel;
	}
	
	public JPanel createScorePanel() {
		showscore = new JLabel("");
		JPanel panel = new JPanel();
		panel.add(showscore);
		return panel;
	}

	public synchronized void start() {
		if (isRunning)
			return;
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		// At here add in the data base insert operation
		if (!isRunning)
			return;
		isRunning = false;
	}

	public void run() {
		count = 60;
		score = 0;
		double timer = System.currentTimeMillis();

		while (isRunning) {
			
			if (System.currentTimeMillis() - timer >= 1000) {
				count--;
				showcount.setText(String.format("Time: %4d", count));
				timer += 1000;
			}
			
			showscore.setText(String.format("Score: %3d", score));
			
			if (count == 0) {
				Game.STATE = Game.TIMESUP;
			}
		}

		stop();
	}

}