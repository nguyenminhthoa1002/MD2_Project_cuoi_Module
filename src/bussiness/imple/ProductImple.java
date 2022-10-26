package bussiness.imple;

import bussiness.design.IShop;
import bussiness.entity.Catalog;
import bussiness.entity.Color;
import bussiness.entity.Product;
import bussiness.entity.Size;
import config.ShopMessage;
import config.ShopValidate;
import data.DataURL;
import data.FileImple;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ProductImple implements IShop<Product, Integer> {

    @Override
    public boolean create(Product product) {
        List<Product> listProduct = readFromFile();
        if (listProduct == null) {
            listProduct = new ArrayList<>();
        }
        listProduct.add(product);
        return writeToFile(listProduct);
    }

    @Override
    public Product input(Scanner scanner) {
        List<Product> listProduct = readFromFile();
        if (listProduct == null) {
            listProduct = new ArrayList<>();
        }
        Product productNew = new Product();
        System.out.println("Nhập mã sản phẩm: ");
        do {
            productNew.setProductID(scanner.nextLine());
            if (ShopValidate.checkLengthProductID(productNew.getProductID())) {
                if (productNew.getProductID().charAt(0) == 'P') {
                    if (ShopValidate.checkExistProductID(listProduct, productNew.getProductID())) {
                        System.err.println(ShopMessage.NOTIFY_PRODUCTID_EXIST);
                    } else {
                        break;
                    }
                } else {
                    System.err.println(ShopMessage.NOTIFY_INPUT_PRODUCTID_STARTWITH);
                }
            } else {
                System.err.println(ShopMessage.NOTIFY_INPUT_PRODUCTID_LENGTH);
            }
        } while (true);
        System.out.println("Nhập tên sản phẩm: ");
        do {
            productNew.setProductName(scanner.nextLine());
            if (ShopValidate.checkLength(productNew.getProductName(), 6, 30)) {
                if (ShopValidate.checkExistProductName(listProduct, productNew.getProductName())) {
                    System.err.println(ShopMessage.NOTIFY_PRODUCT_NAME_EXIST);
                } else {
                    break;
                }
            } else {
                System.err.println(ShopMessage.NOTIFY_INPUT_LENGTH);
            }
        } while (true);
        System.out.println("Nhập giá sản phẩm: ");
        String price;
        do {
            price = scanner.nextLine();
            if (ShopValidate.checkFloatFormat(price)) {
                productNew.setPrice(Float.parseFloat(price));
                break;
            } else {
                System.err.println(ShopMessage.NOTIFY_FLOAT_FORMAT);
            }
        } while (true);
        System.out.println("Nhập phần trăm giảm giá: ");
        String discount;
        do {
            discount = scanner.nextLine();
            if (ShopValidate.checkIntergerFormat(discount)) {
                if (Integer.parseInt(discount) >= 0 && Integer.parseInt(discount) <= 100) {
                    productNew.setDiscount(Integer.parseInt(discount));
                    break;
                } else {
                    System.err.println(ShopMessage.NOTIFY_DISCOUNT);
                }
            } else {
                System.err.println(ShopMessage.NOTIFY_INTEGER_FORMAT);
            }
        } while (true);
        System.out.println("Nhập tiêu đề sản phẩm:");
        do {
            productNew.setTitle(scanner.nextLine());
            if (ShopValidate.checkLength(productNew.getTitle(), 10, 50)) {
                break;
            } else {
                System.err.println(ShopMessage.NOTIFY_INPUT_PRODUCT_TITLE_LENGTH);
            }
        } while (true);
        System.out.println("Nhập mô tả sản phẩm:");
        do {
            productNew.setDescriptions(scanner.nextLine());
            if (ShopValidate.checkEmpty(productNew.getDescriptions())) {
                break;
            } else {
                System.err.println(ShopMessage.NOTIFY_INPUT_EMPTY);
            }
        } while (true);
        System.out.println("Chọn màu sắc sản phẩm:");
        ColorImple colorImple = new ColorImple();
        List<Color> listColor = colorImple.readFromFile();
        if (listColor == null) {
            listColor = new ArrayList<>();
        }
        List<Color> listProductColor = new ArrayList<>();
        do {
            System.out.printf("%-15s%-35s\n", "Mã màu", "Tên màu");
            for (Color col : listColor) {
                if (col.isColorStatus()) {
                    System.out.printf("%-15d%-35s\n", col.getColorID(), col.getColorName());
                }
            }
            System.out.println("Sự lựa chọn của bạn:");
            String choice;
            do {
                choice = scanner.nextLine();
                if (ShopValidate.checkIntergerFormat(choice)) {
                    break;
                } else {
                    System.out.println(ShopMessage.NOTIFY_INTEGER_FORMAT);
                }
            } while (true);

            if (Integer.parseInt(choice) >= 1 && Integer.parseInt(choice) <= listColor.size()) {
                boolean checkColorExist = false;
                for (Color colorExist : listProductColor) {
                    if (colorExist.getColorID() == Integer.parseInt(choice)) {
                        checkColorExist = true;
                    }
                }
                if (!checkColorExist) {
                    boolean checkColorFile = false;
                    for (Color colorChoice : listColor) {
                        if (colorChoice.getColorID() == Integer.parseInt(choice)) {
                            checkColorFile = true;
                            listProductColor.add(colorChoice);
                            break;
                        }
                    }
                    if (!checkColorFile) {
                        System.err.println("Vui long chon mau sac trong danh sach");
                    }

                } else {
                    System.err.println(ShopMessage.NOTIFY_INPUT_PRODUCT_COLOR_EXIST);
                }
                System.out.println("Bạn có muốn chọn thêm màu cho sản phẩm không?");
                System.out.println("1. Có");
                System.out.println("2. Không");
                System.out.println("Sự lựa chọn của bạn: ");
                String choiceExit;
                do {
                    choiceExit = scanner.nextLine();
                    if (ShopValidate.checkIntergerFormat(choiceExit)) {
                        break;
                    } else {
                        System.err.println(ShopMessage.NOTIFY_INTEGER_FORMAT);
                    }
                } while (true);
                if (Integer.parseInt(choiceExit) == 2) {
                    break;
                } else if (Integer.parseInt(choiceExit) != 1 && Integer.parseInt(choiceExit) != 2) {
                    System.err.println(ShopMessage.NOTIFY_CHOICE_STATUS);
                }
            } else {
                System.err.println(ShopMessage.NOTIFY_INPUT_PRODUCT_COLOR);
            }
        } while (true);
        productNew.setColor(listProductColor);

        List<Size> listProductSize = new ArrayList<>();
        System.out.println("Chọn size sản phẩm:");
        SizeImple sizeImple = new SizeImple();
        List<Size> listSize = sizeImple.readFromFile();
        if (listSize == null) {
            listSize = new ArrayList<>();
        }
        do {
            System.out.printf("%-15s%-35s\n", "Mã kích cỡ", "Tên kích cỡ");
            for (Size size : listSize) {
                if (size.isSizeStatus()) {
                    System.out.printf("%-15d%-35s\n", size.getSizeID(), size.getSizeName());
                }
            }
            System.out.println("Sự lựa chọn của bạn: ");
            String choiceSize;
            do {
                choiceSize = scanner.nextLine();
                if (ShopValidate.checkIntergerFormat(choiceSize)) {
                    break;
                } else {
                    System.err.println(ShopMessage.NOTIFY_INTEGER_FORMAT);
                }
            } while (true);
            if (Integer.parseInt(choiceSize) >= 1 && Integer.parseInt(choiceSize) <= listSize.size()) {
                boolean checkSizeExist = false;
                for (Size sizeExist : listProductSize) {
                    if (sizeExist.getSizeID() == Integer.parseInt(choiceSize)) {
                        checkSizeExist = true;
                        break;
                    }
                }
                if (!checkSizeExist) {
                    boolean checkSizeFile = false;
                    for (Size sizeChoice : listSize) {
                        if (sizeChoice.getSizeID() == Integer.parseInt(choiceSize)) {
                            checkSizeFile = true;
                            listProductSize.add(sizeChoice);
                            break;
                        }
                    }
                    if (!checkSizeFile) {
                        System.err.println("Vui long chon kich co trong danh sach");
                    }
                } else {
                    System.err.println(ShopMessage.NOTIFY_INPUT_PRODUCT_SIZE_EXIST);
                }
                System.out.println("Bạn có muốn chọn thêm size cho sản phẩm không?");
                System.out.println("1. Có");
                System.out.println("2. Không");
                System.out.println("Sự lựa chọn của bạn: ");
                String choiceExistSize;
                do {
                    choiceExistSize = scanner.nextLine();
                    if (ShopValidate.checkIntergerFormat(choiceExistSize)) {
                        break;
                    } else {
                        System.err.println(ShopMessage.NOTIFY_INTEGER_FORMAT);
                    }
                } while (true);
                if (Integer.parseInt(choiceExistSize) == 2) {
                    break;
                } else if (Integer.parseInt(choiceExistSize) != 1 && Integer.parseInt(choiceExistSize) != 2) {
                    System.err.println(ShopMessage.NOTIFY_CHOICE_STATUS);
                }
            } else {
                System.err.println(ShopMessage.NOTIFY_INPUT_PRODUCT_SIZE);
            }
        } while (true);
        productNew.setSize(listProductSize);
        System.out.println("Chọn danh mục sản phẩm: ");
        CatalogImple catalogImple = new CatalogImple();
        List<Catalog> listCatalog = catalogImple.readFromFile();
        if (listCatalog == null) {
            listCatalog = new ArrayList<>();
        }
        for (Catalog cat : listCatalog) {
            boolean checkExistChild = false;
            for (Catalog catChild : listCatalog) {
                if (catChild.getCatalog() != null && catChild.getCatalog().getCatalogID() == cat.getCatalogID()) {
                    checkExistChild = true;
                    break;
                }
            }
            if (!checkExistChild && cat.isCatalogStatus()) {
                catalogImple.display(cat);
            }
        }
        System.out.println("Sự lựa chọn của bạn: ");
        String choiceCatalog;
        do {
            choiceCatalog = scanner.nextLine();
            do {
                if (ShopValidate.checkIntergerFormat(choiceCatalog)) {
                    break;
                } else {
                    System.err.println(ShopMessage.NOTIFY_INTEGER_FORMAT);
                }
            } while (true);
            if (Integer.parseInt(choiceCatalog) >= 1 && Integer.parseInt(choiceCatalog) <= listCatalog.size()) {
                for (Catalog catExist : listCatalog) {
                    if (catExist.getCatalogID() == Integer.parseInt(choiceCatalog)) {
                        productNew.setCatalog(catExist);
                        break;
                    }
                }
                break;
            } else {
                System.err.println(ShopMessage.NOTIFY_INPUT_PRODUCT_CATALOG);
            }
        } while (true);

        System.out.println("Chọn trạng thái sản phẩm");
        System.out.println("1. Hoạt động");
        System.out.println("2. Không hoạt động");
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
                productNew.setStatus(true);
                break;
            case 2:
                productNew.setStatus(false);
                break;
            default:
                System.err.println(ShopMessage.NOTIFY_CHOICE_STATUS);
        }
        Date date = new Date();
        productNew.setDateInputProduct(date);
        return productNew;
    }

    @Override
    public void display(Product product) {
        String status = "Không hoạt động";
        if (product.isStatus()) {
            status = "Hoạt động";
        }
        product.setExportPrice(calExportPrice(product));
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String stringDate = formatter.format(product.getDateInputProduct());
        System.out.println("*----------------------------------------------------------------------------------------------------------------------------------------------------------------------------*");
        System.out.printf("|   %-35s%-35s%-30s%-35s%-30s\n", "Mã sản phẩm", "Tên sản phẩm", "Giá sản phẩm", "Phần trăm giảm giá", "Giá bán sản phẩm");
        System.out.printf("|   %-35s%-35s%-30.0f%-35d%-30.0f\n", product.getProductID(), product.getProductName(), product.getPrice(), product.getDiscount(), product.getExportPrice());
        System.out.printf("|   %-35s%-35s%-30s%-35s%-30s\n", "Tiêu đề", "Mô tả", "Danh mục", "Trạng thái", "Ngày nhập sản phẩm");
        System.out.printf("|   %-35s%-35s%-30s%-35s%-30s\n", product.getTitle(), product.getDescriptions(), product.getCatalog().getCatalogName(), status, stringDate);
        System.out.println("|   Color: ");
        for (Color col : product.getColor()) {
            if (col.isColorStatus()) {
                System.out.print("|   " + col.getColorName() + "\t");
            }
        }
        System.out.println();
        System.out.println("|   Size: ");
        for (Size size : product.getSize()) {
            if (size.isSizeStatus()) {
                System.out.print("|   " + size.getSizeName() + "\t");
            }
        }
        System.out.println();
        System.out.println("*----------------------------------------------------------------------------------------------------------------------------------------------------------------------------*");
    }


    @Override
    public boolean update(Product product) {
        List<Product> listProduct = readFromFile();
        if (listProduct == null) {
            listProduct = new ArrayList<>();
        }
        boolean result = false;
        for (int i = 0; i < listProduct.size(); i++) {
            if (listProduct.get(i).getProductID().equals(product.getProductID())) {
                listProduct.set(i, product);
                result = true;
                break;
            }
        }
        if (result && writeToFile(listProduct)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public List<Product> readFromFile() {
        FileImple<Product> fileImple = new FileImple<>();
        return fileImple.readFromFile(DataURL.URL_PRODUCT_FILE);
    }

    @Override
    public boolean writeToFile(List<Product> list) {
        FileImple<Product> fileImple = new FileImple<>();
        return fileImple.writeToFile(list, DataURL.URL_PRODUCT_FILE);
    }

    public float calExportPrice(Product pro) {
        float exportPrice = pro.getPrice() * ((float) (100 - pro.getDiscount()) / 100);
        return exportPrice;
    }

    public boolean delete(String id) {
        List<Product> listProduct = readFromFile();
        if (listProduct == null) {
            listProduct = new ArrayList<>();
        }
        boolean result = false;
        for (int i = 0; i < listProduct.size(); i++) {
            if (listProduct.get(i).getProductID().equals(id)) {
                listProduct.get(i).setStatus(false);
                result = true;
                break;
            }
        }
        if (result && writeToFile(listProduct)) {
            return true;
        }
        return false;
    }
}
