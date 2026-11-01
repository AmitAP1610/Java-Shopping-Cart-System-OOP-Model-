package com.shoppingcart.discount;

/**
 * Implements a coupon code discount with a percentage rate and a maximum cap.
 * Example: Code "SAVE20" offers 20% off up to a maximum of $100.
 * 
 * OOP Concept: Polymorphism & Interface Implementation
 */
public class CouponDiscount implements Discount {
    private String couponCode;
    private double percentage;
    private double maxDiscountCap;

    public CouponDiscount(String couponCode, double percentage, double maxDiscountCap) {
        if (couponCode == null || couponCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Coupon code cannot be empty.");
        }
        if (percentage <= 0 || percentage > 100) {
            throw new IllegalArgumentException("Percentage must be between 0 and 100.");
        }
        if (maxDiscountCap <= 0) {
            throw new IllegalArgumentException("Max discount cap must be positive.");
        }
        this.couponCode = couponCode.trim().toUpperCase();
        this.percentage = percentage;
        this.maxDiscountCap = maxDiscountCap;
    }

    public String getCouponCode() {
        return couponCode;
    }

    @Override
    public double calculateDiscount(double subtotal) {
        if (subtotal <= 0) return 0.0;
        double rawDiscount = subtotal * (percentage / 100.0);
        return Math.min(rawDiscount, maxDiscountCap);
    }

    @Override
    public String getDescription() {
        return String.format("Coupon [%s]: %.1f%% off (capped at ₹%.2f)", couponCode, percentage, maxDiscountCap);
    }
}

