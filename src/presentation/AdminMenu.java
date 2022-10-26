package presentation;

import bussiness.entity.Catalog;
import bussiness.imple.CatalogImple;
import config.ShopMessage;
import config.ShopValidate;

import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    public static void displayAdminMenu(Scanner scanner){
        boolean checkExitAdminMenu = true;
        do {
            System.out.println("*--------------------------------------------------------------------------------------------------------------------------*");
            System.out.println("|                                ******************** ADMIN MENU ***************                                           |");
            System.out.println("|  1. Quản lý danh mục quần áo                                                                                             |");
            System.out.println("|  2. Quản lý màu sắc sản phẩm                                                                                             |");
            System.out.println("|  3. Quản lý kích cỡ sản phẩm                                                                                             |");
            System.out.println("|  4. Quản lý sản phẩm                                                                                                     |");
            System.out.println("|  5. Quản lý Admin & User                                                                                                 |");
            System.out.println("|  6. Thoát                                                                                                                |");
            System.out.println("*--------------------------------------------------------------------------------------------------------------------------*");
            System.out.print("Lựa chọn của bạn: ");
            String choiceAdminMenu;
            do {
                choiceAdminMenu = scanner.nextLine();
                if (ShopValidate.checkIntergerFormat(choiceAdminMenu)) {
                    break;
                } else {
                    System.err.println(ShopMessage.NOTIFY_INTEGER_FORMAT);
                }
            } while (true);
            switch (Integer.parseInt(choiceAdminMenu)){
                case 1:
                    CatalogClothes.displayCatalogClothes(scanner);
                    break;
                case 2:
                    ColorManage.displayColor(scanner);
                    break;
                case 3:
                    SizeManage.displaySize(scanner);
                    break;
                case 4:
                    ProductManage.displayProduct(scanner);
                    break;
                case 5:
                    UserManage.displayUser(scanner);
                    break;
                case 6:
                    checkExitAdminMenu = false;
                    break;
                default:
                    System.err.println(ShopMessage.NOTIFY_CHOICE_ADMIN_MENU);
            }
        } while (checkExitAdminMenu);
    }
}
