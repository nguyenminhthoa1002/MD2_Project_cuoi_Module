package bussiness.design;

import java.util.List;
import java.util.Scanner;

public interface IShop<T, E> {
    boolean create(T t);
    T input(Scanner scanner);
    void display(T t);
    boolean update(T t);
    boolean delete(E id);
    List<T> readFromFile();
    boolean writeToFile(List<T> list);

}
