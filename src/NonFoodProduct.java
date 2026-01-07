public class NonFoodProduct extends Product {

    private String category;

    public NonFoodProduct(int productId, String name, double price, int stockQuantity, String category) {
        super(productId, name, price, stockQuantity);
        this.category = category;
    }

    @Override
    public String toString() {
        return super.toString() + ", category='" + category + "'";
    }
}

