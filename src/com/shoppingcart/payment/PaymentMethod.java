package com.shoppingcart.payment;

/**
 * Interface defining the contract for various payment methods.
 * 
 * OOP Concept: Interface & Polymorphism
 * - Any payment provider (Credit Card, PayPal, UPI, Cash) implements this interface.
 * - Callers can interact with any payment method uniformly without knowing the concrete details.
 */
public interface PaymentMethod {
    /**
     * Executes the payment transaction for the specified amount.
     * 
     * @param amount the total amount to charge
     * @return true if payment succeeds, false otherwise
     */
    boolean processPayment(double amount);

    /**
     * Returns the name of the payment mode (e.g., "Credit Card", "UPI").
     */
    String getMethodName();

    /**
     * Returns masked or summary account details for security and billing.
     */
    String getPaymentDetails();
}

