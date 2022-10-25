package presentation;

import bussiness.entity.Color;
import bussiness.imple.ColorImple;
import config.ShopMessage;
import config.ShopValidate;

import java.util.*;

public class ColorManage {
    static ColorImple colorImple = new ColorImple();

    public static void displayColor(Scanner scanner) {
        boolean checkExist = true;
        do {
            System.out.println("*--------------------------------------------------------------------------------------------------------------------------*");
            System.out.println("|                       ******************** Quản lý màu sắc sản phẩm ***************                                      |");
            System.out.println("|  1. Danh sách màu sắc                                                                                                    |");
            System.out.println("|  2. Tạo mới màu sắc                                                                                                      |");
            System.out.println("|  3. Cập nhật thông tin màu sắc                                                                                           |");
            System.out.println("|  4. Xóa màu sắc                                                                                                          |");
            System.out.println("|  5. Thoát                                                                                                                |");
            System.out.println("*--------------------------------------------------------------------------------------------------------------------------*");
            System.out.print("Lựa chọn của bạn: ");
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
                    displayColorList();
                    break;
                case 2:
                    inputColor(scanner);
                    break;
                case 3:
                    updateColor(scanner);
                    break;
                case 4:
                    deleteColor(scanner);
                    break;
                case 5:
                    checkExist = false;
                    break;
                default:
                    System.err.println(ShopMessage.NOTIFY_CHOICE_COLOR_MENU);
            }
        } while (checkExist);
    }

    public static void displayColorList() {
        List<Color> listColor = colorImple.readFromFile();
        if (listColor == null) {
            listColor = new ArrayList<>();
        }
        Collections.sort(listColor, new Comparator<Color>() {
            @Override
            public int compare(Color o1, Color o2) {
                return o2.getColorName().compareTo(o1.getColorName());
            }
        });
        System.out.printf("%-15s%-35s%-30s\n", "Mã màu", "Tên màu", "Trạng thái");
        for (Color col : listColor) {
            colorImple.display(col);
        }
    }

    public static void inputColor(Scanner scanner) {
        Color colorNew = new Color();
        colorNew = colorImple.input(scanner);
        if (colorImple.create(colorNew)) {
            System.out.println(ShopMessage.NOTIFY_INPUT_SUCCESS);
        }
    }

    public static void updateColor(Scanner scanner) {
        List<Color> listColor = colorImple.readFromFile();
        if (listColor == null) {
            listColor = new ArrayList<>();
        }
        System.out.print("Nhập mã màu cần cập nhật: ");
        String updateColorID;
        do {
            updateColorID = scanner.nextLine();
            if (ShopValidate.checkIntergerFormat(updateColorID)) {
                break;
            } else {
                System.err.println(ShopMessage.NOTIFY_INTEGER_FORMAT);
            }
        } while (true);
        boolean check = false;
        for (Color col : listColor) {
            if (col.getColorID() == Integer.parseInt(updateColorID)) {
                System.out.println("Cập nhật tên màu sắc");
                String updateColorName = scanner.nextLine();
                if (!updateColorName.equals("") && updateColorName.trim().length() != 0) {
                    col.setColorName(updateColorName);
                }
                System.out.println("Cập nhật trạng thái màu sắc: ");
                do {
                    System.out.println("1. Hoạt động");
                    System.out.println("2. Không hoạt động");
                    System.out.print("Sự lựa chọn của bạn: ");
                    String updateStatus = scanner.nextLine();
                    if (!updateStatus.equals("") && updateStatus.trim().length() != 0) {
                        if (ShopValidate.checkIntergerFormat(updateStatus)) {
                            switch (Integer.parseInt(updateStatus)) {
                                case 1:
                                    col.setColorStatus(true);
                                    break;
                                case 2:
                                    col.setColorStatus(false);
                                    break;
                                default:
                                    System.err.println(ShopMessage.NOTIFY_CHOICE_STATUS);
                            }
                            break;
                        } else {
                            System.err.println(ShopMessage.NOTIFY_INTEGER_FORMAT);
                        }
                    }
                } while (true);
                if (colorImple.update(col)) {
                    check = true;
                }
            }
        }
        if (check) {
            System.out.println(ShopMessage.NOTIFY_UPDATE_SUCCESS);
        } else {
            System.err.println(ShopMessage.NOTIFY_NOT_EXIST_COLOR);
        }
    }
    public static void deleteColor(Scanner scanner) {
        System.out.print("Nhập mã màu cần cập nhật: ");
        String updateColorID;
        do {
            updateColorID = scanner.nextLine();
            if (ShopValidate.checkIntergerFormat(updateColorID)) {
                break;
            } else {
                System.err.println(ShopMessage.NOTIFY_INTEGER_FORMAT);
            }
        } while (true);
        if (colorImple.delete(Integer.parseInt(updateColorID))) {
            System.out.println(ShopMessage.NOTIFY_DELETE_SUCCESS);
        }
    }
}
