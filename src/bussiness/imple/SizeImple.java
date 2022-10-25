package bussiness.imple;

import bussiness.design.IShop;
import bussiness.entity.Size;
import config.ShopMessage;
import config.ShopValidate;
import data.DataURL;
import data.FileImple;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SizeImple implements IShop<Size, Integer> {

    @Override
    public boolean create(Size size) {
        List<Size> listSize = readFromFile();
        if (listSize == null) {
            listSize = new ArrayList<>();
        }
        listSize.add(size);
        return writeToFile(listSize);
    }

    @Override
    public Size input(Scanner scanner) {
        List<Size> listSize = readFromFile();
        if (listSize == null) {
            listSize = new ArrayList<>();
        }
        Size sizeNew = new Size();
        if (listSize.size() == 0) {
            sizeNew.setSizeID(1);
        } else {
            int max = 0;
            for (Size size : listSize) {
                if (max < size.getSizeID()) {
                    max = size.getSizeID();
                }
            }
            sizeNew.setSizeID(max + 1);
        }
        System.out.println("Nhập tên size: ");
        do {
            sizeNew.setSizeName(scanner.nextLine());
            if (ShopValidate.checkLength(sizeNew.getSizeName(), 1, 10)) {
                if (ShopValidate.checkExistSizeName(listSize, sizeNew.getSizeName())) {
                    System.err.println(ShopMessage.NOTIFY_SIZE_NAME_EXIST);
                } else {
                    break;
                }
            } else {
                System.err.println(ShopMessage.NOTIFY_INPUT_SIZE_LENGTH);
            }
        } while (true);
        System.out.println("Chọn trạng thái kích cỡ: ");
        System.out.println("1. Hoạt động");
        System.out.println("2. Không hoạt động");
        System.out.print("Sự lựa chọn của bạn: ");
        String choice;
        do {
            choice = scanner.nextLine();
            if (ShopValidate.checkIntergerFormat(choice)) {
                break;
            } else {
                System.err.println(ShopMessage.NOTIFY_INTEGER_FORMAT);
            }
        } while (true);
        switch (Integer.parseInt(choice)){
            case 1:
                sizeNew.setSizeStatus(true);
                break;
            case 2:
                sizeNew.setSizeStatus(false);
                break;
            default:
                System.err.println(ShopMessage.NOTIFY_CHOICE_STATUS);
        }
        return sizeNew;
    }

    @Override
    public void display(Size size) {
        String status = "Không hoạt động";
        if (size.isSizeStatus()){
            status = "Hoạt động";
        }
        System.out.printf("%-15d%-35s%-30s\n", size.getSizeID(),size.getSizeName(),status);
    }


    @Override
    public boolean update(Size size) {
        List<Size> listSize = readFromFile();
        if (listSize == null) {
            listSize = new ArrayList<>();
        }
        boolean result = false;
        for (int i = 0; i < listSize.size(); i++) {
            if (listSize.get(i).getSizeID()== size.getSizeID()){
                listSize.set(i,size);
                result = true;
            }
        }
        if (result && writeToFile(listSize)){
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        List<Size> listSize = readFromFile();
        if (listSize == null) {
            listSize = new ArrayList<>();
        }
        boolean result = false;
        for (int i = 0; i < listSize.size(); i++) {
            if (listSize.get(i).getSizeID()==id){
                listSize.get(i).setSizeStatus(false);
                result = true;
                break;
            }
        }
        if (result && writeToFile(listSize)){
            return true;
        }
        return false;
    }

    @Override
    public List<Size> readFromFile() {
        FileImple<Size> fileImple = new FileImple<>();
        return fileImple.readFromFile(DataURL.URL_SIZE_FILE);
    }

    @Override
    public boolean writeToFile(List<Size> list) {
        FileImple<Size> fileImple = new FileImple<>();
        return fileImple.writeToFile(list, DataURL.URL_SIZE_FILE);
    }
}
