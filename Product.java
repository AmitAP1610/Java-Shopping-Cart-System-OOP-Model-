/**
 * Represents a product available in the shopping store.
 * 
 * OOP Concept: Encapsulation
 * - All fields are private.
 * - Accessed and mutated through getters and setters with validation rules.
 */
public class Product {
    private String id;
    private String name;
    private double price;
    private int stockQuantity;

    public Product(String id, String name, double price, int stockQuantity) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty.");
        }
        if (price < 0.0) {
            throw new IllegalArgumentException("Product price cannot be negative.");
        }
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative.");
        }

        this.id = id.trim();
        this.name = name.trim();
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    // Getters and Setters with Encapsulation & Validation
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty.");
        }
        this.name = name.trim();
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0.0) {
            throw new IllegalArgumentException("Product price cannot be negative.");
        }
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative.");
        }
        this.stockQuantity = stockQuantity;
    }

    /**
     * Deducts stock when a purchase is finalized.
     */
    public boolean deductStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Deduct quantity must be greater than zero.");
        }
        if (quantity > this.stockQuantity) {
            return false;
        }
        this.stockQuantity -= quantity;
        return true;
    }

    /**
     * Restocks the product.
     */
    public void restock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Restock quantity must be greater than zero.");
        }
        this.stockQuantity += quantity;
    }

    @Override
    public String toString() {
        return String.format("%s (ID: %s) - ₹%.2f [Stock: %d]", name, id, price, stockQuantity);
    }
}

