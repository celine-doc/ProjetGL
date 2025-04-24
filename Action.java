package engine;

import map.Block;

public class Action {
	private String room;
	private Block position;
	private String name;
	private int proba;
	private String ecritJournal;
	private int timeAction;
	public Action(String room, String name, Block position, int proba, String ecritJournal, int timeAction) {
		this.room = room;
		this.name = name;
		this.position = position;
		this.proba = proba;
		this.ecritJournal = ecritJournal;
		this.timeAction=timeAction;
	}
	
	public int getTimeAction() {
		return timeAction;
	}
	public void setTimeAction(int timeAction) {
		this.timeAction = timeAction;
	}
	
	public int getProba() {
		return proba;
	}
	public void setProba(int proba) {
		this.proba = proba;
	}
	public String getEcritJournal() {
		return ecritJournal;
	}
	public void setEcritJournal(String ecritJournal) {
		this.ecritJournal = ecritJournal;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public void setPosition(Block position) {
		this.position = position;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRoom() {
		return room;
	}
	public Block getPosition() {
		return position;
	}
	public String getName() {
		return name;
	}
}

