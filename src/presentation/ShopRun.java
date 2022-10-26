package presentation;

import bussiness.entity.User;
import bussiness.imple.UserImple;
import config.ShopMessage;
import config.ShopValidate;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShopRun {
    static Scanner scanner = new Scanner(System.in);
    static UserImple userImple = new UserImple();

    public static void main(String[] args) {
        do {
            System.out.println("*--------------------------------------------------------------------------------------------------------------------------*");
            System.out.println("|                               ********************* NEM SHOP **********************                                      |");
            System.out.println("|  1. Đăng Nhập                                                                                                            |");
            System.out.println("|  2. Đăng Ký                                                                                                              |");
            System.out.println("|  3. Thoát                                                                                                                |");
            System.out.println("*--------------------------------------------------------------------------------------------------------------------------*");
            System.out.println("Lựa chọn của bạn: ");
            String choiceMenu;
            do {
                choiceMenu = scanner.nextLine();
                if (ShopValidate.checkIntergerFormat(choiceMenu)) {
                    break;
                } else {
                    System.err.println(ShopMessage.NOTIFY_INTEGER_FORMAT);
                }
            } while (true);
            switch (Integer.parseInt(choiceMenu)) {
                case 1:
                    login(scanner);
                    break;
                case 2:
                    register(scanner);
                    break;
                case 3:
                    scanner.close();
                    System.exit(0);
                default:
                    System.err.println(ShopMessage.NOTIFY_CHOICE_MENU);
            }
        } while (true);
    }

    public static void login(Scanner scanner) {
        do {
            System.out.print("Tên đăng nhập: ");
            String userName = scanner.nextLine();
            System.out.print("Mật khẩu: ");
            String password = scanner.nextLine();
            User user = userImple.checkLogin(userName, password);
            if (user != null) {
                if (user.isStatus()) {
                    if (user.isPermission()) {
                        AdminMenu.displayAdminMenu(scanner);
                    } else {
                        UserMenu.displayUserMenu(scanner);
                    }
                    break;
                } else {
                    System.out.println(ShopMessage.NOTIFY_CLOSE_USER);
                }
            } else {
                System.out.println("1. Đăng nhập lại");
                System.out.println("2. Đăng ký");
                System.out.println("3. Thoát");
                String choiceLoginExist;
                do {
                    choiceLoginExist = scanner.nextLine();
                    if (ShopValidate.checkIntergerFormat(choiceLoginExist)) {
                        break;
                    } else {
                        System.err.println(ShopMessage.NOTIFY_INTEGER_FORMAT);
                    }
                } while (true);
                if (Integer.parseInt(choiceLoginExist) == 2) {
                    register(scanner);
                    break;
                } else if (Integer.parseInt(choiceLoginExist) == 3) {
                    break;
                } else if (Integer.parseInt(choiceLoginExist) != 1 && Integer.parseInt(choiceLoginExist) != 2 && Integer.parseInt(choiceLoginExist) != 3) {
                    System.err.println(ShopMessage.NOTIFY_CHOICE_MENU);
                }
            }
        } while (true);
    }

    public static void register(Scanner scanner) {
        User user = new User();
        user = userImple.input(scanner);
        if (userImple.create(user)) {
            System.out.println(ShopMessage.NOTIFY_REGISTER_SUCCESS);
        } else {
            System.err.println(ShopMessage.NOTIFY_REGISTER_FAIL);
        }
        if (user.isPermission()) {
            AdminMenu.displayAdminMenu(scanner);
        } else {
            UserMenu.displayUserMenu(scanner);
        }
    }
}
