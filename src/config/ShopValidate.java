package config;

import bussiness.entity.*;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShopValidate {
    public static boolean checkEmpty(String string) {
        if (!string.equals("") && string.trim().length() != 0) {
            return true;
        }
        return false;
    }

    public static boolean checkLength(String string, int min, int max) {
        if (string.trim().length() >= min && string.trim().length() <= max) {
            return true;
        }
        return false;
    }

    public static boolean checkLengthProductID(String string) {
        if (string.trim().length() == 5) {
            return true;
        }
        return false;
    }

    public static boolean checkLengthUserPassWord(String string) {
        if (string.trim().length() >= 6) {
            return true;
        }
        return false;
    }

    public static boolean checkExistCatalogName(List<Catalog> listCat, String catalogName) {
        for (Catalog cat : listCat) {
            if (cat.getCatalogName().equals(catalogName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkExistColorName(List<Color> listColor, String colorName) {
        for (Color col : listColor) {
            if (col.getColorName().equals(colorName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkExistSizeName(List<Size> listSize, String sizeName) {
        for (Size size : listSize) {
            if (size.getSizeName().equals(sizeName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkExistProductID(List<Product> listProduct, String productID) {
        for (Product pro : listProduct) {
            if (pro.getProductID().equals(productID)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkExistProductName(List<Product> listProduct, String productName) {
        for (Product pro : listProduct) {
            if (pro.getProductName().equals(productName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkIntergerFormat(String string) {
        try {
            int number = Integer.parseInt(string);
            if (number >= 0) {
                return true;
            }
            return false;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    public static boolean checkFloatFormat(String string) {
        try {
            float number = Float.parseFloat(string);
            if (number >= 0) {
                return true;
            }
            return false;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    public static boolean checkRegexUserName(String string) {
        String userName = ShopConstanst.REGEX_NAME_LOGIN;
        Pattern pattern = Pattern.compile(userName);
        Matcher matcher = pattern.matcher(string);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    public static boolean checkRegexEmail(String string) {
        String email = ShopConstanst.REGEX_EMAIL;
        Pattern pattern = Pattern.compile(email);
        Matcher matcher = pattern.matcher(string);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    public static boolean checkRegexPhone(String string) {
        String phone = ShopConstanst.REGEX_PHONE;
        Pattern pattern = Pattern.compile(phone);
        Matcher matcher = pattern.matcher(string);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    public static boolean checkExistRegisterName(List<User> listUser, String userName) {
        for (User user : listUser) {
            if (user.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }
}
