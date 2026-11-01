package com.shoppingcart.discount;

/**
 * Implements a percentage-based discount (e.g., 10% off total).
 * 
 * OOP Concept: Polymorphism & Interface Implementation
 */
public class PercentageDiscount implements Discount {
    private double percentage;

    public PercentageDiscount(double percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Percentage must be between 0 and 100.");
        }
        this.percentage = percentage;
    }

    public double getPercentage() {
        return percentage;
    }

    @Override
    public double calculateDiscount(double subtotal) {
        if (subtotal <= 0) return 0.0;
        return subtotal * (percentage / 100.0);
    }

    @Override
    public String getDescription() {
        return String.format("%.1f%% Off Promotional Discount", percentage);
    }
}

