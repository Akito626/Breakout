package mySpeaker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

public class SoundPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	//起動時の音量
	int defaultBGMvolume = 50;
	int defaultSEvolume = 50;
	
	//SE用
	final String SE = "SE";
	MySlider sliderOfSE;
	JLabel labelOfSE;
	SEChangeListener ChangeListenerOfSE;
	BasicPlayer playerOfSE;
	
	//BGM用
	final String BGM = "BGM";
	MySlider sliderOfBGM;
	JLabel labelOfBGM;
	BGMChangeListener ChangeListenerOfBGM;
	BasicPlayer playerOfBGM;
	
	//音楽のキーを保持
	String currentBGMkey;  //ループ用
	String defaultSEkey;  //音量調節用
	
	//ループ用
	Timer bgmLoopMonitorTimer;
	
	SoundPanel(){
		this.setLayout(null);
		this.setBackground(null); //背景色
		
		//BasicPlayer準備
		playerOfSE = new BasicPlayer();
		playerOfBGM = new BasicPlayer();
	}
	
	public void prepareComponents() throws BasicPlayerException{
		//SE用
		sliderOfSE = new MySlider(0, 100, defaultSEvolume);
		sliderOfSE.setLocation(10, 50);
		labelOfSE = new JLabel();
		labelOfSE.setBounds(35, 20, 60, 10);
		labelOfSE.setText(SE + ":" + sliderOfSE.getValue());
		ChangeListenerOfSE = new SEChangeListener(SE, sliderOfSE, labelOfSE, playerOfSE);
		sliderOfSE.addChangeListener(ChangeListenerOfSE);
		
		//BGM用
		sliderOfBGM = new MySlider(0, 100, defaultBGMvolume);
		sliderOfBGM.setLocation(100, 50);
		labelOfBGM = new JLabel();
		labelOfBGM.setBounds(125, 20, 60, 10);
		labelOfBGM.setText(BGM + ":" + sliderOfBGM.getValue());
		ChangeListenerOfBGM = new BGMChangeListener(BGM, sliderOfBGM, labelOfBGM, playerOfBGM);
		sliderOfBGM.addChangeListener(ChangeListenerOfBGM);
		
		this.add(sliderOfSE);
		this.add(labelOfSE);
		this.add(sliderOfBGM);
		this.add(labelOfBGM);
		
		bgmLoopMonitorTimer = new Timer(100, new BGMLoopMonitor());
	}
	
	class MySlider extends JSlider{
		private static final long serialVersionUID = 1L;
		
		MySlider(int i, int j, int k){
			super(i, j, k);
			this.setSize(100, 100);
			this.setMinorTickSpacing(10);
			this.setMajorTickSpacing(25);
			this.setPaintLabels(true);
			this.setPaintTicks(true);
			this.setOrientation(SwingConstants.VERTICAL);
		}
	}
	
	class BGMChangeListener implements ChangeListener{
		String name;
		MySlider slider;
		JLabel label;
		BasicPlayer player;
		BGMChangeListener(String n, MySlider s, JLabel l, BasicPlayer p){
			name = n;
			slider = s;
			label = l;
			player = p;
		}
		
		@Override
		public void stateChanged(ChangeEvent e) {
			label.setText(name + ":" + slider.getValue());
			try {
				player.setGain((double) slider.getValue()/1000);
			} catch (BasicPlayerException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	class SEChangeListener extends BGMChangeListener{
		SEChangeListener(String n, MySlider s, JLabel l, BasicPlayer p){
			super(n, s, l, p);
		}
		
		@Override
		public void stateChanged(ChangeEvent e) {
			label.setText(name + ":" + slider.getValue());
			//マウスを話したときにデフォルトの音が出る
			/*try {
				if(!slider.getValueIsAdjusting()) {MySpeaker.playSE(defaultSEkey);}
				player.setGain((double) slider.getValue()/1000);
			} catch(BasicPlayerException e1){e1.printStackTrace();}*/
		}
	}
	
	class BGMLoopMonitor implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(playerOfBGM.getStatus() == BasicPlayer.STOPPED) {
				MySpeaker.playBGM(currentBGMkey);
			}
		}
	}
}
