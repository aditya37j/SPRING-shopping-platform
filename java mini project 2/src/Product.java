public class Product {
    private String productId;
    private String productName;
    private String brandName;
    private double price;
    private int stock;
    private String description;

    public Product(String productId, String productName, String brandName, double price, int stock, String description) {
        this.productId = productId;
        this.productName = productName;
        this.brandName = brandName;
        this.price = price;
        this.stock = stock;
        this.description = description;
    }

    // Getters for product details
    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getBrandName() {
        return brandName;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getDescription() {
        return description;
    }
}
