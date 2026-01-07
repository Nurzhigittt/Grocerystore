import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Product> inventory = new ArrayList<>();
        ArrayList<Customer> customers = new ArrayList<>();

        inventory.add(new FoodProduct(1, "Milk", 500, 10, "2026-01-01"));
        inventory.add(new NonFoodProduct(2, "Soap", 300, 20, "Health"));
        customers.add(new Customer(101, "Ali", 0));

        boolean running = true;
        while (running) {
            System.out.println("\n1. Add Food\n2. Add Non-Food\n3. View All\n4. Show Specifics\n5. Process Sale\n0. Exit");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    inventory.add(new FoodProduct(sc.nextInt(), sc.next(), sc.nextDouble(), sc.nextInt(), sc.next()));
                    break;
                case 2:
                    inventory.add(new NonFoodProduct(sc.nextInt(), sc.next(), sc.nextDouble(), sc.nextInt(), sc.next()));
                    break;
                case 3:
                    for (Product p : inventory) System.out.println(p);
                    break;
                case 4:
                    for (Product p : inventory) {
                        if (p instanceof FoodProduct) System.out.println(p.getName() + " expires: " + ((FoodProduct)p).getExpirationDate());
                        if (p instanceof NonFoodProduct) System.out.println(p.getName() + " cat: " + ((NonFoodProduct)p).getCategory());
                    }
                    break;
                case 5:
                    Customer c = customers.getFirst();
                    Sale s = new Sale(1, "2024-05-20", c);
                    s.addProduct(inventory.getFirst());
                    c.addPurchase(s.getTotalAmount());
                    System.out.println(s);
                    break;
                case 0:
                    running = false;
                    break;
            }
        }
        sc.close();
    }
}