package bussiness.imple;

import bussiness.design.IUser;
import bussiness.entity.Catalog;
import bussiness.entity.Product;
import bussiness.entity.User;
import config.ShopMessage;
import config.ShopValidate;
import data.DataURL;
import data.FileImple;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class UserImple<T, E> implements IUser<User, Integer>, Serializable {
    @Override
    public boolean create(User user) {
        List<User> listUser = readFromFile();
        if (listUser == null) {
            listUser = new ArrayList<>();
        }
        listUser.add(user);
        return writeToFile(listUser);
    }

    @Override
    public User input(Scanner scanner) {
        List<User> listUser = readFromFile();
        User userNew = new User();
        if (listUser == null) {
            listUser = new ArrayList<>();
            userNew.setUserID(1);
        } else {
            int max = 0;
            for (User user : listUser) {
                if (max < user.getUserID()) {
                    max = user.getUserID();
                }
            }
            userNew.setUserID(max + 1);
        }
        System.out.println("Nhập tên đăng nhập tài khoản: ");
        do {
            userNew.setUserName(scanner.nextLine());
            if (ShopValidate.checkRegexUserName(userNew.getUserName())) {
                if (ShopValidate.checkExistRegisterName(listUser, userNew.getUserName())){
                    System.err.println(ShopMessage.NOTIFY_EXIST_USER);
                } else {
                    break;
                }

            } else {
                System.err.println(ShopMessage.NOTIFY_INPUT_USER_NAME);
            }
        } while (true);
        System.out.println("Nhập mật khẩu: ");
        do {
            userNew.setPassword(scanner.nextLine());
            if (ShopValidate.checkLengthUserPassWord(userNew.getPassword())) {
                break;
            } else {
                System.err.println(ShopMessage.NOTIFY_INPUT_USER_PASSWORD);
            }
        } while (true);
        System.out.println("Xác nhận mật khẩu: ");
        do {
            userNew.setConfirmPassword(scanner.nextLine());
            if (userNew.getConfirmPassword().equals(userNew.getPassword())) {
                break;
            } else {
                System.err.println(ShopMessage.NOTIFY_INPUT_CONFIRM_PASSWORD);
            }
        } while (true);
        System.out.println("Nhập tên chủ tài khoản:");
        userNew.setFullName(scanner.nextLine());
        System.out.println("Chọn loại tài khoản: ");
        System.out.println("1. Quản trị");
        System.out.println("2. Khách hàng");
        System.out.println("Sự lựa chọn của bạn");
        String choicePermission;
        do {
            choicePermission = scanner.nextLine();
            if (ShopValidate.checkIntergerFormat(choicePermission)) {
                break;
            } else {
                System.err.println(ShopMessage.NOTIFY_INTEGER_FORMAT);
            }
        } while (true);
        switch (Integer.parseInt(choicePermission)) {
            case 1:
                userNew.setPermission(true);
                break;
            case 2:
                userNew.setPermission(false);
                break;
            default:
                System.err.println(ShopMessage.NOTIFY_CHOICE_STATUS);
        }
        Date date = new Date();
        userNew.setDate(date);
//        System.out.println("Chọn trạng thái tài khoản: ");
//        System.out.println("1. Hoạt động");
//        System.out.println("2. Khóa");
//        System.out.print("Sự lựa chọn của bạn: ");
//        String choiceStatus;
//        do {
//            choiceStatus = scanner.nextLine();
//            if (ShopValidate.checkIntergerFormat(choiceStatus)) {
//                break;
//            } else {
//                System.err.println(ShopMessage.NOTIFY_INTEGER_FORMAT);
//            }
//        } while (true);
//        switch (Integer.parseInt(choiceStatus)) {
//            case 1:
//                userNew.setStatus(true);
//                break;
//            case 2:
//                userNew.setStatus(false);
//                break;
//            default:
//                System.err.println(ShopMessage.NOTIFY_CHOICE_STATUS);
//        }
        userNew.setStatus(true);
        System.out.println("Nhập Email: ");
        do {
            userNew.setEmail(scanner.nextLine());
            if (ShopValidate.checkRegexEmail(userNew.getEmail())) {
                break;
            } else {
                System.err.println(ShopMessage.NOTIFY_INPUT_USER_EMAIL);
            }
        } while (true);
        System.out.println("Nhập số điện thoại: ");
        do {
            userNew.setPhone(scanner.nextLine());
            if (ShopValidate.checkRegexPhone(userNew.getPhone())) {
                break;
            } else {
                System.err.println(ShopMessage.NOTIFY_INPUT_USER_PHONE);
            }
        } while (true);
        return userNew;
    }

    @Override
    public void display(User user) {
        String status = "Khóa";
        if (user.isStatus()) {
            status = "Hoạt động";
        }
        String permission = "User";
        if (user.isPermission()) {
            permission = "Admin";
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String stringDate = formatter.format(user.getDate());
        System.out.println("*--------------------------------------------------------------------------------------------------------------------------------------------------------------------------*");
        System.out.printf("|   %-40s%-35s%-35s%-35s%-35s\n", "Mã tài khoản", "Tên đăng nhập", "Mật khẩu", "Loại tài khoản", "Trạng thái");
        System.out.printf("|   %-40d%-35s%-35s%-35s%-35s\n", user.getUserID(), user.getUserName(), user.getPassword(), permission, status);
        System.out.printf("|   %-40s%-35s%-35s%-35s\n", "Tên chủ tài khoản", "Ngày tạo", "Email", "Phone");
        System.out.printf("|   %-40s%-35s%-35s%-35s\n", user.getFullName(), stringDate, user.getEmail(), user.getPhone());
        System.out.println("*--------------------------------------------------------------------------------------------------------------------------------------------------------------------------*");
    }

    @Override
    public boolean update(User user) {
        List<User> listUser = readFromFile();
        boolean result = false;
        for (int i = 0; i < listUser.size(); i++) {
            if (listUser.get(i).getUserID() == user.getUserID()) {
                listUser.set(i, user);
                result = true;
                break;
            }
        }
        if (result && writeToFile(listUser)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        List<User> listUser = readFromFile();
        boolean result = false;
        for (int i = 0; i < listUser.size(); i++) {
            if (listUser.get(i).getUserID() == id) {
                listUser.get(i).setStatus(false);
                result = true;
                break;
            }
        }
        if (result && writeToFile(listUser)) {
            return true;
        }
        return false;
    }

    @Override
    public List<User> readFromFile() {
        FileImple<User> fileImple = new FileImple<>();
        return fileImple.readFromFile(DataURL.URL_USER_FILE);
    }

    @Override
    public boolean writeToFile(List<User> list) {
        FileImple<User> fileImple = new FileImple<>();
        return fileImple.writeToFile(list, DataURL.URL_USER_FILE);
    }


    @Override
    public void displayProductByCatalog(List<Catalog> list) {

    }

    @Override
    public void displayProductByDate(Date date) {

    }

    @Override
    public void displayProductByDiscount(float discount) {

    }

    @Override
    public Product searchByProductName(String productName) {
        return null;
    }

    @Override
    public Product searchByCatalogName(String catalogName) {
        return null;
    }

    @Override
    public List<Product> searchProductByExportPrice(float minExportPrice, float maxExportPrice) {
        return null;
    }

    @Override
    public List<Product> searchProductByDiscount(float discountPrice) {
        return null;
    }

    @Override
    public boolean register() {
        return false;
    }

    @Override
    public boolean login() {
        return false;
    }

    @Override
    public boolean changePassword() {
        return false;
    }

    public User checkLogin(String userName, String password) {
        List<User> listUser = readFromFile();
        if (listUser==null) {
            listUser = new ArrayList<>();
            System.out.println(ShopMessage.NOTIFY_INPUT_CONFIRM_PASSWORD);
        } else {
            boolean checkExistUser = false;
            for (User user : listUser) {
                if (user.getUserName().equals(userName) && user.getPassword().equals(password)) {
                    checkExistUser = true;
                    return user;
                }
            }
            if (!checkExistUser) {
                System.out.println(ShopMessage.NOTIFY_INPUT_CONFIRM_PASSWORD);
            }
        }
        return null;
    }
}
