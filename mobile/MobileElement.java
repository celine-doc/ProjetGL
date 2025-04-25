package mobile;

import java.io.Serializable;
import java.util.ArrayList;

import map.Block;

public abstract class MobileElement implements Serializable{

	private String location;
	private ArrayList<String> listAction = new ArrayList<String>();
	private static final long serialVersionUID = 1L;
	private Block position;
	private boolean haveToMoove;
	private int actionTime=0;
	
	public int getActionTime() {
		return actionTime;
	}
	public void setActionTime(int actionTime) {
		this.actionTime = actionTime;
	}
	
	public boolean haveToMoove() {
		return haveToMoove;
	}

	public void setHaveToMoove(boolean haveToMoove) {
		this.haveToMoove = haveToMoove;
	}
	public boolean dontHaveAction() {
		if(listAction.isEmpty()) {
			return true;
		}else {
			return false;
		}
	}
	public void setLocation(String newLocation) {
		this.location = newLocation;
	}
	public String getLocation() {
		return location;
	}
	
	public void addAction(String newAction){
		listAction.add(newAction);
	}
	public void supFirstAction() {
		listAction.remove(0);
	}
	public String getAction() {
		return listAction.get(0);
		
	}
	public ArrayList<String> getListAction(){
		return listAction;
	}
	public Block getPosition() {
		return position;
	}
	
	public void setPosition(Block position) {
		this.position = position;
	}
	
}
