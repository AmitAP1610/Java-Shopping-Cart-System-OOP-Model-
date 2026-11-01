package com.shoppingcart.payment;

/**
 * Implements Credit Card payment processing.
 * 
 * OOP Concept: Encapsulation & Polymorphism
 * - Private sensitive fields with secure masking.
 * - Implements the PaymentMethod interface.
 */
public class CreditCardPayment implements PaymentMethod {
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv;

    public CreditCardPayment(String cardNumber, String cardHolderName, String expiryDate, String cvv) {
        if (cardNumber == null || cardNumber.replaceAll("\\s+", "").length() < 12) {
            throw new IllegalArgumentException("Invalid credit card number.");
        }
        if (cardHolderName == null || cardHolderName.trim().isEmpty()) {
            throw new IllegalArgumentException("Cardholder name is required.");
        }
        if (cvv == null || cvv.trim().length() < 3) {
            throw new IllegalArgumentException("Invalid CVV.");
        }
        this.cardNumber = cardNumber.replaceAll("\\s+", "");
        this.cardHolderName = cardHolderName.trim();
        this.expiryDate = expiryDate;
        this.cvv = cvv.trim();
    }

    @Override
    public boolean processPayment(double amount) {
        if (amount <= 0) {
            System.out.println("[FAILED] Credit Card Payment Failed: Invalid amount ₹" + amount);
            return false;
        }
        // Simulating bank authorization
        System.out.println("[CARD] Contacting Card Payment Gateway...");
        System.out.printf("       Charging ₹%.2f to Card (%s)...%n", amount, getMaskedCardNumber());
        System.out.println("[SUCCESS] Payment Authorized and Processed Successfully!");
        return true;
    }

    @Override
    public String getMethodName() {
        return "Credit/Debit Card";
    }

    @Override
    public String getPaymentDetails() {
        return String.format("%s [Holder: %s, Card: %s]", getMethodName(), cardHolderName, getMaskedCardNumber());
    }

    private String getMaskedCardNumber() {
        if (cardNumber.length() <= 4) {
            return "****";
        }
        String lastFour = cardNumber.substring(cardNumber.length() - 4);
        return "****-****-****-" + lastFour;
    }
}
