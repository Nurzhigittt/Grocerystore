public class Product {
    protected int productId;
    protected String name;
    protected double price;
    protected int stockQuantity;

    public Product(int productId, String name, double price, int stockQuantity) {
        this.productId = productId;
        this.name = name;
        setPrice(price);
        setStockQuantity(stockQuantity);
    }

    public Product() {
        this(0, "Unknown", 0.0, 0);
    }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) {
        if (price >= 0) this.price = price;
        else this.price = 0;
    }

    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) {
        if (stockQuantity >= 0) this.stockQuantity = stockQuantity;
        else this.stockQuantity = 0;
    }

    public boolean isInStock() { return stockQuantity > 0; }

    public void restock(int amount) {
        if (amount > 0) stockQuantity += amount;
    }

    @Override
    public String toString() {
        return "ID: " + productId + " | Name: " + name + " | Price: " + price + " | Stock: " + stockQuantity;
    }
}