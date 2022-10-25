package bussiness.imple;

import bussiness.design.ICatalog;
import bussiness.entity.Catalog;
import bussiness.entity.Product;
import config.ShopMessage;
import config.ShopValidate;
import data.DataURL;
import data.FileImple;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CatalogImple implements ICatalog<Catalog, Integer> {
    @Override
    public void searchByName(String catalogName) {
        List<Catalog> listCatalog = readFromFile();
        if (listCatalog == null) {
            listCatalog = new ArrayList<>();
        }
        for (Catalog cat : listCatalog) {
            if (cat.getCatalogName().contains(catalogName)) {
                display(cat);
            }
        }
    }

    @Override
    public boolean create(Catalog catalog) {
        List<Catalog> listCatalog = readFromFile();
        if (listCatalog == null) {
            listCatalog = new ArrayList<>();
        }
        listCatalog.add(catalog);
        return writeToFile(listCatalog);
    }

    @Override
    public Catalog input(Scanner scanner) {
        List<Catalog> listCatalog = readFromFile();
        if (listCatalog == null) {
            listCatalog = new ArrayList<>();
        }
        Catalog catalogNew = new Catalog();
        if (listCatalog.size() == 0) {
            catalogNew.setCatalogID(1);
        } else {
            int max = 0;
            for (Catalog cat : listCatalog) {
                if (max < cat.getCatalogID()) {
                    max = cat.getCatalogID();
                }
            }
            catalogNew.setCatalogID(max + 1);
        }
        System.out.println("Nhập tên danh mục: ");
        do {
            catalogNew.setCatalogName(scanner.nextLine());
            if (ShopValidate.checkLength(catalogNew.getCatalogName(), 6, 30)) {
                if (ShopValidate.checkExistCatalogName(listCatalog, catalogNew.getCatalogName())) {
                    System.err.println(ShopMessage.NOTIFY_CATALOG_NAME_EXIST);
                } else {
                    break;
                }
            } else {
                System.err.println(ShopMessage.NOTIFY_INPUT_LENGTH);
            }
        } while (true);
        System.out.println("Nhập mô tả danh mục: ");
        do {
            catalogNew.setCatalogDescriptions(scanner.nextLine());
            if (ShopValidate.checkEmpty(catalogNew.getCatalogDescriptions())) {
                break;
            } else {
                System.err.println(ShopMessage.NOTIFY_INPUT_EMPTY);
            }
        } while (true);
        System.out.println("Chọn trạng thái danh mục: ");
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
                catalogNew.setCatalogStatus(true);
                break;
            case 2:
                catalogNew.setCatalogStatus(false);
                break;
            default:
                System.err.println(ShopMessage.NOTIFY_CHOICE_STATUS);
        }
        System.out.println("Chọn danh mục sản phẩm: ");
        System.out.println("0. Danh mục gốc");
        ProductImple productImple = new ProductImple();
        List<Product> listProduct = productImple.readFromFile();
        if (listProduct==null) {
            listProduct = new ArrayList<>();
        }
        List<Catalog> listCatalogActive = new ArrayList<>();
        for (Catalog cat : listCatalog) {
            if (cat.isCatalogStatus()) {
                boolean checkCatalog = false;
                for (Product pro : listProduct) {
                    if (pro.getCatalog().getCatalogID() == cat.getCatalogID()) {
                        checkCatalog = true;
                    }
                }
                if (!checkCatalog) {
                    listCatalogActive.add(cat);
                }
            }
        }
        for (Catalog cat : listCatalogActive) {
            System.out.printf("%d. %s\n",cat.getCatalogID(),cat.getCatalogName());
        }
        System.out.println("Sự lựa chọn của bạn: ");
        String choiceCatalog;
        do {
            choiceCatalog = scanner.nextLine();
            if (!ShopValidate.checkIntergerFormat(choiceCatalog)) {
                System.err.println(ShopMessage.NOTIFY_INTEGER_FORMAT);
            } else {
                if (Integer.parseInt(choiceCatalog) == 0) {
                    catalogNew.setCatalog(null);
                    break;
                } else if (Integer.parseInt(choiceCatalog) >= 1 && Integer.parseInt(choiceCatalog) <= listCatalog.size()) {
                    for (Catalog cat : listCatalog) {
                        if (cat.getCatalogID() == listCatalog.get(Integer.parseInt(choiceCatalog) - 1).getCatalogID()) {
                            catalogNew.setCatalog(listCatalog.get(Integer.parseInt(choiceCatalog) - 1));
                            break;
                        }
                    }
                    break;
                } else {
                    System.err.println(ShopMessage.NOTIFY_CATALOG_CHOICE);
                }
            }
        } while (true);
        return catalogNew;
    }

    @Override
    public void display(Catalog catalog) {
        String status = "Không hoạt động";
        if (catalog.isCatalogStatus()) {
            status = "Hoạt động";
        }
        String name = (catalog.getCatalog()==null)?"":catalog.getCatalog().getCatalogName();
        System.out.printf("%-15d%-35s%-35s%-30s\n", catalog.getCatalogID(), catalog.getCatalogName(), name, status);
    }

    public void displayListCatalog(List<Catalog> listCat, Catalog root, int count) {
        for (int i = 0; i < count; i++) {
            System.out.print("\t");
        }
        String status = "Không hoạt động";
        if (root.isCatalogStatus()) {
            status = "Hoạt động";
        }
        System.out.printf("%d. %s - %s\n", root.getCatalogID(), root.getCatalogName(), status);
        List<Catalog> listChild = new ArrayList<>();
        for (Catalog cat : listCat) {
            if (cat.getCatalog() != null && cat.getCatalog().getCatalogID() == root.getCatalogID()) {
                listChild.add(cat);
            }
        }
        if (listChild.size() != 0) {
            count++;
        }
        for (Catalog cat : listChild) {
            displayListCatalog(listCat, cat, count);
        }
    }

    @Override
    public boolean update(Catalog catalog) {
        List<Catalog> listCatalog = readFromFile();
        if (listCatalog == null) {
            listCatalog = new ArrayList<>();
        }
        boolean result = false;
        for (int i = 0; i < listCatalog.size(); i++) {
            if (listCatalog.get(i).getCatalogID() == catalog.getCatalogID()) {
                listCatalog.set(i, catalog);
                result = true;
                break;
            }
        }
        if (writeToFile(listCatalog) && result) {
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        List<Catalog> listCatalog = readFromFile();
        if (listCatalog == null) {
            listCatalog = new ArrayList<>();
        }
        boolean result = false;
        for (int i = 0; i < listCatalog.size(); i++) {
            if (listCatalog.get(i).getCatalogID() == id) {
                listCatalog.get(i).setCatalogStatus(false);
                result = true;
                break;
            }
        }
        if (writeToFile(listCatalog) && result) {
            return true;
        }
        return false;
    }

    @Override
    public List<Catalog> readFromFile() {
        FileImple<Catalog> fileImple = new FileImple<>();
        return fileImple.readFromFile(DataURL.URL_CATALOG_FILE);
    }

    @Override
    public boolean writeToFile(List<Catalog> list) {
        FileImple<Catalog> fileImple = new FileImple<>();
        return fileImple.writeToFile(list, DataURL.URL_CATALOG_FILE);
    }
}
