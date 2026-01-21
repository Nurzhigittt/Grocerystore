package model;

public abstract class Product implements Discountable {

    protected int productId;
    protected String name;
    protected double price;
    protected int stockQuantity;

    public Product(int productId, String name, double price, int stockQuantity) {
        setProductId(productId);
        setName(name);
        setPrice(price);
        setStockQuantity(stockQuantity);
    }

    public abstract String getDetails();

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        if (productId <= 0)
            throw new IllegalArgumentException("Product ID must be positive");
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Product name cannot be empty");
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0)
            throw new IllegalArgumentException("Price cannot be negative");
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        if (stockQuantity < 0)
            throw new IllegalArgumentException("Stock cannot be negative");
        this.stockQuantity = stockQuantity;
    }

    public boolean isInStock() {
        return stockQuantity > 0;
    }

    public void restock(int amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Restock amount must be positive");
        stockQuantity += amount;
    }

    @Override
    public String toString() {
        return "ID: " + productId +
                " | Name: " + name +
                " | Price: " + price +
                " | Stock: " + stockQuantity;
    }
    @Override
    public void applyDiscount(double percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Discount must be between 0 and 100%");
        }
        this.price -= this.price * (percentage / 100.0);
    }
}

