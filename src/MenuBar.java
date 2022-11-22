import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mySpeaker.SpeakerButton;

public class MenuBar extends JPanel{
	private static final long serialVersionUID = 1L;
	
	JButton homeButton;
	JLabel scoreLabel;
	JLabel missLabel;
	JLabel levelLabel;
	
	HomeButtonListener homeButtonListener;
	SpeakerButton speakerButton;
	
	int score = 0;  //スコアカウント	
	int level = 1;  //レベル
	String miss = "";  //ミスカウント
	
	
	public MenuBar() {
		this.setPreferredSize(new Dimension(100, 40));  //コンポーネントサイズ用
		this.setBackground(Color.red);
		this.setLayout(null);
	}
	
	public void prepareComponents() {
		//スコア
		scoreLabel = new JLabel();
		scoreLabel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 18));
		scoreLabel.setText("score : " + score);
		scoreLabel.setBounds(150, 5, 150, 30);
		
		//ミス回数
		missLabel = new JLabel();
		missLabel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 18));
		missLabel.setText("miss : " + miss);
		missLabel.setBounds(350, 5, 150, 30);
		
		//レベル
		levelLabel = new JLabel();
		levelLabel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 18));
		levelLabel.setText("Level : " + level);
		levelLabel.setBounds(550, 5, 150, 30);
		
		//ホームボタン
		homeButton = new JButton();
		homeButton.setBounds(5, 5, 80, 30);
		homeButton.setText("HOME");
		homeButton.setFocusable(false);
		homeButtonListener = new HomeButtonListener();
		homeButton.addActionListener(homeButtonListener);
		
		//スピーカーボタン
		speakerButton = new SpeakerButton();
		speakerButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("11_8bu_onpu.png")));
		speakerButton.setLocation(730, 5);
		
		this.add(homeButton);
		this.add(speakerButton);
		this.add(scoreLabel);
		this.add(missLabel);
		this.add(levelLabel);
	}
	
	public void updateText() {
		scoreLabel.setText("score : " + score);
		missLabel.setText("miss : " + miss);
		levelLabel.setText("Level : " + level);
	}
	
	private class HomeButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Main.mainWindow.gamePanel.backToTitleDialogue();
		}
	}
 }
