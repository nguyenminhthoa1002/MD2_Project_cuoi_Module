package bussiness.entity;

import java.io.Serializable;

public class Size implements Serializable {
    private int sizeID;
    private String sizeName;
    private boolean sizeStatus;

    public Size() {
    }

    public Size(int sizeID, String sizeName, boolean sizeStatus) {
        this.sizeID = sizeID;
        this.sizeName = sizeName;
        this.sizeStatus = sizeStatus;
    }

    public int getSizeID() {
        return sizeID;
    }

    public void setSizeID(int sizeID) {
        this.sizeID = sizeID;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public boolean isSizeStatus() {
        return sizeStatus;
    }

    public void setSizeStatus(boolean sizeStatus) {
        this.sizeStatus = sizeStatus;
    }
}
