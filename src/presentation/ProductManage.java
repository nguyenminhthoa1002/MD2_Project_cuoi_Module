package presentation;

import bussiness.entity.Catalog;
import bussiness.entity.Color;
import bussiness.entity.Product;
import bussiness.entity.Size;
import bussiness.imple.CatalogImple;
import bussiness.imple.ColorImple;
import bussiness.imple.ProductImple;
import bussiness.imple.SizeImple;
import config.ShopMessage;
import config.ShopValidate;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductManage {
    static ProductImple productImple = new ProductImple();

    public static void displayProduct(Scanner scanner) {
        boolean checkExist = true;
        do {
            System.out.println("*--------------------------------------------------------------------------------------------------------------------------*");
            System.out.println("|                       ******************** Quản lý sản phẩm ***************                                              |");
            System.out.println("|  1. Danh sách sản phẩm                                                                                                   |");
            System.out.println("|  2. Tạo mới sản phẩm                                                                                                     |");
            System.out.println("|  3. Cập nhật thông tin sản phẩm                                                                                          |");
            System.out.println("|  4. Xóa sản phẩm                                                                                                         |");
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
                    displayProductList();
                    break;
                case 2:
                    inputProduct(scanner);
                    break;
                case 3:
                    updateProduct(scanner);
                    break;
                case 4:
                    deleteProduct(scanner);
                    break;
                case 5:
                    checkExist = false;
                    break;
                default:
                    System.err.println(ShopMessage.NOTIFY_CHOICE_COLOR_MENU);
            }
        } while (checkExist);
    }

    public static void displayProductList() {
        List<Product> listProduct = productImple.readFromFile();
        if (listProduct == null) {
            listProduct = new ArrayList<>();
        }
        for (Product pro : listProduct) {
            productImple.display(pro);
        }
    }

    public static void inputProduct(Scanner scanner) {
        Product product = new Product();
        product = productImple.input(scanner);
        if (productImple.create(product)) {
            System.out.println(ShopMessage.NOTIFY_INPUT_SUCCESS);
        }
    }

    public static void updateProduct(Scanner scanner) {
        List<Product> listProduct = productImple.readFromFile();
        if (listProduct == null) {
            listProduct = new ArrayList<>();
        }
        System.out.println("Nhập id sản phẩm cần cập nhật: ");
        String updateProduct = scanner.nextLine();
        if (ShopValidate.checkEmpty(updateProduct)) {
            boolean check = false;
            for (Product pro : listProduct) {
                if (pro.getProductID().equals(updateProduct)) {
                    System.out.println("Cập nhật tên sản phẩm: ");
                    String updateName = scanner.nextLine();
                    if (!updateName.equals("") && updateName.trim().length() != 0) {
                        pro.setProductName(updateName);
                    }
                    System.out.println("Cập nhật giá sản phẩm: ");
                    do {
                        String updatePrice = scanner.nextLine();
                        if (!updatePrice.equals("") && updatePrice.trim().length() != 0) {
                            if (ShopValidate.checkFloatFormat(updatePrice)) {
                                pro.setPrice(Float.parseFloat(updatePrice));
                                break;
                            } else {
                                System.err.println(ShopMessage.NOTIFY_FLOAT_FORMAT);
                            }
                        }
                    } while (true);
                    System.out.println("Cập nhật phần trăm giảm giá: ");
                    do {
                        String updateDiscount = scanner.nextLine();
                        if (!updateDiscount.equals("") && updateDiscount.trim().length() != 0) {
                            if (ShopValidate.checkIntergerFormat(updateDiscount)) {
                                pro.setDiscount(Integer.parseInt(updateDiscount));
                                break;
                            } else {
                                System.err.println(ShopMessage.NOTIFY_INTEGER_FORMAT);
                            }
                        }
                    } while (true);
                    System.out.println("Cập nhật tiêu đề sản phẩm: ");
                    String updateTitlle = scanner.nextLine();
                    if (!updateTitlle.equals("") && updateTitlle.trim().length() != 0) {
                        pro.setDescriptions(updateTitlle);
                    }
                    System.out.println("Cập nhật mô tả sản phẩm: ");
                    String updateDescriptions = scanner.nextLine();
                    if (!updateDescriptions.equals("") && updateDescriptions.trim().length() != 0) {
                        pro.setDescriptions(updateDescriptions);
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
                                        pro.setStatus(true);
                                        break;
                                    case 2:
                                        pro.setStatus(false);
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

                    System.out.println("Cập nhật danh mục sản phẩm: ");
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
                    String choiceChangeColorStatus;
                    do {
                        choiceChangeColorStatus = scanner.nextLine();
                        if (!choiceChangeColorStatus.equals("") && choiceChangeColorStatus.trim().length() != 0) {
                            do {
                                if (ShopValidate.checkIntergerFormat(choiceChangeColorStatus)) {
                                    break;
                                } else {
                                    System.err.println(ShopMessage.NOTIFY_INTEGER_FORMAT);
                                }
                            } while (true);
                            if (Integer.parseInt(choiceChangeColorStatus) >= 1 && Integer.parseInt(choiceChangeColorStatus) <= listCatalog.size()) {
                                for (Catalog catExist : listCatalog) {
                                    if (catExist.getCatalogID() == Integer.parseInt(choiceChangeColorStatus)) {
                                        pro.setCatalog(catExist);
                                        break;
                                    }
                                }
                                break;
                            } else {
                                System.err.println(ShopMessage.NOTIFY_INPUT_PRODUCT_CATALOG);
                            }
                        } else {
                            break;
                        }
                    } while (true);

                    System.out.println("Cập nhật màu sắc sản phẩm: ");
                    System.out.println("Bạn có muốn cập nhật màu sắc không");
                    System.out.println("1. Có");
                    System.out.println("2. Không");
                    System.out.print("Sự lựa chọn của bạn: ");
                    ColorImple colorImple = new ColorImple();
                    List<Color> listColor = colorImple.readFromFile();
                    if (listColor == null) {
                        listColor = new ArrayList<>();
                    }
                    String choiceUpdateColorProduct;
                    do {
                        choiceUpdateColorProduct = scanner.nextLine();
                        if (ShopValidate.checkIntergerFormat(choiceUpdateColorProduct)) {
                            break;
                        } else {
                            System.err.println(ShopMessage.NOTIFY_INTEGER_FORMAT);
                        }
                    } while (true);
                    if (Integer.parseInt(choiceUpdateColorProduct) == 1) {
                        System.out.printf("%-15s%-35s%-30s\n", "Mã màu", "Tên màu", "Trạng thái");
                        String status = "Không hoạt động";
                        for (Color color : pro.getColor()) {
                            if (color.isColorStatus()) {
                                status = "Hoạt động";
                            }
                            System.out.printf("%-15d%-35s%-30s\n", color.getColorID(), color.getColorName(), status);
                        }
                        System.out.println("Chọn hình thức cập nhật");
                        System.out.println("1. Thêm màu");
                        System.out.println("2. Thay đổi trạng thái màu có sẵn");
                        System.out.print("Sự lựa chọn của bạn: ");
                        String choiceUpdateColor;
                        do {
                            choiceUpdateColor = scanner.nextLine();
                            if (ShopValidate.checkIntergerFormat(choiceUpdateColor)) {
                                break;
                            } else {
                                System.err.println(ShopMessage.NOTIFY_INTEGER_FORMAT);
                            }
                        } while (true);
                        if (Integer.parseInt(choiceUpdateColor) == 1) {
                            List<Color> listProductColor = new ArrayList<>(pro.getColor());
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
                            pro.setColor(listProductColor);
                        } else if (Integer.parseInt(choiceUpdateColor) == 2) {
                            System.out.printf("%-15s%-35s%-30s\n", "Mã màu", "Tên màu", "Trạng thái");
                            for (Color color : pro.getColor()) {
                                String statusColor = "Không hoạt động";
                                if (color.isColorStatus()) {
                                    statusColor = "Hoạt động";
                                }
                                System.out.printf("%-15d%-35s%-30s\n", color.getColorID(), color.getColorName(), statusColor);
                            }
                            System.out.println("Sự lựa chọn của bạn:");
                            String choiceChangeStatus;
                            do {
                                choiceChangeStatus = scanner.nextLine();
                                if (ShopValidate.checkIntergerFormat(choiceChangeStatus)) {
                                    break;
                                } else {
                                    System.err.println(ShopMessage.NOTIFY_INTEGER_FORMAT);
                                }
                            } while (true);
                            if (Integer.parseInt(choiceChangeStatus) >= 1 && Integer.parseInt(choiceChangeStatus) <= pro.getColor().size()) {
                                for (Color colExist : pro.getColor()) {
                                    if (colExist.getColorID() == Integer.parseInt(choiceChangeStatus)) {
                                        pro.setStatus(!pro.isStatus());
                                        break;
                                    }
                                }
                                break;
                            } else {
                                System.err.println(ShopMessage.NOTIFY_INPUT_PRODUCT_CATALOG);
                            }
                        } else {
                            System.err.println(ShopMessage.NOTIFY_CHOICE_STATUS);
                        }
                    }

                    System.out.println("Cập nhật kích cỡ sản phẩm: ");
                    System.out.println("Bạn có muốn cập nhật kích cỡ không");
                    System.out.println("1. Có");
                    System.out.println("2. Không");
                    System.out.print("Sự lựa chọn của bạn: ");
                    String choiceUpdateSizeProduct;
                    do {
                        choiceUpdateSizeProduct = scanner.nextLine();
                        if (ShopValidate.checkIntergerFormat(choiceUpdateSizeProduct)) {
                            break;
                        } else {
                            System.err.println(ShopMessage.NOTIFY_INTEGER_FORMAT);
                        }
                    } while (true);
                    if (Integer.parseInt(choiceUpdateSizeProduct) == 1) {
                        System.out.printf("%-15s%-35s%-30s\n", "Mã kích cỡ", "Tên kích cỡ", "Trạng thái");
                        String status = "Không hoạt động";
                        for (Size size : pro.getSize()) {
                            if (size.isSizeStatus()) {
                                status = "Hoạt động";
                            }
                            System.out.printf("%-15d%-35s%-30s\n", size.getSizeID(), size.getSizeName(), status);
                        }
                        System.out.println("Chọn hình thức cập nhật");
                        System.out.println("1. Thêm kích cỡ");
                        System.out.println("2. Thay đổi trạng thái kích cỡ có sẵn");
                        System.out.print("Sự lựa chọn của bạn: ");
                        String choiceUpdateSize;
                        do {
                            choiceUpdateSize = scanner.nextLine();
                            if (ShopValidate.checkIntergerFormat(choiceUpdateSize)) {
                                break;
                            } else {
                                System.err.println(ShopMessage.NOTIFY_INTEGER_FORMAT);
                            }
                        } while (true);
                        if (Integer.parseInt(choiceUpdateSize) == 1) {
                            List<Size> listProductSize = new ArrayList<>(pro.getSize());
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
                            pro.setSize(listProductSize);
                        } else if (Integer.parseInt(choiceUpdateSize) == 2) {
                            System.out.printf("%-15s%-35s%-30s\n", "Mã kích cỡ", "Tên kích cỡ", "Trạng thái");
                            for (Size size : pro.getSize()) {
                                String statusSize = "Không hoạt động";
                                for (Size si : pro.getSize()) {
                                    if (si.isSizeStatus()) {
                                        statusSize = "Hoạt động";
                                    }
                                    System.out.printf("%-15d%-35s%-30s\n", si.getSizeID(), si.getSizeName(), statusSize);
                                }
                            }
                            System.out.print("Sự lựa chọn của bạn: ");
                            String choiceChangeSize;
                            do {
                                choiceChangeSize = scanner.nextLine();
                                if (ShopValidate.checkIntergerFormat(choiceChangeSize)) {
                                    break;
                                } else {
                                    System.err.println(ShopMessage.NOTIFY_INTEGER_FORMAT);
                                }
                            } while (true);
                            if (Integer.parseInt(choiceChangeSize) >= 1 && Integer.parseInt(choiceChangeSize) <= pro.getSize().size()) {
                                for (Size size : pro.getSize()) {
                                    if (size.getSizeID() == Integer.parseInt(choiceChangeSize)) {
                                        pro.setStatus(!pro.isStatus());
                                        break;
                                    }
                                }
                                break;
                            } else {
                                System.err.println(ShopMessage.NOTIFY_INPUT_PRODUCT_CATALOG);
                            }
                        } else {
                            System.err.println(ShopMessage.NOTIFY_CHOICE_STATUS);
                        }
                    }
                }
                if (productImple.update(pro)) {
                    check = true;
                    break;
                }
            }
            if (check) {
                System.out.println(ShopMessage.NOTIFY_UPDATE_SUCCESS);
            }
        } else {
            System.err.println(ShopMessage.NOTIFY_INPUT_EMPTY);
        }
    }

    public static void deleteProduct(Scanner scanner) {
        List<Product> listProduct = productImple.readFromFile();
        if (listProduct == null) {
            listProduct = new ArrayList<>();
        }
        System.out.print("Nhập id sản phẩm muốn xóa");
        String deleteProductID = scanner.nextLine();
        if (productImple.delete(deleteProductID)) {
            System.out.println(ShopMessage.NOTIFY_DELETE_SUCCESS);
        }
    }
}

