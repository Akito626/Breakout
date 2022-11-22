import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Arrow extends JPanel{
	private int direction;  //左上：0,左下：1,右上：2,右下：3
	
	public Arrow(MyBall ball) {
		this.setOpaque(false);  //背景を透明に
		
		//ボールの向きから位置を設定
		if(ball.xVelocity == -1 && ball.yVelocity == -1) {
			this.setBounds(ball.x - 22, ball.y - 22, 21, 21);
			direction = 0;
		}else if(ball.xVelocity == -1 && ball.yVelocity == 1) {
			this.setBounds(ball.x - 22, ball.y + 12, 21, 21);
			direction = 1;
		}else if(ball.xVelocity == 1 && ball.yVelocity == -1) {
			this.setBounds(ball.x + 12, ball.y - 22, 21, 21);
			direction = 2;
		}else if(ball.xVelocity == 1 && ball.yVelocity == 1) {
			this.setBounds(ball.x + 12, ball.y + 12, 21, 21);
			direction = 3;
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		switch(direction){
		case 0:
			g.drawLine(0, 0, 20, 20);
			g.drawLine(0, 0, 0, 5);
			g.drawLine(0, 0, 5, 0);
			break;
		case 1:
			g.drawLine(0, 20, 20, 0);
			g.drawLine(0, 20, 0, 15);
			g.drawLine(0, 20, 5, 20);
			break;
		case 2:
			g.drawLine(0, 20, 20, 0);
			g.drawLine(20, 0, 20, 5);
			g.drawLine(20, 0, 15, 0);
			break;
		case 3:
			g.drawLine(0, 0, 20, 20);
			g.drawLine(20, 20, 20, 15);
			g.drawLine(20, 20, 15, 20);
			break;
		}
	}
}
