package bussiness.imple;

import bussiness.design.IShop;
import bussiness.entity.Color;
import bussiness.entity.Product;
import config.ShopMessage;
import config.ShopValidate;
import data.DataURL;
import data.FileImple;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ColorImple implements IShop<Color, Integer> {

    @Override
    public boolean create(Color color) {
        List<Color> listColor = readFromFile();
        if (listColor == null) {
            listColor = new ArrayList<>();
        }
        listColor.add(color);
        return writeToFile(listColor);
    }

    @Override
    public Color input(Scanner scanner) {
        List<Color> listColor = readFromFile();
        if (listColor == null) {
            listColor = new ArrayList<>();
        }
        Color colorNew = new Color();
        if (listColor.size() == 0) {
            colorNew.setColorID(1);
        } else {
            int max = 0;
            for (Color col : listColor) {
                if (max < col.getColorID()) {
                    max = col.getColorID();
                }
            }
            colorNew.setColorID(max + 1);
        }
        System.out.println("Nhập tên màu: ");
        do {
            colorNew.setColorName(scanner.nextLine());
            if (ShopValidate.checkLength(colorNew.getColorName(), 4, 30)) {
                if (ShopValidate.checkExistColorName(listColor, colorNew.getColorName())) {
                    System.err.println(ShopMessage.NOTIFY_COLOR_NAME_EXIST);
                } else {
                    break;
                }
            } else {
                System.err.println(ShopMessage.NOTIFY_INPUT_COLOR_LENGTH);
            }
        } while (true);
        System.out.println("Chọn trạng thái màu sắc: ");
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
        switch (Integer.parseInt(choice)) {
            case 1:
                colorNew.setColorStatus(true);
                break;
            case 2:
                colorNew.setColorStatus(false);
                break;
            default:
                System.err.println(ShopMessage.NOTIFY_CHOICE_STATUS);
        }

        return colorNew;
    }

    @Override
    public void display(Color color) {
        String status = "Không hoạt động";
        if (color.isColorStatus()) {
            status = "Hoạt động";
        }
        System.out.printf("%-15d%-35s%-30s\n", color.getColorID(), color.getColorName(), status);
    }

    @Override
    public boolean update(Color color) {
        List<Color> listColor = readFromFile();
        if (listColor == null) {
            listColor = new ArrayList<>();
        }
        boolean result = false;
        for (int i = 0; i < listColor.size(); i++) {
            if (listColor.get(i).getColorID() == color.getColorID()) {
                listColor.set(i, color);
                result = true;
            }
        }
        if (result && writeToFile(listColor)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        List<Color> listColor = readFromFile();
        if (listColor == null) {
            listColor = new ArrayList<>();
        }
        boolean result = false;
        for (int i = 0; i < listColor.size(); i++) {
            if (listColor.get(i).getColorID() == id) {
                listColor.get(i).setColorStatus(false);
                result = true;
                break;
            }
        }
        if (result && writeToFile(listColor)) {
            return true;
        }
        return false;
    }

    @Override
    public List<Color> readFromFile() {
        FileImple<Color> fileImple = new FileImple<>();
        return fileImple.readFromFile(DataURL.URL_COLOR_FILE);
    }

    @Override
    public boolean writeToFile(List<Color> list) {
        FileImple<Color> fileImple = new FileImple<>();
        return fileImple.writeToFile(list, DataURL.URL_COLOR_FILE);
    }
}
