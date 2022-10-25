package bussiness.design;

import java.util.Scanner;

public interface ICatalog<T,E> extends IShop<T,E>{
    void searchByName(String catalogName);
}
