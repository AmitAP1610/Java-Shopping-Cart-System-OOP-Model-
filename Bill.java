import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Represents an itemized bill (invoice/receipt) generated after checkout.
 * 
 * OOP Concepts:
 * - Classes & Objects: Encapsulates bill state and rendering.
 * - Encapsulation: Stores an immutable snapshot of purchased items and amounts.
 */
public class Bill {
    private final String billId;
    private final String customerId;
    private final String customerName;
    private final List<CartItem> purchasedItems;
    private final double subtotal;
    private final double discountAmount;
    private final String discountDescription;
    private final double taxRatePercentage;
    private final double taxAmount;
    private final double totalAmount;
    private final Payment payment;
    private final LocalDateTime billDate;

    public Bill(Customer customer, Cart cart, Payment payment) {
        if (customer == null || cart == null || payment == null) {
            throw new IllegalArgumentException("Customer, cart, and payment details must not be null.");
        }

        this.billId = "INV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.customerId = customer.getCustomerId();
        this.customerName = customer.getName();

        // Defensive copy of cart items
        this.purchasedItems = new ArrayList<>(cart.getItems());

        this.subtotal = cart.calculateSubtotal();
        this.discountAmount = cart.calculateDiscountAmount();
        this.discountDescription = cart.getAppliedDiscount() != null 
                ? cart.getAppliedDiscount().getDescription() 
                : "None";
        this.taxRatePercentage = cart.getTaxRatePercentage();
        this.taxAmount = cart.calculateTax();
        this.totalAmount = cart.calculateTotal();
        this.payment = payment;
        this.billDate = LocalDateTime.now();
    }

    public String getBillId() {
        return billId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<CartItem> getPurchasedItems() {
        return Collections.unmodifiableList(purchasedItems);
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public String getDiscountDescription() {
        return discountDescription;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public Payment getPayment() {
        return payment;
    }

    public LocalDateTime getBillDate() {
        return billDate;
    }

    /**
     * Prints a beautifully formatted itemized tax invoice in Rupees.
     */
    public void printBill() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
        String formattedDate = billDate.format(formatter);

        System.out.println("\n" + "=".repeat(68));
        System.out.println("                     OFFICIAL TAX INVOICE                    ");
        System.out.println("=".repeat(68));
        System.out.printf(" Invoice No: %-20s  Date: %s%n", billId, formattedDate);
        System.out.printf(" Customer  : %-20s  ID  : %s%n", customerName, customerId);
        System.out.println("-".repeat(68));
        System.out.printf("%-4s %-28s %-12s %-5s %-14s%n", "No.", "Item Description", "Price (₹)", "Qty", "Total (₹)");
        System.out.println("-".repeat(68));

        int index = 1;
        for (CartItem item : purchasedItems) {
            System.out.printf("%-4d %-28s ₹%-11.2f %-5d ₹%-13.2f%n",
                    index++,
                    item.getProduct().getName(),
                    item.getProduct().getPrice(),
                    item.getQuantity(),
                    item.getTotalPrice());
        }

        System.out.println("-".repeat(68));
        System.out.printf(" Subtotal:                                         ₹%12.2f%n", subtotal);
        if (discountAmount > 0) {
            System.out.printf(" Discount (%-35s): -₹%12.2f%n", discountDescription, discountAmount);
        } else {
            System.out.printf(" Discount:                                          ₹%12.2f%n", 0.0);
        }
        System.out.printf(" GST (%.1f%%):                                      ₹%12.2f%n", taxRatePercentage, taxAmount);
        System.out.println("-".repeat(68));
        System.out.printf(" GRAND TOTAL:                                      ₹%12.2f%n", totalAmount);
        System.out.println("=".repeat(68));
        System.out.println(" PAYMENT DETAILS:");
        System.out.printf("   Status         : %s%n", payment.getStatus());
        System.out.printf("   Transaction ID : %s%n", payment.getTransactionId());
        System.out.printf("   Method         : %s%n", payment.getPaymentMethod().getMethodName());
        System.out.printf("   Account Info   : %s%n", payment.getPaymentMethod().getPaymentDetails());
        System.out.printf("   Paid at        : %s%n", payment.getFormattedTimestamp());
        System.out.println("=".repeat(68));
        System.out.println("           Thank you for shopping with us! Have a great day!          ");
        System.out.println("=".repeat(68) + "\n");
    }
}
