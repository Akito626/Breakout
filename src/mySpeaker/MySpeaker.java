package mySpeaker;

import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

public class MySpeaker {
	public static SoundWindow soundWindow;
	public static MySoundtrack st;
	
	private MySpeaker() {;}  //コンストラクタを呼べなくする
	
	public static void prepareSpeaker(int defaultSEvolume, int defaultBGMvolume) throws BasicPlayerException {
		st = new MySoundtrack();
		soundWindow = new SoundWindow();
		soundWindow.preparePanels();
		soundWindow.soundPanel.defaultSEvolume = defaultSEvolume;
		soundWindow.soundPanel.defaultBGMvolume = defaultBGMvolume;
		soundWindow.prepareComponents();
		soundWindow.setFrontScreenAndFocus(ScreenModeforSound.MAIN);
		soundWindow.setVisible(true);
	}
	
	public static void setDefaultSE(String key) {
		if(key == null || !(st.getSEs().containsKey(key))) {
			throw new IllegalArgumentException("setDefaultSEメソッドの引数が不正です");
		}
		soundWindow.soundPanel.defaultSEkey = key;
	}
	
	public static void playBGM(String key) {
		BasicPlayer p = soundWindow.soundPanel.playerOfBGM;
		if(key == null || !(st.getBGMs().containsKey(key))) {
			throw new IllegalArgumentException("playBGMメソッドの引数が不正です");
		}
		try {
			soundWindow.soundPanel.bgmLoopMonitorTimer.stop();
			p.stop();
			p.open(st.getBGMs().get(key));  //BGM指定
			soundWindow.soundPanel.currentBGMkey = key;
			soundWindow.soundPanel.bgmLoopMonitorTimer.start();
			p.play();  //再生
			p.setGain((double)soundWindow.soundPanel.sliderOfBGM.getValue() / 1000);  //音量調整;
		}catch (BasicPlayerException e) {
			System.out.println("playBGMメソッドでエラーが発生しています");
		}
	};
	
	public static void stopBGM(String key) {
		try {
			soundWindow.soundPanel.playerOfBGM.stop();
			soundWindow.soundPanel.bgmLoopMonitorTimer.stop();
		}catch (BasicPlayerException e) {
			System.out.println("stopBGMメソッドでエラーが発生しています");
		}
	};
	
	public static void playSE(String key) {
		BasicPlayer p = soundWindow.soundPanel.playerOfSE;
		if(key == null || !(st.getSEs().containsKey(key))) {
			throw new IllegalArgumentException("playSEメソッドの引数が不正です");
		}
		try {
			p.stop();
			p.open(st.getSEs().get(key));  //BGM指定
			p.play();  //再生
			p.setGain((double)soundWindow.soundPanel.sliderOfSE.getValue() / 1000);  //音量調整;
		}catch (BasicPlayerException e) {
			System.out.println("playSEメソッドでエラーが発生しています");
		}
	}
	
	public static void stopSE(String key) {
		try {
			soundWindow.soundPanel.playerOfSE.stop();
		}catch (BasicPlayerException e) {
			System.out.println("stopSEメソッドでエラーが発生しています");
		}
	}

}
