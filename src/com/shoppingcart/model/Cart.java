package com.shoppingcart.model;

import com.shoppingcart.discount.Discount;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a customer's shopping cart.
 * 
 * OOP Concepts:
 * - Encapsulation: Cart items and discount strategy are protected private fields.
 *   Access is managed through validated methods.
 * - Polymorphism (Compile-time): Method overloading for addProduct().
 * - Polymorphism (Runtime): Works with any Discount implementation via the Discount interface.
 */
public class Cart {
    private final Map<String, CartItem> items; // Keyed by Product ID to prevent duplicates
    private Discount appliedDiscount;         // Polymorphic reference to Discount interface
    private double taxRatePercentage;          // e.g., 5.0 for 5% tax

    public Cart() {
        this.items = new LinkedHashMap<>();
        this.appliedDiscount = null;
        this.taxRatePercentage = 5.0; // Default 5% sales tax
    }

    public Cart(double taxRatePercentage) {
        if (taxRatePercentage < 0) {
            throw new IllegalArgumentException("Tax rate cannot be negative.");
        }
        this.items = new LinkedHashMap<>();
        this.appliedDiscount = null;
        this.taxRatePercentage = taxRatePercentage;
    }

    // ==========================================
    // POLYMORPHISM: METHOD OVERLOADING
    // ==========================================

    /**
     * Overloaded method: Adds 1 unit of the product to the cart.
     */
    public boolean addProduct(Product product) {
        return addProduct(product, 1);
    }

    /**
     * Overloaded method: Adds specified quantity of the product to the cart.
     */
    public boolean addProduct(Product product, int quantity) {
        if (product == null) {
            System.out.println("[ERROR] Cannot add null product to cart.");
            return false;
        }
        if (quantity <= 0) {
            System.out.println("[ERROR] Quantity must be greater than zero.");
            return false;
        }

        int currentInCart = items.containsKey(product.getId()) ? items.get(product.getId()).getQuantity() : 0;
        int totalRequested = currentInCart + quantity;

        // Check stock availability
        if (totalRequested > product.getStockQuantity()) {
            System.out.printf("[OUT OF STOCK] Cannot add %d x '%s'. Only %d available in stock (already in cart: %d).%n",
                    quantity, product.getName(), product.getStockQuantity(), currentInCart);
            return false;
        }

        if (items.containsKey(product.getId())) {
            items.get(product.getId()).addQuantity(quantity);
        } else {
            items.put(product.getId(), new CartItem(product, quantity));
        }

        System.out.printf("[ADDED] %d x '%s' (₹%.2f each) to cart.%n", quantity, product.getName(), product.getPrice());
        return true;
    }

    /**
     * Removes a product entirely from the cart by its ID.
     */
    public boolean removeProduct(String productId) {
        if (productId == null || !items.containsKey(productId)) {
            System.out.println("[WARNING] Product not found in cart.");
            return false;
        }
        CartItem removed = items.remove(productId);
        System.out.printf("[REMOVED] '%s' from cart.%n", removed.getProduct().getName());
        return true;
    }

    /**
     * Updates the quantity of a product in the cart.
     */
    public boolean updateQuantity(String productId, int newQuantity) {
        if (!items.containsKey(productId)) {
            System.out.println("[WARNING] Product not found in cart.");
            return false;
        }
        if (newQuantity <= 0) {
            return removeProduct(productId);
        }

        CartItem item = items.get(productId);
        if (newQuantity > item.getProduct().getStockQuantity()) {
            System.out.printf("[STOCK LIMIT] Cannot set quantity to %d. Only %d in stock.%n", 
                    newQuantity, item.getProduct().getStockQuantity());
            return false;
        }

        item.setQuantity(newQuantity);
        System.out.printf("[UPDATED] '%s' quantity set to %d.%n", item.getProduct().getName(), newQuantity);
        return true;
    }

    // ==========================================
    // TOTAL CALCULATIONS & DISCOUNT HANDLING
    // ==========================================

    /**
     * Calculates the sum of prices of all items in the cart before discounts and tax.
     */
    public double calculateSubtotal() {
        double subtotal = 0.0;
        for (CartItem item : items.values()) {
            subtotal += item.getTotalPrice();
        }
        return subtotal;
    }

    /**
     * Applies a discount policy using runtime polymorphism.
     */
    public void applyDiscount(Discount discount) {
        this.appliedDiscount = discount;
        if (discount != null) {
            System.out.println("[DISCOUNT] Applied Discount: " + discount.getDescription());
        }
    }

    /**
     * Removes any currently applied discount.
     */
    public void removeDiscount() {
        this.appliedDiscount = null;
        System.out.println("[DISCOUNT] Discount removed.");
    }

    /**
     * Calculates the monetary discount amount.
     */
    public double calculateDiscountAmount() {
        if (appliedDiscount == null) {
            return 0.0;
        }
        double subtotal = calculateSubtotal();
        return appliedDiscount.calculateDiscount(subtotal);
    }

    /**
     * Calculates tax on (subtotal - discount).
     */
    public double calculateTax() {
        double taxableAmount = Math.max(0.0, calculateSubtotal() - calculateDiscountAmount());
        return taxableAmount * (taxRatePercentage / 100.0);
    }

    /**
     * Calculates final total payable amount: (Subtotal - Discount) + Tax.
     */
    public double calculateTotal() {
        double discountedSubtotal = Math.max(0.0, calculateSubtotal() - calculateDiscountAmount());
        return discountedSubtotal + calculateTax();
    }

    // ==========================================
    // ENCAPSULATION & UTILITIES
    // ==========================================

    /**
     * Returns an unmodifiable list of items to prevent external tampering.
     */
    public List<CartItem> getItems() {
        return Collections.unmodifiableList(new ArrayList<>(items.values()));
    }

    public int getTotalItemCount() {
        int count = 0;
        for (CartItem item : items.values()) {
            count += item.getQuantity();
        }
        return count;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {
        items.clear();
        appliedDiscount = null;
    }

    public Discount getAppliedDiscount() {
        return appliedDiscount;
    }

    public double getTaxRatePercentage() {
        return taxRatePercentage;
    }

    public void setTaxRatePercentage(double taxRatePercentage) {
        if (taxRatePercentage < 0) {
            throw new IllegalArgumentException("Tax rate cannot be negative.");
        }
        this.taxRatePercentage = taxRatePercentage;
    }

    public void printCartPreview() {
        System.out.println("\n--- Current Cart Contents ---");
        if (items.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        for (CartItem item : items.values()) {
            System.out.println("  " + item);
        }
        System.out.printf("Subtotal: ₹%.2f | Discount: -₹%.2f | GST (%.1f%%): ₹%.2f | Est. Total: ₹%.2f%n",
                calculateSubtotal(), calculateDiscountAmount(), taxRatePercentage, calculateTax(), calculateTotal());
        System.out.println("-----------------------------");
    }
}
