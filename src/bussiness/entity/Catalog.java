package bussiness.entity;

import java.io.Serializable;

public class Catalog implements Serializable {
    private int catalogID;
    private String catalogName;
    private String catalogDescriptions;
    private boolean catalogStatus;
    private Catalog catalog;

    public Catalog() {
    }

    public Catalog(int catalogID, String catalogName, String catalogDescriptions, boolean catalogStatus, Catalog catalog) {
        this.catalogID = catalogID;
        this.catalogName = catalogName;
        this.catalogDescriptions = catalogDescriptions;
        this.catalogStatus = catalogStatus;
        this.catalog = catalog;
    }

    public int getCatalogID() {
        return catalogID;
    }

    public void setCatalogID(int catalogID) {
        this.catalogID = catalogID;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getCatalogDescriptions() {
        return catalogDescriptions;
    }

    public void setCatalogDescriptions(String catalogDescriptions) {
        this.catalogDescriptions = catalogDescriptions;
    }

    public boolean isCatalogStatus() {
        return catalogStatus;
    }

    public void setCatalogStatus(boolean catalogStatus) {
        this.catalogStatus = catalogStatus;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }
}
