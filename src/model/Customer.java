package model;

public class Customer {

    private int customerId;
    private String name;
    private String membershipLevel;
    private double totalPurchases;

    public Customer(int customerId, String name, double totalPurchases) {
        if (customerId <= 0)
            throw new IllegalArgumentException("Customer ID must be positive");
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Customer name cannot be empty");

        this.customerId = customerId;
        this.name = name;
        setTotalPurchases(totalPurchases);
    }

    public void setTotalPurchases(double totalPurchases) {
        if (totalPurchases < 0)
            throw new IllegalArgumentException("Total purchases cannot be negative");
        this.totalPurchases = totalPurchases;
        updateMembership();
    }

    private void updateMembership() {
        membershipLevel = (totalPurchases >= 10000) ? "VIP" : "Regular";
    }

    public void addPurchase(double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Purchase amount must be positive");
        totalPurchases += amount;
        updateMembership();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Customer: " + name +
                " | Level: " + membershipLevel +
                " | Total: " + totalPurchases;
    }
}
