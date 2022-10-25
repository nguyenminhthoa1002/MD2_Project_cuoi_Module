package data;

import java.io.*;
import java.util.List;

public class FileImple<T> {
    public List<T> readFromFile(String path) {
        File file = null;
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        List<T> list = null;
        try {
            file = new File(path);
            if (file.exists()) {
                fileInputStream = new FileInputStream(file);
                objectInputStream = new ObjectInputStream(fileInputStream);
                list = (List<T>) objectInputStream.readObject();
            }
        } catch (Exception exception) {

        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return list;
    }

    public boolean writeToFile(List<T> list, String path) {
        File file = null;
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        boolean result = false;
        try {
            file = new File(path);
            fileOutputStream = new FileOutputStream(file);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(list);
            result = true;
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return result;
    }
}

