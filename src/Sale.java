import java.util.ArrayList;

public class Sale {
    private int saleId;
    private String date;
    private Customer customer;
    private ArrayList<Product> items;
    private double totalAmount;

    public Sale(int saleId, String date, Customer customer) {
        this.saleId = saleId;
        this.date = date;
        this.customer = customer;
        this.items = new ArrayList<>();
        this.totalAmount = 0;
    }

    public void addProduct(Product product) {
        if (product.isInStock()) {
            items.add(product);
            totalAmount += product.getPrice();
            product.setStockQuantity(product.getStockQuantity() - 1);
        }
    }

    public double getTotalAmount() { return totalAmount; }

    @Override
    public String toString() {
        String result = "Sale ID: " + saleId + " | Date: " + date + " | Customer: " + customer.getName() + "\nItems:\n";
        for (Product p : items) {
            result += " - " + p.getName() + ": " + p.getPrice() + "\n";
        }
        result += "Total: " + totalAmount;
        return result;
    }
}