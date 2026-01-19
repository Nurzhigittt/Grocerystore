package model;

public class NonFoodProduct extends Product {

    private String category;

    public NonFoodProduct(int productId, String name, double price,
                          int stockQuantity, String category) {
        super(productId, name, price, stockQuantity);
        setCategory(category);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        if (category == null || category.isBlank())
            throw new IllegalArgumentException("Category cannot be empty");
        this.category = category;
    }

    @Override
    public String getDetails() {
        return "Category: " + category;
    }

    @Override
    public String toString() {
        return "[Non-Food] " + super.toString() + " | " + getDetails();
    }
}
