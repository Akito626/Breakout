package mySpeaker;

import java.awt.Component;
import java.net.URL;
import java.util.HashMap;

public class MySoundtrack extends Component{
	private static final long sirialVersionUID = 1L;
	
	HashMap<String, URL> BGMs;
	HashMap<String, URL> SEs;
	
	public MySoundtrack() {
		BGMs = new HashMap<String, URL>();
		SEs = new HashMap<String, URL>();
	}
	
	public HashMap<String, URL>getBGMs(){
		return BGMs;
	}
	
	public HashMap<String, URL> getSEs(){
		return SEs;
	}
	
	public void addBGM(String key, String filename) {
		URL url = this.getClass().getClassLoader().getResource(filename);
		this.BGMs.put(key, url);
	}
	
	public void addSE(String key, String filename) {
		URL url = this.getClass().getClassLoader().getResource(filename);
		this.SEs.put(key, url);
	}
}
