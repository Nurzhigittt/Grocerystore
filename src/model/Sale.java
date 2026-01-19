package model;

import java.util.ArrayList;

public class Sale {

    private int saleId;
    private String date;
    private Customer customer;
    private ArrayList<Product> items = new ArrayList<>();
    private double totalAmount;

    public Sale(int saleId, String date, Customer customer) {
        this.saleId = saleId;
        this.date = date;
        this.customer = customer;
    }

    public void addProduct(Product product) {
        if (!product.isInStock())
            throw new IllegalArgumentException("Product out of stock");

        items.add(product);
        totalAmount += product.getPrice();
        product.setStockQuantity(product.getStockQuantity() - 1);
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}
