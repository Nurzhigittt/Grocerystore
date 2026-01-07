public class FoodProduct extends Product {

    private String expirationDate;

    public FoodProduct(int productId, String name, double price, int stockQuantity, String expirationDate) {
        super(productId, name, price, stockQuantity);
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return super.toString() + ", expirationDate='" + expirationDate + "'";
    }
}
