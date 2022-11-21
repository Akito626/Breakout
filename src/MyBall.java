

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.Timer;

public class MyBall extends JPanel{
	private static final long serialVersion = 1L;
	
	Timer timer = null;
	
	int x = 400;
	int y = 450;
	int xVelocity = 1;
	int yVelocity = -1;
	
	public MyBall(){
		this.setBounds(x, y, 10, 10);
		this.setOpaque(false);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillOval(0, 0, 10, 10);
	}
}
