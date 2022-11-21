package mySpeaker;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import javazoom.jlgui.basicplayer.BasicPlayerException;

public class SoundWindow extends JFrame{
	private static final long serialVersionUID = 1L;
	
	ScreenModeforSound screenMode = ScreenModeforSound.MAIN;
	
	final int WIDTH = 220;
	final int HEIGHT = 220;
	
	CardLayout layout = new CardLayout();
	
	//コンポーネント
	SoundPanel soundPanel;
	
	SoundWindow(){
		this.setTitle("sound");
		//ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(""));
		//this.setIconImage(icon.getImage());
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setResizable(false);
		this.getContentPane().setBackground(null);
		this.setLayout(layout);
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.pack();
		this.setLocationRelativeTo(null);
	}
	
	public void preparePanels() {
		//パネル準備
		soundPanel = new SoundPanel();
		this.add(soundPanel, "音量調節画面");
		this.pack();
	}
	
	public void prepareComponents() throws BasicPlayerException{
		soundPanel.prepareComponents();
	}
	
	public void setFrontScreenAndFocus(ScreenModeforSound s) {
		screenMode = s;
		
		switch(screenMode) {
		case MAIN:
			layout.show(this.getContentPane(), "音量調節画面");
			soundPanel.requestFocus();
			break;
		}
	}
}
