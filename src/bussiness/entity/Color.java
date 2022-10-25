package bussiness.entity;

import java.io.Serializable;

public class Color implements Serializable {
    private int colorID;
    private String colorName;
    private boolean colorStatus;

    public Color() {
    }

    public Color(int colorID, String colorName, boolean colorStatus) {
        this.colorID = colorID;
        this.colorName = colorName;
        this.colorStatus = colorStatus;
    }

    public int getColorID() {
        return colorID;
    }

    public void setColorID(int colorID) {
        this.colorID = colorID;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public boolean isColorStatus() {
        return colorStatus;
    }

    public void setColorStatus(boolean colorStatus) {
        this.colorStatus = colorStatus;
    }
}
