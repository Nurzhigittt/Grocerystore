public class Customer {
    private int customerId;
    private String name;
    private String membershipLevel;
    private double totalPurchases;

    public Customer(int customerId, String name, double totalPurchases) {
        this.customerId = customerId;
        this.name = name;
        setTotalPurchases(totalPurchases);
    }

    public int getCustomerId() { return customerId; }
    public String getName() { return name; }

    public double getTotalPurchases() { return totalPurchases; }
    public void setTotalPurchases(double totalPurchases) {
        if (totalPurchases >= 0) {
            this.totalPurchases = totalPurchases;
            updateMembership();
        }
    }

    private void updateMembership() {
        if (totalPurchases >= 10000) this.membershipLevel = "VIP";
        else this.membershipLevel = "Regular";
    }

    public void addPurchase(double amount) {
        if (amount > 0) {
            this.totalPurchases += amount;
            updateMembership();
        }
    }

    public String getMembershipLevel() { return membershipLevel; }

    @Override
    public String toString() {
        return "Customer: " + name + " | ID: " + customerId + " | Level: " + membershipLevel + " | Total Spent: " + totalPurchases;
    }
}