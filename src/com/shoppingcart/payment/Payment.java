package com.shoppingcart.payment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Represents a payment transaction in the shopping system.
 * 
 * OOP Concepts:
 * - Classes & Objects: Models a concrete payment transaction.
 * - Encapsulation: Protects transaction status and generates immutable transaction IDs.
 * - Polymorphism: Uses the PaymentMethod interface to execute different payment logic dynamically.
 */
public class Payment {
    private final String transactionId;
    private final double amount;
    private final PaymentMethod paymentMethod;
    private final LocalDateTime timestamp;
    private String status; // PENDING, COMPLETED, FAILED

    public Payment(double amount, PaymentMethod paymentMethod) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Payment amount must be greater than zero.");
        }
        if (paymentMethod == null) {
            throw new IllegalArgumentException("Payment method cannot be null.");
        }

        // Generate unique transaction ID
        this.transactionId = "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.timestamp = LocalDateTime.now();
        this.status = "PENDING";
    }

    /**
     * Executes the payment using runtime polymorphism on the PaymentMethod.
     */
    public boolean executePayment() {
        System.out.printf("%n[Processing Transaction: %s | Method: %s | Amount: ₹%.2f]%n",
                transactionId, paymentMethod.getMethodName(), amount);

        boolean success = paymentMethod.processPayment(amount);
        if (success) {
            this.status = "COMPLETED";
        } else {
            this.status = "FAILED";
        }
        return success;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getFormattedTimestamp() {
        return timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getStatus() {
        return status;
    }

    public boolean isSuccessful() {
        return "COMPLETED".equalsIgnoreCase(status);
    }

    @Override
    public String toString() {
        return String.format("Transaction ID: %s | Status: %s | Amount: ₹%.2f | Method: %s",
                transactionId, status, amount, paymentMethod.getMethodName());
    }
}

