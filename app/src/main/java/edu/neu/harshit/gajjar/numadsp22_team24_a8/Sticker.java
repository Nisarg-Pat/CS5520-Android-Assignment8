package edu.neu.harshit.gajjar.numadsp22_team24_a8;

import java.io.Serializable;
public class Sticker implements Serializable {
    private final String name;
    private final int id;
    private int countSent;
    private boolean isSelected;
    public Sticker(String name, int id, int countSent) {
        this.name = name;
        this.id = id;
        this.countSent = countSent;
        this.isSelected = false;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getCountSent() {
        return countSent;
    }

    public void setCountSent(int count) {
        this.countSent = count;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
