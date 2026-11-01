/**
 * Implements Cash / Cash-On-Delivery payment processing.
 * 
 * OOP Concept: Polymorphism & Interface Implementation
 */
public class CashPayment implements PaymentMethod {
    private String deliveryAddress;

    public CashPayment(String deliveryAddress) {
        if (deliveryAddress == null || deliveryAddress.trim().isEmpty()) {
            throw new IllegalArgumentException("Delivery address cannot be empty for Cash-on-Delivery.");
        }
        this.deliveryAddress = deliveryAddress.trim();
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    @Override
    public boolean processPayment(double amount) {
        if (amount <= 0) {
            System.out.println("[FAILED] Cash Payment Failed: Invalid amount ₹" + amount);
            return false;
        }
        System.out.printf("[CASH] Cash-on-Delivery registered for ₹%.2f at address: %s%n", amount, deliveryAddress);
        System.out.println("[SUCCESS] Order confirmed for cash collection on delivery!");
        return true;
    }

    @Override
    public String getMethodName() {
        return "Cash on Delivery";
    }

    @Override
    public String getPaymentDetails() {
        return String.format("%s [Deliver to: %s]", getMethodName(), deliveryAddress);
    }
}
