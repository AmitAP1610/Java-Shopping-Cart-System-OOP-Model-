/**
 * Implements a flat monetary discount when orders exceed a certain threshold.
 * Example: $30 off on orders of $150 or more.
 * 
 * OOP Concept: Polymorphism & Interface Implementation
 */
public class FlatDiscount implements Discount {
    private double discountAmount;
    private double minimumSpend;

    public FlatDiscount(double discountAmount, double minimumSpend) {
        if (discountAmount <= 0) {
            throw new IllegalArgumentException("Discount amount must be positive.");
        }
        if (minimumSpend < 0) {
            throw new IllegalArgumentException("Minimum spend cannot be negative.");
        }
        this.discountAmount = discountAmount;
        this.minimumSpend = minimumSpend;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public double getMinimumSpend() {
        return minimumSpend;
    }

    @Override
    public double calculateDiscount(double subtotal) {
        if (subtotal >= minimumSpend) {
            // Cannot discount more than the order value itself
            return Math.min(discountAmount, subtotal);
        }
        return 0.0;
    }

    @Override
    public String getDescription() {
        return String.format("₹%.2f Flat Discount on orders over ₹%.2f", discountAmount, minimumSpend);
    }
}

