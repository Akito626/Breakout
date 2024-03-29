import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import mySpeaker.MySpeaker;

public class GamePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	BorderLayout layout = new BorderLayout();
	
	MenuBar menuBar;
	FieldPanel fieldPanel;
	PlayerPaddle playerPaddle;
	
	MyKeyListener myKeyListener;
	MyActionListener myActionListener;
	Timer timer;  //画面の監視用
	Timer countdowntimer;  //一時停止用
	
	int pv = 10;  //パドルの速度
	boolean permissR = true;
	
	GamePanel(){
		this.setLayout(layout);
		this.setBackground(Color.yellow);
		
		menuBar = new MenuBar();
		fieldPanel = new FieldPanel();
		this.add(menuBar, BorderLayout.NORTH);
		this.add(fieldPanel, BorderLayout.CENTER);
	}
	
	public void prepareComponents() {
		menuBar.prepareComponents();
		fieldPanel.prepareComponents();
		
		myKeyListener = new MyKeyListener(this);
		myActionListener = new MyActionListener();
		timer = new Timer(10, myActionListener);
		timer.stop();
		
		countdowntimer = new Timer(1000, myActionListener);
		countdowntimer.stop();
		
		loadScore();
	}
	
	public void resetGame() {
		this.fieldPanel.removeAll();
		this.fieldPanel.prepareComponents();
		menuBar.score = 0;
		menuBar.miss = "";
		fieldPanel.score  = 0;
		fieldPanel.missCount = 0;
		fieldPanel.miss = "";
		permissR = true;
		fieldPanel.isStop = false;
		menuBar.updateText();
		loadScore();
	}
	
	public void showRuleDialogue() {
		MySpeaker.playBGM("タイム");
		timer.stop();
		fieldPanel.myBall.timer.stop();
		String str = "・十字キーでパドルを操作できます。ボールを打ち返して"
				+ "　ブロックを破壊することでスコアが増えます。"
				+ "\n・全てのブロックを破壊するとブロックが再配置されます。"
				+ "\n・3回ミスをするとゲームオーバー"
				+ "\n・Rキーでボールの位置をリセットできます"
				+ "\n※一度使うとしばらく使えません";
		JOptionPane.showOptionDialog(
			this,
			str,
			"ルール説明",
			JOptionPane.DEFAULT_OPTION,
			JOptionPane.PLAIN_MESSAGE,
			null,
			new Object[] {"スタート"},
			"スタート"
		);
		timer.start();
		countdowntimer.start();
		fieldPanel.timerLabel.setText("3");
	}
	
	public void backToTitleDialogue() {
		this.fieldPanel.myBall.timer.stop();
		timer.stop();
		countdowntimer.stop();
		
		String str = "タイトル画面に戻りますか?";
		int responce = JOptionPane.showOptionDialog(
			this,
			str,
			"HOME",
			JOptionPane.YES_NO_OPTION,
			JOptionPane.PLAIN_MESSAGE,
			null,
			null,
			null
		);
		
		if(responce == JOptionPane.YES_OPTION) {
			fieldPanel.myBall.timer.stop();
			countdowntimer.stop();
			Main.mainWindow.layout.show(Main.mainWindow.getContentPane(), "titlePanel");
			Main.mainWindow.setFrontScreenAndFocus(ScreenMode.TITLE);
		}else if(responce == JOptionPane.NO_OPTION) {
			timer.start();
			countdowntimer.start();
		}else if(responce == JOptionPane.CLOSED_OPTION) {
			timer.start();
			countdowntimer.start();
		}
	}
	
	public void showResultDialogue(int point) {
		MySpeaker.stopBGM("タイム");
		MySpeaker.playSE("終わり");
		timer.stop();
		fieldPanel.isStop = false;
		fieldPanel.myBall.timer.stop();
		String str = "		ゲーム終了！	得点:" + point + "点";
		JOptionPane.showOptionDialog(
			this,
			str,
			"result",
			JOptionPane.DEFAULT_OPTION,
			JOptionPane.PLAIN_MESSAGE,
			null,
			null,
			0
		);
		MySpeaker.stopSE("終わり");
		MySpeaker.playBGM("タイム");
	}
	
	public void saveFiledDialogue() {
		String str = "スコアのセーブに失敗しました";
		JOptionPane.showOptionDialog(
			this,
			str,
			"result",
			JOptionPane.DEFAULT_OPTION,
			JOptionPane.PLAIN_MESSAGE,
			null,
			null,
			0
		);
	}
	
	public void loadScore() {
		Path p = Paths.get("./save.txt");
		
		try {
			String str = Files.readString(p);
			menuBar.highscore = Integer.parseInt(str);
			menuBar.updateText();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveScore(){
		String str = String.valueOf(menuBar.score);
		
		try {
			PrintWriter pw = new PrintWriter("./save.txt");
			pw.write(str);
			pw.close();
		}catch (IOException e) {
			e.printStackTrace();
			saveFiledDialogue();
		}
	}
	
	private class MyKeyListener implements KeyListener{
		JPanel panel;
		
		MyKeyListener(JPanel p){
			super();
			panel = p;
			panel.addKeyListener(this);
		}
		
		@Override
		public void keyTyped(KeyEvent e) {
			
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			
		}
		
		@Override
		public void keyPressed(KeyEvent e) {			
			switch(e.getKeyCode()) {
			case KeyEvent.VK_DOWN:
				if(fieldPanel.y3 + 50 < 525) {
					for(int i = 0; i < pv * fieldPanel.level; i++) {
						fieldPanel.y3 += 1;
						fieldPanel.y4 += 1;
						fieldPanel.myPaddle3.setLocation(fieldPanel.x3, fieldPanel.y3);
						fieldPanel.myPaddle4.setLocation(fieldPanel.x4, fieldPanel.y4);
					}
				}
				break;
			case KeyEvent.VK_UP:
				if(fieldPanel.y3 > 0) {
					for(int i = 0; i < pv * fieldPanel.level; i++) {
						fieldPanel.y3 -= 1;
						fieldPanel.y4 -= 1;
						fieldPanel.myPaddle3.setLocation(fieldPanel.x3, fieldPanel.y3);
						fieldPanel.myPaddle4.setLocation(fieldPanel.x4, fieldPanel.y4);
					}
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(fieldPanel.x1 + 50 < 790) {
					for(int i = 0; i < pv * fieldPanel.level; i++) {
						fieldPanel.x1 += 1;
						fieldPanel.x2 += 1;
						fieldPanel.myPaddle1.setLocation(fieldPanel.x1, fieldPanel.y1);
						fieldPanel.myPaddle2.setLocation(fieldPanel.x2, fieldPanel.y2);
					}
				}
				break;
			case KeyEvent.VK_LEFT:
				if(fieldPanel.x1 > 0) {
					for(int i = 0; i < pv * fieldPanel.level; i++) {
						fieldPanel.x1 -= 1;
						fieldPanel.x2 -= 1;
						fieldPanel.myPaddle1.setLocation(fieldPanel.x1, fieldPanel.y1);
						fieldPanel.myPaddle2.setLocation(fieldPanel.x2, fieldPanel.y2);
					}
				}
				break;
			case KeyEvent.VK_R:
				if(permissR) {
					permissR = false;
					fieldPanel.arrow.setVisible(false);
					countdowntimer.start();
					fieldPanel.resetBall();
					fieldPanel.myBall.timer.stop();
					fieldPanel.timerLabel.setText("3");
				}
				break;
			case KeyEvent.VK_H:
				fieldPanel.myBall.timer.stop();
				countdowntimer.stop();
				Main.mainWindow.gamePanel.backToTitleDialogue();
				break;	
			}
			
		}
	}
	
	private class MyActionListener implements ActionListener{
		private int count = 3;
		private int permisscount = 1000;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == timer) {
				menuBar.score = fieldPanel.score;
				menuBar.miss = fieldPanel.miss;
				menuBar.level = fieldPanel.level;
				if(menuBar.score > menuBar.highscore) {
					menuBar.highscore = menuBar.score;
				}
				menuBar.updateText();
				if(fieldPanel.missCount == 3) {
					saveScore();
					Main.mainWindow.gamePanel.showResultDialogue(menuBar.score);
					Main.mainWindow.setFrontScreenAndFocus(ScreenMode.TITLE);
				}
				
				//残りのブロックのカウント
				if(fieldPanel.blockCount == 0) {
					fieldPanel.setNextBlocks();
				}
				
				//ボールが止まっているかの確認
				if(fieldPanel.isStop) {
					fieldPanel.isStop = false;
					countdowntimer.start();
					fieldPanel.timerLabel.setText("3");
				}
				
				//再度Rを押せるまでのカウント
				if(permissR == false) {
					permisscount--;
					if(permisscount == 0) {
						permissR = true;
						permisscount = 1000;
					}
				}
				
			}else if(e.getSource() == countdowntimer) {
				if(count > 0) {
					fieldPanel.timerLabel.setText(String.valueOf(count));
				}else if(count == 0) {  //3秒経つと再開
					fieldPanel.timerLabel.setText("");
					fieldPanel.myBall.timer.start();
					count = 3;
					countdowntimer.stop();
					fieldPanel.arrow.setVisible(false);
				}
				count--;
			}
		}
	}
}
