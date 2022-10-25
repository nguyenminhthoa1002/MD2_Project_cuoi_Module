package bussiness.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Product implements Serializable {
    private String productID;
    private String productName;
    private float price;
    private int discount;
    private float exportPrice;
    private String title;
    private String descriptions;
    private List<Color> color;
    private List<Size> size;
    private Catalog catalog;
    private boolean status;
    private Date dateInputProduct;

    public Product() {
    }

    public Product(String productID, String productName, float price, int discount, float exportPrice, String title, String descriptions, List<Color> color, List<Size> size, Catalog catalog, boolean status, Date dateInputProduct) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.discount = discount;
        this.exportPrice = exportPrice;
        this.title = title;
        this.descriptions = descriptions;
        this.color = color;
        this.size = size;
        this.catalog = catalog;
        this.status = status;
        this.dateInputProduct = dateInputProduct;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public float getExportPrice() {
        return exportPrice;
    }

    public void setExportPrice(float exportPrice) {
        this.exportPrice = exportPrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public List<Color> getColor() {
        return color;
    }

    public void setColor(List<Color> color) {
        this.color = color;
    }

    public List<Size> getSize() {
        return size;
    }

    public void setSize(List<Size> size) {
        this.size = size;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getDateInputProduct() {
        return dateInputProduct;
    }

    public void setDateInputProduct(Date dateInputProduct) {
        this.dateInputProduct = dateInputProduct;
    }
}
