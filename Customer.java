/**
 * Represents a customer who shops in the store.
 * 
 * OOP Concepts:
 * - Classes & Objects: Models real-world customer identity and actions.
 * - Encapsulation: Protects customer profile details and maintains customer-specific Cart state.
 */
public class Customer {
    private String customerId;
    private String name;
    private String phoneNumber;
    private final Cart cart;

    public Customer(String customerId, String name) {
        this(customerId, name, "");
    }

    public Customer(String customerId, String name, String phoneNumber) {
        if (customerId == null || customerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer ID cannot be empty.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty.");
        }

        this.customerId = customerId.trim();
        this.name = name.trim();
        this.phoneNumber = phoneNumber != null ? phoneNumber.trim() : "";
        this.cart = new Cart(); // Encapsulated cart instance for this customer
    }

    // Overloaded constructor for backwards compatibility if needed
    public Customer(String customerId, String name, String email, String phoneNumber) {
        this(customerId, name, phoneNumber);
    }

    // Getters and Setters
    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty.");
        }
        this.name = name.trim();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber != null ? phoneNumber.trim() : "";
    }

    public Cart getCart() {
        return cart;
    }

    // Convenient delegate methods for customer cart operations
    public void addToCart(Product product) {
        cart.addProduct(product);
    }

    public void addToCart(Product product, int quantity) {
        cart.addProduct(product, quantity);
    }

    public void removeFromCart(String productId) {
        cart.removeProduct(productId);
    }

    public void applyDiscount(Discount discount) {
        cart.applyDiscount(discount);
    }

    /**
     * Completes customer checkout by processing payment and generating an itemized bill.
     */
    public Bill checkout(PaymentMethod paymentMethod) {
        if (cart.isEmpty()) {
            System.out.println("[FAILED] Checkout aborted: Shopping cart is empty.");
            return null;
        }

        double totalAmount = cart.calculateTotal();
        Payment payment = new Payment(totalAmount, paymentMethod);
        boolean isPaid = payment.executePayment();

        if (!isPaid) {
            System.out.println("[FAILED] Checkout aborted: Payment failed.");
            return null;
        }

        // Deduct inventory stock for purchased products
        for (CartItem item : cart.getItems()) {
            item.getProduct().deductStock(item.getQuantity());
        }

        // Generate the final bill
        Bill bill = new Bill(this, cart, payment);

        // Clear cart after successful checkout
        cart.clear();
        return bill;
    }

    @Override
    public String toString() {
        return String.format("%s (ID: %s)", name, customerId);
    }
}
