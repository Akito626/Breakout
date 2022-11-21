import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Block extends JPanel{
	static final long serialVersionUID = 1L;
	
	int bx;
	int by;
	int bw;  //横幅
	int bh;  //縦幅
	int score = 10;
	boolean isVisible = true;
	
	public Block() {
		
	}
	
	public Block(int x, int y, int w, int h) {
		bx = x;
		by = y;
		bw = w;
		bh = h;
		this.setBackground(Color.WHITE);
		this.setBounds(bx, by, bw+1, bh+1);
	}
	
	public void collision(MyBall ball) {
		if(isVisible == true) {
			if(ball.x > bx && ball.x < bx + bw 
					&& ((ball.y + 5 == by && ball.yVelocity > 0) || (ball.y - 5 == by + bh && ball.yVelocity < 0))){
				ball.yVelocity = ball.yVelocity *(-1); 
				isVisible = false;
				setVisible(isVisible);
			}
			if(ball.y > by && ball.y < by + bh 
					&& ((ball.x + 5 == bx && ball.xVelocity > 0) || (ball.x - 5 == bx + bw && ball.xVelocity < 0))) {
				ball.xVelocity = ball.xVelocity *(-1); 
				isVisible = false;
				setVisible(isVisible);
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, bw, bh);
	}
}
