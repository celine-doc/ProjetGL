package mobile;

import java.util.ArrayList;
import map.Block;

public class MobileElement {
    protected ArrayList<String> listAction = new ArrayList<>();
    protected Block position;
    protected String location;
    protected int actionTime;
    protected long actionStartTime; // Temps de début de l'action
    private int sizeImage;

	public void supAllAction() {
    	while(!dontHaveAction()) {
    		supFirstAction();
    	}
    }
    public int getSizeImage() {
		return sizeImage;
	}

	public void setSizeImage(int sizeImage) {
		this.sizeImage = sizeImage;
	}

	public String getAction() {
        if (listAction.isEmpty()) {
            return null;
        }
        return listAction.get(0);
    }

    public boolean dontHaveAction() {
        return listAction.isEmpty();
    }

    public void addAction(String action) {
        listAction.add(action);
        actionStartTime = System.currentTimeMillis(); // Enregistrer le temps de début
    }

    public void supFirstAction() {
        if (!listAction.isEmpty()) {
            listAction.remove(0);
        }
    }

    public Block getPosition() {
        return position;
    }

    public void setPosition(Block position) {
        this.position = position;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getActionTime() {
        return actionTime;
    }

    public void setActionTime(int actionTime) {
        this.actionTime = actionTime;
    }

    // Vérifier si l'action a dépassé la durée limite (en millisecondes)
    public boolean isActionExpired(long duration) {
        return System.currentTimeMillis() - actionStartTime > duration;
    }

	public MobileElement getTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setActionAnimal(boolean b) {
		// TODO Auto-generated method stub
		
	}

	public void setTarget(Object object) {
		// TODO Auto-generated method stub
		
	}
}
