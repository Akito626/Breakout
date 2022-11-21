import javax.swing.UIManager;

import javazoom.jlgui.basicplayer.BasicPlayerException;
import mySpeaker.MySpeaker;

public class Main {
	static MainWindow mainWindow;
	static MySpeaker myspeaker;
	public static void main(String[] args) {
		UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);
		
		try {
			MySpeaker.prepareSpeaker(50, 0);
			MySpeaker.soundWindow.setVisible(false);
			MySpeaker.soundWindow.setLocation(1200, 150);
			settingAllSounds();
			MySpeaker.playBGM("ネコ");
		}catch(BasicPlayerException e) {
			e.printStackTrace();
		}
		
		mainWindow = new MainWindow();
		mainWindow.preparePanels();
		mainWindow.prepareComponents();
		mainWindow.setFrontScreenAndFocus(ScreenMode.TITLE);
		mainWindow.setVisible(true);
	}
	
	public static void settingAllSounds() {
		//曲
		MySpeaker.st.addBGM("戦闘", "battle.mp3");
		MySpeaker.st.addBGM("ネコ", "Cat_life.mp3");
		//SE
		MySpeaker.st.addSE("シャキーン", "syakeen.mp3");
		MySpeaker.st.addSE("爆発", "explode.mp3");
		MySpeaker.st.addSE("チーン", "cheeen.mp3");
		MySpeaker.st.addSE("キラキラ", "shining.mp3");
		//デフォルトSE
		MySpeaker.setDefaultSE("シャキーン");
	}

}
