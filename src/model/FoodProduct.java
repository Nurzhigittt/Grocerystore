package model;

public class FoodProduct extends Product {

    private String expirationDate;

    public FoodProduct(int productId, String name, double price,
                       int stockQuantity, String expirationDate) {
        super(productId, name, price, stockQuantity);
        setExpirationDate(expirationDate);
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        if (expirationDate == null || expirationDate.isBlank())
            throw new IllegalArgumentException("Expiration date cannot be empty");
        this.expirationDate = expirationDate;
    }

    @Override
    public String getDetails() {
        return "Expires: " + expirationDate;
    }

    @Override
    public String toString() {
        return "[Food] " + super.toString() + " | " + getDetails();
    }
}
