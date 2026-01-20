package menu;

import exception.InvalidInputException;
import model.*;
import java.util.ArrayList;
import java.util.Scanner;

public class StoreMenu implements Menu {

    private Scanner sc = new Scanner(System.in);
    private ArrayList<Product> inventory = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();

    public StoreMenu() {
        inventory.add(new FoodProduct(1, "Milk", 500, 10, "2026-01-01"));
        inventory.add(new NonFoodProduct(2, "Soap", 300, 20, "Health"));
        customers.add(new Customer(101, "Ali", 0));
    }

    @Override
    public void displayMenu() {
        System.out.println("""
        ==============================
        GROCERY STORE MENU
        ==============================
        1. Add Food Product
        2. Add Non-Food Product
        3. View All Products
        4. Show Product Details
        5. Process Sale
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
                int choice = sc.nextInt();
                sc.nextLine();

                if (choice < 0 || choice > 5) {
                    throw new InvalidInputException("Invalid menu choice");
                }

                switch (choice) {
                    case 1:
                        addFoodProduct();
                        break;

                    case 2:
                        addNonFoodProduct();
                        break;

                    case 3:
                        viewAllProducts();
                        break;

                    case 4:
                        showDetails();
                        break;

                    case 5:
                        processSale();
                        break;

                    case 0:
                        running = false;
                        break;
                }

            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Input error. Please enter a number.");
                sc.nextLine();
            }
        }

        sc.close();
    }

    private void addFoodProduct() {
        try {
            System.out.print("ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Name: ");
            String name = sc.nextLine();

            System.out.print("Price: ");
            double price = sc.nextDouble();

            System.out.print("Stock: ");
            int stock = sc.nextInt();
            sc.nextLine();

            System.out.print("Expiration date: ");
            String exp = sc.nextLine();

            inventory.add(new FoodProduct(id, name, price, stock, exp));
            System.out.println("Food product added");

        } catch (IllegalArgumentException e) {
            System.out.println("Input error: " + e.getMessage());
        }
    }

    private void addNonFoodProduct() {
        try {
            System.out.print("ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Name: ");
            String name = sc.nextLine();

            System.out.print("Price: ");
            double price = sc.nextDouble();

            System.out.print("Stock: ");
            int stock = sc.nextInt();
            sc.nextLine();

            System.out.print("Category: ");
            String category = sc.nextLine();

            inventory.add(new NonFoodProduct(id, name, price, stock, category));
            System.out.println("Non-food product added");

        } catch (IllegalArgumentException e) {
            System.out.println("Input error: " + e.getMessage());
        }
    }

    private void viewAllProducts() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty");
            return;
        }

        for (Product p : inventory) {
            System.out.println(p);
        }
    }

    private void showDetails() {
        for (Product p : inventory) {
            System.out.println(p.getName() + " → " + p.getDetails());
        }
    }

    private void processSale() {
        try {
            if (inventory.isEmpty()) {
                System.out.println("No products available");
                return;
            }

            Customer customer = customers.getFirst();
            Sale sale = new Sale(1, "2024-05-20", customer);

            Product product = inventory.getFirst();
            sale.addProduct(product);

            customer.addPurchase(sale.getTotalAmount());

            System.out.println("Sale completed");
            System.out.println("Total: " + sale.getTotalAmount());

        } catch (IllegalArgumentException e) {
            System.out.println("Sale error: " + e.getMessage());
        }
    }
}
