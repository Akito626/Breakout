import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import mySpeaker.MySpeaker;

public class FieldPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	
	MyBall myBall;
	Arrow arrow;
	Block block[][];
	JLabel timerLabel;
	
	//パドル
	JLabel myPaddle1;
	JLabel myPaddle2;
	JLabel myPaddle3;
	JLabel myPaddle4;
	
	int x1 = 375;
	int x2 = 375;
	int x3 = 10;
	int x4 = 765;
	int y1 = 10;
	int y2 = 500;
	int y3 = 230;
	int y4 = 230;
	
	//ブロックの設定
	int bWidth = 60;  //ブロックの大きさ(横)
	int bHeight = 30;  //ブロックの大きさ(縦)
	int vb = 5;  //ブロックの数(縦)
	int hb = 5;  //ブロックの数(横)
	int blockCount;	 //残っているブロックの数
	
	int cw;  //中心座標(横)
	int ch;  //中心座標(縦)   
	int sw;  //ブロックの起点(横)
	int sh;  //ブロックの起点(縦)
	
	int score = 0;
	int missCount = 0;
	String miss = "";
	
	boolean isStop = false;  //ボールが止まっているか
	
	int level = 3;  //ゲームの速度
	
	public FieldPanel() {
		this.setBackground(Color.WHITE);
		this.setLayout(null);
	}
	
	public void prepareComponents() {
		cw = this.getWidth() / 2;
		ch = this.getHeight() / 2;
		
		//パドルの生成
		x1 = cw - 25;
		myPaddle1 = new JLabel();
		myPaddle1.setBackground(Color.BLACK);
		myPaddle1.setOpaque(true);
		myPaddle1.setBounds(x1, y1, 50, 10);
		this.add(myPaddle1);
		
		x2 = cw - 25;
		myPaddle2 = new JLabel();
		myPaddle2.setBackground(Color.BLACK);
		myPaddle2.setOpaque(true);
		myPaddle2.setBounds(x2, y2, 50, 10);
		this.add(myPaddle2);
		
		y3 = ch - 25;
		myPaddle3 = new JLabel();
		myPaddle3.setBackground(Color.BLACK);
		myPaddle3.setOpaque(true);
		myPaddle3.setBounds(x3, y3, 10, 50);
		this.add(myPaddle3);
		
		y4 = ch - 25;
		myPaddle4 = new JLabel();
		myPaddle4.setBackground(Color.BLACK);
		myPaddle4.setOpaque(true);
		myPaddle4.setBounds(x4, y4, 10, 50);
		this.add(myPaddle4);
		
		//ボールの生成
		myBall = new MyBall();
		BallActionListener ballListener = new BallActionListener(myBall);
		myBall.timer = new Timer(10, ballListener);
		this.add(myBall);
		myBall.timer.stop();
		resetBall();
		
		sw = cw - bWidth * vb / 2;
		sh = ch - bHeight* hb / 2;
		generateBlocks();
		
		//カウントダウンラベル
		timerLabel = new JLabel();
		timerLabel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 50));
		timerLabel.setBounds(cw, 50, 100, 100);
		this.add(timerLabel);
	}
	
	public void resetBall() {
		do {
			myBall.x = new java.util.Random().nextInt(this.getWidth());
			myBall.y = new java.util.Random().nextInt(this.getHeight());
		}while(((myBall.x - 5 < 50 || myBall.x + 5 > this.getWidth() - 50)  //ウィンドウの端に出ないように
				|| (myBall.y - 5 < 50 || myBall.y + 5 > this.getHeight() - 50))
				|| (myBall.x + 10> sw && myBall.x - 10 < cw + (bWidth * vb / 2)   //ブロックの上に出ないように
				&& myBall.y + 10 > sh && myBall.y - 10 < ch + (bHeight* hb / 2)));
		
		do {
			myBall.xVelocity = (new java.util.Random().nextInt(3) - 1);  //-1か1
			myBall.yVelocity = (new java.util.Random().nextInt(3) - 1);  //-1か1
		}while(myBall.xVelocity == 0 || myBall.yVelocity == 0);
		myBall.setLocation(myBall.x, myBall.y);
		
		//ボールの進行方向の表示
		arrow = new Arrow(myBall);
		this.add(arrow);
		arrow.setVisible(true);
		arrow.repaint();
	}
	
	public void generateBlocks() {
		block = new Block[vb][hb];
		blockCount = vb * hb;
		
		int i;
		int j;
		
		for(i = 0; i < vb; i++) {
			for(j = 0; j < hb; j++) {
				block[i][j] = new Block(sw +(i)*bWidth, sh + (j)*bHeight, bWidth, bHeight);
				this.add(block[i][j]);
			}
		}
	}
	
	//ブロックの再配置
	public void setNextBlocks() {
		for(int i = 0; i < vb; i++) {
			for(int j = 0; j < hb; j++) {
				block[i][j].isVisible = true;
				block[i][j].setVisible(true);
				blockCount = vb * hb;
			}
		}
		level++;
	}
	
	private class BallActionListener implements ActionListener{
		private MyBall ball;
		
		public BallActionListener(MyBall b) {
			ball = b;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			for(int i = 0; i < (double)level / 2; i++) {
			
				//左右のパドルで反射
				if((ball.x + 5 == x4 && ball.y > y4 && ball.y < y4 + 50)
						|| (ball.x == x3 + 10 && ball.y > y3 && ball.y < y3 + 50)) {
					ball.xVelocity = ball.xVelocity *(-1);
					MySpeaker.playSE("打つ");
				}
			
				//上下のパドルで反射
				if((ball.y + 5 == y2 && ball.x > x2 && ball.x < x2 + 50)
						|| (ball.y == y1 + 10 && ball.x > x1 && ball.x < x1 + 50)) {
					ball.yVelocity = ball.yVelocity *(-1);
					MySpeaker.playSE("打つ");
				}
			
				//ミスのカウント
				if(ball.x > Main.mainWindow.gamePanel.getWidth() - ball.getWidth() || ball.x < 0) {
					missCount++;
					miss += "×";
					ball.timer.stop();
					isStop = true;
					resetBall();
				}
				if(ball.y > Main.mainWindow.gamePanel.getHeight() - ball.getHeight() - 40|| ball.y < 0) {
					missCount++;
					miss += "×";
					ball.timer.stop();
					isStop = true;
					resetBall();
				}
			
				//ボールの移動
				ball.x = ball.x + ball.xVelocity;
				ball.y = ball.y + ball.yVelocity;
				ball.setLocation(ball.x, ball.y);
				ball.repaint();
				
				//ブロックの破壊処理
				for(int j = 0; j < vb; j++) {
					for(int k = 0; k < hb; k++) {
						if(block[j][k].isVisible == true) {		//ある時は処理を行う
							block[j][k].collision(myBall);
							if(block[j][k].isVisible == false) {		//消えたらスコアを追加
								score += block[j][k].score * level;
								blockCount--;
								MySpeaker.playSE("破壊");
							}
						}
					}
				}
			}
		}
	}
}
