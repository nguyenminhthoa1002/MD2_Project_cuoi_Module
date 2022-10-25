package presentation;

import config.ShopMessage;
import config.ShopValidate;

import java.util.Scanner;

public class UserMenu {
    public static void displayUserMenu(Scanner scanner) {
        boolean checkExitUserMenu = true;
        do {
            System.out.println("*--------------------------------------------------------------------------------------------------------------------------*");
            System.out.println("|                                 ******************** USER MENU ***************                                           |");
            System.out.println("|  1. Xem danh sách sản phẩm                                                                                               |");
            System.out.println("|  2. Tìm kiếm sản phẩm                                                                                                    |");
            System.out.println("|  3. Đổi mật khẩu                                                                                                         |");
            System.out.println("|  4. Thoát                                                                                                                |");
            System.out.println("*--------------------------------------------------------------------------------------------------------------------------*");
            System.out.print("Lựa chọn của bạn: ");
            String choiceUserMenu;
            do {
                choiceUserMenu = scanner.nextLine();
                if (ShopValidate.checkIntergerFormat(choiceUserMenu)) {
                    break;
                } else {
                    System.err.println(ShopMessage.NOTIFY_INTEGER_FORMAT);
                }
            } while (true);
            switch (Integer.parseInt(choiceUserMenu)) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    checkExitUserMenu = false;
                    break;
                default:
                    System.err.println(ShopMessage.NOTIFY_CHOICE_USER_MENU);
            }
        } while (checkExitUserMenu);

    }
}
