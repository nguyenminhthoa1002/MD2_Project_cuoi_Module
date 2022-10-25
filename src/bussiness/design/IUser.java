package bussiness.design;

import bussiness.entity.Catalog;
import bussiness.entity.Product;

import java.util.Date;
import java.util.List;

public interface IUser<T,E> extends IShop<T,E>{
    void displayProductByCatalog(List<Catalog> list);
    void displayProductByDate(Date date);
    void displayProductByDiscount(float discount);
    Product searchByProductName(String productName);
    Product searchByCatalogName(String catalogName);
    List<Product> searchProductByExportPrice(float minExportPrice, float maxExportPrice);
    List<Product> searchProductByDiscount(float discountPrice);
    boolean register();
    boolean login();
    boolean changePassword();
}
