public class Main {
    public static void main(String[] args) {

        System.out.println("=== Grocery Store Management System ===\n");

        Product p1 = new Product(1, "Milk", 500, 10);
        Product p2 = new Product(2, "Bread", 300, 0);
        Product p3 = new Product();

        Customer c1 = new Customer(101, "Ali", "Regular", 8000);
        Customer c2 = new Customer(102, "Dana", "VIP", 15000);

        Sale s1 = new Sale(1001, "Ali", 0, "2025-12-24");

        System.out.println("--- PRODUCTS ---");
        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);

        System.out.println("\n--- CUSTOMERS ---");
        System.out.println(c1);
        System.out.println(c2);

        System.out.println("\n--- SALE ---");
        s1.addItem(p1.getPrice());
        s1.addItem(300);
        System.out.println(s1);

        System.out.println("\n--- METHODS TEST ---");
        System.out.println("Milk in stock: " + p1.isInStock());
        p2.restock(20);
        System.out.println("Bread after restock: " + p2);

        c1.addPurchase(s1.calculateTotal());
        System.out.println("Ali total purchases: " + c1.getTotalPurchases());
        System.out.println("Ali VIP: " + c1.isVIP());

        System.out.println("\n=== Program Complete ===");
    }
}
