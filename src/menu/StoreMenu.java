package menu;

import database.ProductDAO;
import model.*;

import java.util.List;
import java.util.Scanner;

public class StoreMenu implements Menu {

    private final Scanner sc = new Scanner(System.in);
    private final ProductDAO productDAO = new ProductDAO();

    @Override
    public void displayMenu() {
        System.out.println("""
        ==============================
        GROCERY STORE MENU
        ==============================
        1. Add Food Product
        2. Add Non-Food Product
        3. View All Products
        4. View Food Only
        5. View Non-Food Only
        6. Update Product
        7. Delete Product
        8. Search by Name
        9. Search by Price Range
        10. Apply Discount
        11. Polymorphism Demo
        0. Exit
        ==============================
        """);
    }

    @Override
    public void run() {
        boolean running = true;

        while (running) {
            displayMenu();
            try {
                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1 -> addFoodProduct();
                    case 2 -> addNonFoodProduct();
                    case 3 -> viewAllProducts();
                    case 4 -> viewFoodOnly();
                    case 5 -> viewNonFoodOnly();
                    case 6 -> updateProduct();
                    case 7 -> deleteProduct();
                    case 8 -> searchByName();
                    case 9 -> searchByPriceRange();
                    case 10 -> applyDiscount();
                    case 11 -> polymorphismDemo();
                    case 0 -> running = false;
                    default -> System.out.println("Invalid choice");
                }

            } catch (Exception e) {
                System.out.println("Input error.");
            }
        }
        sc.close();
    }

    private void addFoodProduct() {
        try {
            System.out.print("Name: ");
            String name = sc.nextLine();

            System.out.print("Price: ");
            double price = Double.parseDouble(sc.nextLine());

            System.out.print("Stock: ");
            int stock = Integer.parseInt(sc.nextLine());

            System.out.print("Expiration date (YYYY-MM-DD): ");
            String exp = sc.nextLine();

            FoodProduct product = new FoodProduct(0, name, price, stock, exp);
            productDAO.insertProduct(product);

        } catch (Exception e) {
            System.out.println("Failed to add food product.");
        }
    }

    private void addNonFoodProduct() {
        try {
            System.out.print("Name: ");
            String name = sc.nextLine();

            System.out.print("Price: ");
            double price = Double.parseDouble(sc.nextLine());

            System.out.print("Stock: ");
            int stock = Integer.parseInt(sc.nextLine());

            System.out.print("Category: ");
            String category = sc.nextLine();

            NonFoodProduct product = new NonFoodProduct(0, name, price, stock, category);
            productDAO.insertProduct(product);

        } catch (Exception e) {
            System.out.println("Failed to add non-food product.");
        }
    }

    private void viewAllProducts() {
        productDAO.getAllProduct();
    }

    private void updateProduct() {
        try {
            System.out.print("Enter Product ID to update: ");
            int id = Integer.parseInt(sc.nextLine());

            Product existing = productDAO.getProductById(id);
            if (existing == null) {
                System.out.println("Product not found.");
                return;
            }

            System.out.println("Current: " + existing);

            System.out.print("New Name [" + existing.getName() + "]: ");
            String newName = sc.nextLine();
            if (newName.isBlank()) newName = existing.getName();

            System.out.print("New Price [" + existing.getPrice() + "]: ");
            String priceInput = sc.nextLine();
            double newPrice = priceInput.isBlank()
                    ? existing.getPrice()
                    : Double.parseDouble(priceInput);

            System.out.print("New Stock [" + existing.getStockQuantity() + "]: ");
            String stockInput = sc.nextLine();
            int newStock = stockInput.isBlank()
                    ? existing.getStockQuantity()
                    : Integer.parseInt(stockInput);

            if (existing instanceof FoodProduct fp) {
                System.out.print("New Expiration [" + fp.getExpirationDate() + "]: ");
                String exp = sc.nextLine();
                if (exp.isBlank()) exp = fp.getExpirationDate();

                FoodProduct updated = new FoodProduct(id, newName, newPrice, newStock, exp);
                productDAO.updateFoodProduct(updated);

            } else if (existing instanceof NonFoodProduct nfp) {
                System.out.print("New Category [" + nfp.getCategory() + "]: ");
                String cat = sc.nextLine();
                if (cat.isBlank()) cat = nfp.getCategory();

                NonFoodProduct updated = new NonFoodProduct(id, newName, newPrice, newStock, cat);
                productDAO.updateNonFoodProduct(updated);
            }

        } catch (Exception e) {
            System.out.println("Update failed.");
        }
    }

    private void deleteProduct() {
        try {
            System.out.print("Enter Product ID to delete: ");
            int id = Integer.parseInt(sc.nextLine());

            Product product = productDAO.getProductById(id);
            if (product == null) {
                System.out.println("Product not found.");
                return;
            }

            System.out.println("Will be deleted:");
            System.out.println(product);

            System.out.print("Are you sure? (yes/no): ");
            String confirm = sc.nextLine();

            if (confirm.equalsIgnoreCase("yes")) {
                productDAO.deleteProduct(id);
            } else {
                System.out.println("Deletion cancelled.");
            }

        } catch (Exception e) {
            System.out.println("Delete failed.");
        }
    }

    private void searchByName() {
        System.out.print("Enter name: ");
        String name = sc.nextLine();

        List<Product> products = productDAO.searchByName(name);
        products.forEach(System.out::println);
    }

    private void searchByPriceRange() {
        System.out.print("Min price: ");
        double min = Double.parseDouble(sc.nextLine());

        System.out.print("Max price: ");
        double max = Double.parseDouble(sc.nextLine());

        List<Product> products = productDAO.searchByPriceRange(min, max);
        products.forEach(System.out::println);
    }


    private void applyDiscount() {
        try {
            System.out.print("Enter Product ID: ");
            int id = Integer.parseInt(sc.nextLine());

            Product product = productDAO.getProductById(id);
            if (product == null) {
                System.out.println("Product not found.");
                return;
            }

            System.out.println("Current price: " + product.getPrice());

            System.out.print("Enter discount %: ");
            double percent = Double.parseDouble(sc.nextLine());

            if (percent < 0 || percent > 100) {
                System.out.println("Invalid discount.");
                return;
            }

            productDAO.applyDiscount(id, percent);

        } catch (Exception e) {
            System.out.println("Failed to apply discount.");
        }
    }
    private void viewFoodOnly(){
        productDAO.getProductsByType("FOOD").forEach(System.out::println);
    }
    private void viewNonFoodOnly(){
        productDAO.getProductsByType("NON_FOOD").forEach(System.out::println);

    }
    private void polymorphismDemo() {
        System.out.println("Polymorphism demo: getDetails() is called at runtime");
        productDAO.getAllProductsList().forEach(p ->
                System.out.println(p.getName() + " -> " + p.getDetails())
        );
    }

}
