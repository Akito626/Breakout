import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class PlayerPaddle extends JPanel{
	private static final long serialVersion = 1L;
	
	int x1 = 375;
	int x2 = 375;
	int x3 = 10;
	int x4 = 770;
	int y1 = 10;
	int y2 = 500;
	int y3 = 230;
	int y4 = 230;
	
	public PlayerPaddle() {
		this.setBounds(0, 0, 400, 200);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		System.out.println("書きました");
		g.setColor(Color.BLACK);
		g.fillRect(x1, y1, 50, 10);
		g.fillRect(x2, y2, 50, 10);
		g.fillRect(x3, y3, 10, 50);
		g.fillRect(x4, y4, 10, 50);
	}
}
