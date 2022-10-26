package presentation;

import bussiness.entity.User;
import bussiness.imple.UserImple;
import config.ShopMessage;
import config.ShopValidate;

import java.util.*;

public class UserManage {
    static UserImple userImple = new UserImple();

    public static void displayUser(Scanner scanner) {
        boolean checkExist = true;
        do {
            System.out.println("*--------------------------------------------------------------------------------------------------------------------------*");
            System.out.println("|                       ******************** Quản lý tài khoản ***************                                             |");
            System.out.println("|  1. Hiển thị danh sách tài khoản theo ngày tạo                                                                           |");
            System.out.println("|  2. Thêm tài khoản quản trị                                                                                              |");
            System.out.println("|  3. Cập nhật tài khoản quản trị                                                                                          |");
            System.out.println("|  4. Cập nhật trạng thái tài khoản khách hàng                                                                             |");
            System.out.println("|  5. Tìm kiếm tài khoản khách hàng theo tên đăng nhập hoặc tên chủ tài khoản                                              |");
            System.out.println("|  6. Thoát                                                                                                                |");
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
                    displayListUserByDate();
                    break;
                case 2:
                    inputAdmin(scanner);
                    break;
                case 3:
                    updateAdmin(scanner);
                    break;
                case 4:
                    updateUserStatus(scanner);
                    break;
                case 5:
                    searchUserByName(scanner);
                    break;
                case 6:
                    checkExist = false;
                    break;
                default:
                    System.err.println(ShopMessage.NOTIFY_CHOICE_ADMIN_MENU);
            }
        } while (checkExist);
    }

    public static void displayListUserByDate() {
        List<User> listUser = userImple.readFromFile();
        if (listUser == null) {
            listUser = new ArrayList<>();
        }
        Collections.sort(listUser, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });
        for (User user : listUser) {
            userImple.display(user);
        }
    }

    public static void inputAdmin(Scanner scanner) {
        User user = new User();
        user = userImple.input(scanner);
        if (userImple.create(user)) {
            System.out.println(ShopMessage.NOTIFY_INPUT_SUCCESS);
        }
    }

    public static void updateAdmin(Scanner scanner) {
        List<User> listUser = userImple.readFromFile();
        if (listUser == null) {
            listUser = new ArrayList<>();
        }
        System.out.println("Nhập id tài khoản muốn cập nhật");
        String updateUserID;
        do {
            updateUserID = scanner.nextLine();
            if (ShopValidate.checkIntergerFormat(updateUserID)) {
                break;
            } else {
                System.err.println(ShopMessage.NOTIFY_INTEGER_FORMAT);
            }
        } while (true);
        boolean checkUserExist = false;
        for (User user : listUser) {
            if (user.getUserID() == Integer.parseInt(updateUserID) && user.isPermission()) {
                System.out.println("Cập nhật tên tài khoản: ");
                do {
                    String updateUserName = scanner.nextLine();
                    if (!updateUserName.equals("") && updateUserName.trim().length() != 0) {
                        if (ShopValidate.checkRegexUserName(updateUserName)) {
                            user.setUserName(updateUserName);
                            break;
                        } else {
                            System.err.println(ShopMessage.NOTIFY_INPUT_USER_NAME);
                        }
                    } else {
                        break;
                    }
                } while (true);
                System.out.println("Cập nhật tên chủ tài khoản");
                String updateFullName = scanner.nextLine();
                if (!updateFullName.equals("") && updateFullName.trim().length() != 0) {
                    user.setFullName(updateFullName);
                }
                System.out.println("Cập nhật trạng thái: ");
                System.out.println("1. Hoạt động");
                System.out.println("2. Khóa");
                System.out.print("Sự lựa chọn của bạn: ");
                String choiceStatus;
                do {
                    choiceStatus = scanner.nextLine();
                    if (ShopValidate.checkIntergerFormat(choiceStatus)) {
                        break;
                    } else {
                        System.err.println(ShopMessage.NOTIFY_INTEGER_FORMAT);
                    }
                } while (true);
                switch (Integer.parseInt(choiceStatus)) {
                    case 1:
                        user.setStatus(true);
                        break;
                    case 2:
                        user.setStatus(false);
                        break;
                    default:
                        System.err.println(ShopMessage.NOTIFY_CHOICE_STATUS);
                }
                System.out.println("Cập nhật email:");
                String updateEmail = scanner.nextLine();
                do {
                    if (!updateEmail.equals("") && updateEmail.trim().length() != 0) {
                        if (ShopValidate.checkRegexEmail(updateEmail)) {
                            user.setEmail(updateEmail);
                            break;
                        } else {
                            System.err.println(ShopMessage.NOTIFY_INPUT_USER_EMAIL);
                        }
                    } else {
                        break;
                    }
                } while (true);
                System.out.println("Cập nhật số điện thoại:");
                String updatePhone = scanner.nextLine();
                do {
                    if (!updatePhone.equals("") && updatePhone.trim().length() != 0) {
                        if (ShopValidate.checkRegexPhone(updatePhone)) {
                            user.setPhone(updatePhone);
                            break;
                        } else {
                            System.err.println(ShopMessage.NOTIFY_INPUT_USER_PHONE);
                        }
                    } else {
                        break;
                    }
                } while (true);
            }
            if (userImple.update(user)) {
                checkUserExist = true;
                break;
            }
        }
        if (!checkUserExist) {
            System.err.println(ShopMessage.NOTIFY_NOT_EXIST_USER);
        } else {
            System.out.println(ShopMessage.NOTIFY_UPDATE_SUCCESS);
        }
    }

    public static void updateUserStatus(Scanner scanner) {
        List<User> listUser = userImple.readFromFile();
        if (listUser == null) {
            listUser = new ArrayList<>();
        }
        System.out.println("Nhập id tài khoản khách hàng cần thay đổi trạng thái");
        String changeStatus = scanner.nextLine();
        do {
            if (ShopValidate.checkIntergerFormat(changeStatus)) {
                break;
            } else {
                System.err.println(ShopMessage.NOTIFY_INTEGER_FORMAT);
            }
        } while (true);
        boolean checkExist = false;
        for (User user : listUser) {
            if (!user.isPermission()){
                if (user.getUserID() == Integer.parseInt(changeStatus)) {
                    user.setStatus(!user.isStatus());
                    checkExist= userImple.update(user);
                    break;
                }
            }
        }
        if (!checkExist) {
            System.err.println(ShopMessage.NOTIFY_NOT_EXIST_USER);
        } else {
            System.out.println(ShopMessage.NOTIFY_UPDATE_SUCCESS);
        }
    }

    public static void searchUserByName(Scanner scanner) {
        List<User> listUser = userImple.readFromFile();
        if (listUser == null) {
            listUser = new ArrayList<>();
        }
        System.out.println("Nhập tên đăng nhập hoặc tên chủ tài khoản khách hàng cần tìm");
        String searchName = scanner.nextLine();
        boolean checkExist = false;
        for (User user : listUser) {
            if (!user.isPermission()){
                if (user.getUserName().contains(searchName) || user.getFullName().contains(searchName)){
                    userImple.display(user);
                    checkExist = true;
                }
            }
        }
        if (!checkExist) {
            System.err.println(ShopMessage.NOTIFY_NOT_EXIST_USER);
        }
    }
}
