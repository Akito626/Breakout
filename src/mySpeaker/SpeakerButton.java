package mySpeaker;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class SpeakerButton extends JButton{
	private static final long serialVersionUID = 1L;
	
	public SpeakerButton() {
		this.setFocusable(false);
		this.setPreferredSize(new Dimension(30, 30));
		this.setSize(this.getPreferredSize());
		//ImageIcon soundIcon = new ImageIcon("11_8bu_onpu.png");
		//this.setIcon(soundIcon);
		SoundButtonListener soundButtonListener = new SoundButtonListener();
		this.addActionListener(soundButtonListener);
	}
	
	class SoundButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(MySpeaker.soundWindow.isVisible() == true) {
				MySpeaker.soundWindow.setVisible(false);
			}else {
				MySpeaker.soundWindow.setVisible(true);
			}
		}
	}
}
