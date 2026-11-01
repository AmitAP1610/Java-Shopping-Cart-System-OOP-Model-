package com.shoppingcart.model;

/**
 * Represents an item line inside a shopping cart.
 * Links a Product to the quantity selected by the customer.
 * 
 * OOP Concept: Encapsulation
 * - Internal state is protected.
 * - Subtotal calculation is encapsulated within the object.
 */
public class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be at least 1.");
        }
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be at least 1.");
        }
        this.quantity = quantity;
    }

    public void addQuantity(int extraQuantity) {
        if (extraQuantity <= 0) {
            throw new IllegalArgumentException("Additional quantity must be greater than zero.");
        }
        this.quantity += extraQuantity;
    }

    /**
     * Calculates the total cost for this item line (unit price * quantity).
     */
    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return String.format("%-28s x %-3d @ ₹%-10.2f = ₹%.2f", 
            product.getName(), quantity, product.getPrice(), getTotalPrice());
    }
}

