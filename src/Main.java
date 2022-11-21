import javax.swing.UIManager;

import javazoom.jlgui.basicplayer.BasicPlayerException;
import mySpeaker.MySpeaker;

public class Main {
	static MainWindow mainWindow;
	static MySpeaker myspeaker;
	public static void main(String[] args) {
		UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);
		
		try {
			MySpeaker.prepareSpeaker(100, 40);
			MySpeaker.soundWindow.setVisible(false);
			MySpeaker.soundWindow.setLocation(1200, 150);
			settingAllSounds();
			MySpeaker.playBGM("タイム");
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
		MySpeaker.st.addBGM("タイム", "Timebend.mp3");
		MySpeaker.st.addBGM("終わり", "gameover.mp3");
		//SE
		MySpeaker.st.addSE("破壊", "break.mp3");
		MySpeaker.st.addSE("打つ", "strike.mp3");
		//デフォルトSE
		MySpeaker.setDefaultSE("破壊");
	}

}
