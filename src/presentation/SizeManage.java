package presentation;

import bussiness.entity.Size;
import bussiness.imple.SizeImple;
import config.ShopMessage;
import config.ShopValidate;

import java.util.*;

public class SizeManage {
    static SizeImple sizeImple = new SizeImple();

    public static void displaySize(Scanner scanner) {
        boolean checkExist = true;
        do {
            System.out.println("*--------------------------------------------------------------------------------------------------------------------------*");
            System.out.println("|                       ******************** Quản lý kích cỡ sản phẩm ***************                                      |");
            System.out.println("|  1. Danh sách kích cỡ                                                                                                    |");
            System.out.println("|  2. Tạo mới kích cỡ                                                                                                      |");
            System.out.println("|  3. Cập nhật thông tin kích cỡ                                                                                           |");
            System.out.println("|  4. Xóa kích cỡ                                                                                                          |");
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
                    displaySizeList();
                    break;
                case 2:
                    inputSize(scanner);
                    break;
                case 3:
                    updateSize(scanner);
                    break;
                case 4:
                    deleteSize(scanner);
                    break;
                case 5:
                    checkExist = false;
                    break;
                default:
                    System.err.println(ShopMessage.NOTIFY_CHOICE_COLOR_MENU);
            }
        } while (checkExist);
    }

    public static void displaySizeList() {
        List<Size> litSize = sizeImple.readFromFile();
        if (litSize == null) {
            litSize = new ArrayList<>();
        }
        Collections.sort(litSize, new Comparator<Size>() {
            @Override
            public int compare(Size o1, Size o2) {
                return o1.getSizeName().compareTo(o2.getSizeName());
            }
        });
        System.out.printf("%-15s%-35s%-30s\n", "Mã kích cỡ", "Tên kích cỡ", "Trạng thái");
        for (Size size : litSize) {
            sizeImple.display(size);
        }
    }

    public static void inputSize(Scanner scanner) {
        Size sizeNew = new Size();
        sizeNew = sizeImple.input(scanner);
        if (sizeImple.create(sizeNew)) {
            System.out.println(ShopMessage.NOTIFY_INPUT_SUCCESS);
        }
    }

    public static void updateSize(Scanner scanner) {
        List<Size> litSize = sizeImple.readFromFile();
        if (litSize == null) {
            litSize = new ArrayList<>();
        }
        System.out.print("Nhập mã kích cỡ cần cập nhật: ");
        String updateSizeID;
        do {
            updateSizeID = scanner.nextLine();
            if (ShopValidate.checkIntergerFormat(updateSizeID)) {
                break;
            } else {
                System.err.println(ShopMessage.NOTIFY_INTEGER_FORMAT);
            }
        } while (true);
        boolean check = false;
        for (Size size : litSize) {
            if (size.getSizeID() == Integer.parseInt(updateSizeID)) {
                System.out.println("Cập nhật tên kích cỡ");
                String updateSizeName = scanner.nextLine();
                if (!updateSizeName.equals("") && updateSizeName.trim().length() != 0) {
                    size.setSizeName(updateSizeName);
                }
                System.out.println("Cập nhật trạng thái kích cỡ: ");
                do {
                    System.out.println("1. Hoạt động");
                    System.out.println("2. Không hoạt động");
                    System.out.print("Sự lựa chọn của bạn: ");
                    String updateStatus = scanner.nextLine();
                    if (!updateStatus.equals("") && updateStatus.trim().length() != 0) {
                        if (ShopValidate.checkIntergerFormat(updateStatus)) {
                            switch (Integer.parseInt(updateStatus)) {
                                case 1:
                                    size.setSizeStatus(true);
                                    break;
                                case 2:
                                    size.setSizeStatus(false);
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
                if (sizeImple.update(size)) {
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

    public static void deleteSize(Scanner scanner) {
        System.out.print("Nhập mã kích cỡ cần cập nhật: ");
        String updateSizzeID;
        do {
            updateSizzeID = scanner.nextLine();
            if (ShopValidate.checkIntergerFormat(updateSizzeID)) {
                break;
            } else {
                System.err.println(ShopMessage.NOTIFY_INTEGER_FORMAT);
            }
        } while (true);
        if (sizeImple.delete(Integer.parseInt(updateSizzeID))) {
            System.out.println(ShopMessage.NOTIFY_DELETE_SUCCESS);
        }
    }
}
