package presentation;

import bussiness.entity.Catalog;
import bussiness.entity.Product;
import bussiness.imple.CatalogImple;
import bussiness.imple.ProductImple;
import config.ShopMessage;
import config.ShopValidate;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CatalogClothes {
    static CatalogImple catalogImple = new CatalogImple();

    public static void displayCatalogClothes(Scanner scanner) {
        boolean checkExist = true;
        do {
            System.out.println("*--------------------------------------------------------------------------------------------------------------------------*");
            System.out.println("|                       ******************** Quản lý danh mục quần áo ***************                                      |");
            System.out.println("|  1. Danh sách danh mục theo cây danh mục                                                                                 |");
            System.out.println("|  2. Tạo mới danh mục                                                                                                     |");
            System.out.println("|  3. Cập nhật danh mục                                                                                                    |");
            System.out.println("|  4. Xóa danh mục                                                                                                         |");
            System.out.println("|  5. Tìm kiếm danh mục theo tên                                                                                           |");
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
                    displayListCatalog();
                    break;
                case 2:
                    inputCatalog(scanner);
                    break;
                case 3:
                    updateCatalog(scanner);
                    break;
                case 4:
                    deleteCatalog(scanner);
                    break;
                case 5:
                    searchCatalog(scanner);
                    break;
                case 6:
                    checkExist = false;
                    break;
                default:
                    System.err.println(ShopMessage.NOTIFY_CHOICE_ADMIN_MENU);
            }
        } while (checkExist);
    }

    public static void displayListCatalog() {
        List<Catalog> listCatalog = catalogImple.readFromFile();
        if (listCatalog == null) {
            listCatalog = new ArrayList<>();
        }
        for (Catalog cat : listCatalog) {
            if (cat.getCatalog() == null) {
                catalogImple.displayListCatalog(listCatalog, cat, 0);
            }
        }
    }

    public static void inputCatalog(Scanner scanner) {
        Catalog catalog = new Catalog();
        catalog = catalogImple.input(scanner);
        if (catalogImple.create(catalog)) {
            System.out.println(ShopMessage.NOTIFY_INPUT_SUCCESS);
        }
    }

    public static void updateCatalog(Scanner scanner) {
        List<Catalog> listCatalog = catalogImple.readFromFile();
        if (listCatalog == null) {
            listCatalog = new ArrayList<>();
        }
        System.out.println("Nhập id danh mục muốn cập nhật");
        String updateCatalogID;
        do {
            updateCatalogID = scanner.nextLine();
            if (ShopValidate.checkIntergerFormat(updateCatalogID)) {
                break;
            } else {
                System.err.println(ShopMessage.NOTIFY_INTEGER_FORMAT);
            }
        } while (true);
        boolean check = false;
        for (Catalog cat : listCatalog) {
            if (cat.getCatalogID() == Integer.parseInt(updateCatalogID)) {
                System.out.println("Cập nhật tên danh mục: ");
                String updateName = scanner.nextLine();
                if (!updateName.equals("") && updateName.trim().length() != 0) {
                    cat.setCatalogName(updateName);
                }
                System.out.println("Cập nhật mô tả danh mục: ");
                String updateDescriptions = scanner.nextLine();
                if (!updateDescriptions.equals("") && updateDescriptions.trim().length() != 0) {
                    cat.setCatalogDescriptions(updateDescriptions);
                }
                System.out.println("Cập nhật trạng thái danh mục: ");
                do {
                    System.out.println("1. Hoạt động");
                    System.out.println("2. Không hoạt động");
                    System.out.print("Sự lựa chọn của bạn: ");
                    String updateStatus = scanner.nextLine();
                    if (!updateStatus.equals("") && updateStatus.trim().length() != 0) {
                        if (ShopValidate.checkIntergerFormat(updateStatus)) {
                            switch (Integer.parseInt(updateStatus)) {
                                case 1:
                                    cat.setCatalogStatus(true);
                                    break;
                                case 2:
                                    cat.setCatalogStatus(false);
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

                System.out.println("Cập nhật danh mục: ");
                System.out.println("0. Danh mục gốc");
                ProductImple productImple = new ProductImple();
                List<Product> listProduct = productImple.readFromFile();
                if (listProduct == null) {
                    listProduct = new ArrayList<>();
                }
                List<Catalog> listCatalogActive = new ArrayList<>();
                for (Catalog catalog : listCatalog) {
                    if (catalog.isCatalogStatus()) {
                        boolean checkCatalog = false;
                        for (Product pro : listProduct) {
                            if (pro.getCatalog().getCatalogID() == catalog.getCatalogID()) {
                                checkCatalog = true;
                            }
                        }
                        if (!checkCatalog) {
                            listCatalogActive.add(catalog);
                        }
                    }
                }
                for (Catalog catalog : listCatalogActive) {
                    System.out.printf("%d. %s\n", catalog.getCatalogID(), catalog.getCatalogName());
                }
                System.out.println("Sự lựa chọn của bạn: ");
                String updateCatalog = scanner.nextLine();
                if (!updateCatalog.equals("") && updateCatalog.trim().length() != 0) {
                    if (!ShopValidate.checkIntergerFormat(updateCatalog)) {
                        System.err.println(ShopMessage.NOTIFY_INTEGER_FORMAT);
                    } else {
                        if (Integer.parseInt(updateCatalog) == 0) {
                            cat.setCatalog(null);
                        } else if (Integer.parseInt(updateCatalog) >= 1 && Integer.parseInt(updateCatalog) <= listCatalog.size()) {
                            for (Catalog catalog : listCatalog) {
                                if (catalog.getCatalogID() == listCatalog.get(Integer.parseInt(updateCatalog) - 1).getCatalogID()) {
                                    cat.setCatalog(listCatalog.get(Integer.parseInt(updateCatalog) - 1));
                                }
                            }
                        }
                    }
                }
                if (catalogImple.update(cat)) {
                    check = true;
                    break;
                }
            }
        }
        if (check) {
            System.err.println(ShopMessage.NOTIFY_UPDATE_SUCCESS);
        } else {
            System.err.println(ShopMessage.NOTIFY_NOT_EXIST_CATALOG);
        }
    }

    public static void deleteCatalog(Scanner scanner) {
        List<Catalog> listCatalog = catalogImple.readFromFile();
        if (listCatalog == null) {
            listCatalog = new ArrayList<>();
        }
        System.out.println("Nhập id danh mục muốn xóa");
        String deleteCatalogID;
        do {
            deleteCatalogID = scanner.nextLine();
            if (ShopValidate.checkIntergerFormat(deleteCatalogID)) {
                break;
            } else {
                System.err.println(ShopMessage.NOTIFY_INTEGER_FORMAT);
            }
        } while (true);
        if (catalogImple.delete(Integer.parseInt(deleteCatalogID))) {
            System.err.println(ShopMessage.NOTIFY_DELETE_SUCCESS);
        }
    }

    public static void searchCatalog(Scanner scanner) {
        System.out.println("Nhập tên danh mục muốn tìm: ");
        String catalogSearchName = scanner.nextLine();
        System.out.printf("%-15s%-35s%-35s%-30s\n", "Mã danh mục", "Tên danh mục", "Danh mục cha", "Trạng thái");
        catalogImple.searchByName(catalogSearchName);
    }
}
