package com.pinal.my;

// UserModel.java
public class UserModel {
    private int number;
    private boolean highlighted;

    public UserModel(int number) {
        this.number = number;
        this.highlighted = false;
    }

    public int getNumber() {
        return number;
    }

    public boolean isHighlighted() {
        return highlighted;
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }
}
