/**
 * Implements UPI (Unified Payments Interface) payment processing.
 * 
 * OOP Concept: Polymorphism & Interface Implementation
 */
public class UPIPayment implements PaymentMethod {
    private String upiId;

    public UPIPayment(String upiId) {
        if (upiId == null || !upiId.contains("@")) {
            throw new IllegalArgumentException("Invalid UPI ID (e.g. user@bank).");
        }
        this.upiId = upiId.trim();
    }

    public String getUpiId() {
        return upiId;
    }

    @Override
    public boolean processPayment(double amount) {
        if (amount <= 0) {
            System.out.println("[FAILED] UPI Payment Failed: Invalid amount ₹" + amount);
            return false;
        }
        System.out.println("[UPI] Sending payment request to UPI App...");
        System.out.printf("      Requesting ₹%.2f from VPA (%s)...%n", amount, upiId);
        System.out.println("[SUCCESS] UPI Payment Received and Verified!");
        return true;
    }

    @Override
    public String getMethodName() {
        return "UPI";
    }

    @Override
    public String getPaymentDetails() {
        return String.format("%s [VPA: %s]", getMethodName(), upiId);
    }
}
