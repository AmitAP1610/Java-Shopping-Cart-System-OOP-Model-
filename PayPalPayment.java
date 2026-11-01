/**
 * Implements PayPal payment processing.
 * 
 * OOP Concept: Polymorphism & Interface Implementation
 */
public class PayPalPayment implements PaymentMethod {
    private String email;

    public PayPalPayment(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid PayPal email address.");
        }
        this.email = email.trim();
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean processPayment(double amount) {
        if (amount <= 0) {
            System.out.println("[FAILED] PayPal Payment Failed: Invalid amount ₹" + amount);
            return false;
        }
        System.out.println("[PAYPAL] Connecting to PayPal Gateway...");
        System.out.printf("         Debiting ₹%.2f from account (%s)...%n", amount, email);
        System.out.println("[SUCCESS] PayPal Transaction Completed Successfully!");
        return true;
    }

    @Override
    public String getMethodName() {
        return "PayPal";
    }

    @Override
    public String getPaymentDetails() {
        return String.format("%s [Account: %s]", getMethodName(), email);
    }
}
