import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import mySpeaker.MySpeaker;
import mySpeaker.SpeakerButton;

public class TitlePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	//コンポーネント
	JLabel title;
	JLabel start;
	JLabel exit;
	JLabel select;
	JLabel message;
	Menu checkMenu = Menu.START;
	Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
	MyKeyListener myKeyListener;
	SpeakerButton speakerButton;
	JButton creditButton;
	
	public enum Menu{
		START,
		EXIT,
	}
	
	TitlePanel(){
		this.setLayout(null);
		this.setBackground(Color.cyan);
	}
	
	public void prepareComponents() {
		title = new JLabel();
		//ImageIcon titleLogo = new ImageIcon(getClass(), getClassLoader().getResource("title.png"));  //タイトル画面(600×300)
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setVerticalAlignment(SwingConstants.BOTTOM);
		title.setText("Created by 【AKITO】");
		title.setHorizontalTextPosition(JLabel.CENTER);
		title.setVerticalTextPosition(SwingConstants.BOTTOM);
		title.setBounds(90, 0, 600, 350);
		title.setBorder(border);
		
		//選択肢
		start = new JLabel();
		start.setText("START");
		start.setFont(new Font("MV boil", Font.BOLD, 40));
		start.setHorizontalTextPosition(JLabel.CENTER);
		start.setVerticalTextPosition(JLabel.BOTTOM);
		start.setBounds(330, 400, 150, 40);
		start.setBorder(border);
		
		exit = new JLabel();
		exit.setText("EXIT");
		exit.setFont(new Font("MV boil", Font.BOLD, 40));
		exit.setHorizontalTextPosition(JLabel.CENTER);
		exit.setVerticalTextPosition(JLabel.BOTTOM);
		exit.setBounds(350, 450, 110, 40);
		exit.setBorder(border);
		
		//選択アイコン
		select = new JLabel();
		//ImageIcon Icon02 = new ImageIcon(getClass(), getClassLoader().getResource(".png"));
		select.setBackground(Color.blue);
		select.setOpaque(true);
		select.setBounds(280, 400, 40, 40);
		select.setBorder(border);
		
		//説明
		message = new JLabel();
		message.setHorizontalAlignment(SwingConstants.CENTER);
		message.setText("選択：↑,↓　　決定：SPACE");
		message.setHorizontalTextPosition(JLabel.CENTER);
		message.setVerticalTextPosition(JLabel.CENTER);
		message.setBounds(249, 517, 300, 30);
		message.setBorder(border);
		
		//スピーカーボタン
		speakerButton = new SpeakerButton();
		speakerButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("11_8bu_onpu.png")));
		speakerButton.setLocation(730, 5);
		
		//クレジットボタン
		creditButton = new JButton();
		creditButton.setText("Credit");
		creditButton.setBounds(680, 500, 80, 40);
		
		this.setLayout(null);
		this.add(title);
		this.add(start);
		this.add(exit);
		this.add(select);
		this.add(message);
		this.add(speakerButton);
		this.add(creditButton);
		
		//リスナー
		myKeyListener = new MyKeyListener(this);
	}
	
	public void showRuleDialogue() {
		MySpeaker.playBGM("タイム");
		String str = "";
		JOptionPane.showOptionDialog(
			this,
			str,
			"Credit",
			JOptionPane.DEFAULT_OPTION,
			JOptionPane.PLAIN_MESSAGE,
			null,
			new Object[] {"閉じる"},
			"閉じる"
		);
	}
	
	public class MyKeyListener implements KeyListener{
		//貼り付け先
		TitlePanel panel;
		
		//コンストラクタ
		MyKeyListener(TitlePanel p){
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
				if(checkMenu == Menu.START) {
					select.setLocation(select.getX(), select.getY()+50);
					checkMenu = Menu.EXIT;
				}
				break;
			case KeyEvent.VK_UP:
				if(checkMenu == Menu.EXIT) {
					select.setLocation(select.getX(), select.getY()-50);
					checkMenu = Menu.START;
				}
				break;
			case KeyEvent.VK_SPACE:
				if(checkMenu == Menu.START) {
					Main.mainWindow.gamePanel.resetGame();
					Main.mainWindow.setFrontScreenAndFocus(ScreenMode.GAME);
					MySpeaker.stopBGM("タイム");
					Main.mainWindow.gamePanel.showRuleDialogue();
				} else if(checkMenu == Menu.EXIT) {
					System.exit(0);
				}
				break;
			}
		}
	}
}