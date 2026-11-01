package com.shoppingcart.ui;

import com.shoppingcart.discount.*;
import com.shoppingcart.model.*;
import com.shoppingcart.payment.*;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Modern, Colorful Graphical User Interface (GUI) for the Shopping Cart System.
 * Customized for Customer: Amit (Prices in INR ₹, Email removed).
 */
public class ShoppingAppGUI extends JFrame {

    // Modern Color Palette
    private static final Color COLOR_PRIMARY_NAVY   = new Color(30, 41, 59);    // #1e293b
    private static final Color COLOR_ACCENT_BLUE    = new Color(37, 99, 235);   // #2563eb
    private static final Color COLOR_SUCCESS_GREEN  = new Color(22, 163, 74);   // #16a34a
    private static final Color COLOR_DANGER_RED     = new Color(220, 38, 38);   // #dc2626
    private static final Color COLOR_WARNING_ORANGE = new Color(217, 119, 6);   // #d97706
    private static final Color COLOR_BG_LIGHT       = new Color(248, 250, 252); // #f8fafc
    private static final Color COLOR_CARD_BG        = Color.WHITE;
    private static final Color COLOR_BORDER_GRAY    = new Color(226, 232, 240); // #e2e8f0

    // Model entities
    private final Customer customer;
    private final List<Product> inventory;

    // GUI Components
    private JTable productTable;
    private DefaultTableModel productTableModel;
    private JTable cartTable;
    private DefaultTableModel cartTableModel;

    private JSpinner quantitySpinner;
    private JComboBox<String> discountComboBox;
    private JComboBox<String> paymentMethodComboBox;

    private JLabel subtotalLabel;
    private JLabel discountLabel;
    private JLabel taxLabel;
    private JLabel grandTotalLabel;

    public ShoppingAppGUI() {
        // Initialize Customer: Amit (Email removed)
        this.customer = new Customer("AMIT-01", "Amit", "+91-9876543210");
        this.inventory = new ArrayList<>();
        initializeInventory();

        // Setup Main Window Frame
        setTitle("✨ Java Shopping Cart System - Interactive Store (₹ INR)");
        setSize(1020, 720);
        setMinimumSize(new Dimension(920, 640));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen
        getContentPane().setBackground(COLOR_BG_LIGHT);

        // Build the layout
        initUI();
        refreshCartDisplay();
    }

    private void initializeInventory() {
        // Real-world products with official Indian market prices in Rupees (₹)
        inventory.add(new Product("P101", "Apple MacBook Air 13\" (M3, 16GB)", 114900.00, 8));
        inventory.add(new Product("P102", "Google Pixel 9 Pro (128GB)", 109999.00, 12));
        inventory.add(new Product("P103", "Sony WH-1000XM5 ANC Headphones", 29990.00, 15));
        inventory.add(new Product("P104", "Apple Watch Series 10 (42mm GPS)", 46900.00, 10));
        inventory.add(new Product("P105", "Samsung 990 PRO 1TB Gen4 SSD", 10999.00, 25));
        inventory.add(new Product("P106", "Logitech MX Master 3S Mouse", 8995.00, 20));
        inventory.add(new Product("P107", "Keychron K2 Mechanical Keyboard", 7499.00, 14));
        inventory.add(new Product("P108", "Anker 555 8-in-1 USB-C Hub", 4499.00, 30));
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(14, 14));
        mainPanel.setBackground(COLOR_BG_LIGHT);
        mainPanel.setBorder(new EmptyBorder(14, 14, 14, 14));

        // =========================================================================
        // 1. TOP HEADER PANEL (Deep Navy with Account Badge)
        // =========================================================================
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(COLOR_PRIMARY_NAVY);
        headerPanel.setBorder(new EmptyBorder(16, 20, 16, 20));

        JPanel headerLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        headerLeft.setOpaque(false);

        JLabel logoIcon = new JLabel("🛒");
        logoIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 26));

        JLabel titleLabel = new JLabel("Online Shopping System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);

        JLabel badgeLabel = new JLabel(" INR (₹) Store ");
        badgeLabel.setOpaque(true);
        badgeLabel.setBackground(COLOR_ACCENT_BLUE);
        badgeLabel.setForeground(Color.WHITE);
        badgeLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        badgeLabel.setBorder(new EmptyBorder(3, 8, 3, 8));

        headerLeft.add(logoIcon);
        headerLeft.add(titleLabel);
        headerLeft.add(badgeLabel);

        // Header Right: Account Name "Amit" (Email removed)
        JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        headerRight.setOpaque(false);
        JLabel userLabel = new JLabel("👤 Account: " + customer.getName());
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        userLabel.setForeground(Color.WHITE);

        JLabel statusBadge = new JLabel(" Verified ");
        statusBadge.setOpaque(true);
        statusBadge.setBackground(COLOR_SUCCESS_GREEN);
        statusBadge.setForeground(Color.WHITE);
        statusBadge.setFont(new Font("Segoe UI", Font.BOLD, 10));
        statusBadge.setBorder(new EmptyBorder(2, 6, 2, 6));

        headerRight.add(userLabel);
        headerRight.add(statusBadge);

        headerPanel.add(headerLeft, BorderLayout.WEST);
        headerPanel.add(headerRight, BorderLayout.EAST);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // =========================================================================
        // 2. CENTER SPLIT: STORE INVENTORY (LEFT) & SHOPPING CART (RIGHT)
        // =========================================================================
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 16, 0));
        contentPanel.setOpaque(false);

        // --- Left: Products Panel ---
        JPanel productsPanel = createCardPanel("📦 Store Products");

        String[] prodColumns = {"ID", "Product Name", "Price (₹)", "Stock"};
        productTableModel = new DefaultTableModel(prodColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        populateProductTable();

        productTable = new JTable(productTableModel);
        styleTable(productTable, new Color(30, 41, 59), new Color(241, 245, 249));

        // Highlight stock column with colors
        productTable.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                setHorizontalAlignment(CENTER);
                try {
                    int stock = Integer.parseInt(value.toString());
                    if (stock <= 8) {
                        setForeground(COLOR_WARNING_ORANGE);
                        setFont(getFont().deriveFont(Font.BOLD));
                    } else {
                        setForeground(COLOR_SUCCESS_GREEN);
                        setFont(getFont().deriveFont(Font.PLAIN));
                    }
                } catch (Exception ignored) {}
                return c;
            }
        });

        JScrollPane productScroll = new JScrollPane(productTable);
        productScroll.setBorder(new LineBorder(COLOR_BORDER_GRAY, 1));
        productsPanel.add(productScroll, BorderLayout.CENTER);

        // Add to Cart Controls
        JPanel addControls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        addControls.setBackground(COLOR_CARD_BG);

        JLabel qtyLabel = new JLabel("Quantity:");
        qtyLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        addControls.add(qtyLabel);

        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 50, 1));
        quantitySpinner.setPreferredSize(new Dimension(65, 30));
        quantitySpinner.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        addControls.add(quantitySpinner);

        JButton addToCartBtn = createStyledButton("➕ Add to Cart", COLOR_SUCCESS_GREEN, Color.WHITE);
        addToCartBtn.addActionListener(e -> handleAddToCart());
        addControls.add(addToCartBtn);

        productsPanel.add(addControls, BorderLayout.SOUTH);
        contentPanel.add(productsPanel);

        // --- Right: Cart Panel ---
        JPanel cartPanel = createCardPanel("🛍️ Cart (Amit)");

        String[] cartColumns = {"Product", "Price (₹)", "Qty", "Total (₹)"};
        cartTableModel = new DefaultTableModel(cartColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        cartTable = new JTable(cartTableModel);
        styleTable(cartTable, new Color(15, 118, 110), new Color(240, 253, 250)); // Teal Header

        JScrollPane cartScroll = new JScrollPane(cartTable);
        cartScroll.setBorder(new LineBorder(COLOR_BORDER_GRAY, 1));
        cartPanel.add(cartScroll, BorderLayout.CENTER);

        // Cart Actions (Remove & Clear)
        JPanel cartControls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        cartControls.setBackground(COLOR_CARD_BG);

        JButton removeBtn = createStyledButton("🗑️ Remove", COLOR_DANGER_RED, Color.WHITE);
        removeBtn.addActionListener(e -> handleRemoveCartItem());

        JButton clearBtn = createStyledButton("Clear All", new Color(100, 116, 139), Color.WHITE);
        clearBtn.addActionListener(e -> handleClearCart());

        cartControls.add(removeBtn);
        cartControls.add(clearBtn);
        cartPanel.add(cartControls, BorderLayout.SOUTH);

        contentPanel.add(cartPanel);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // =========================================================================
        // 3. BOTTOM PANEL: TOTALS, DISCOUNTS & CHECKOUT
        // =========================================================================
        JPanel bottomPanel = new JPanel(new BorderLayout(14, 14));
        bottomPanel.setBackground(COLOR_CARD_BG);
        bottomPanel.setBorder(new CompoundBorder(
                new LineBorder(COLOR_BORDER_GRAY, 1),
                new EmptyBorder(14, 16, 14, 16)
        ));

        // Options: Discount & Payment Selector (in Rupees)
        JPanel optionsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        optionsPanel.setOpaque(false);

        JLabel discPrompt = new JLabel("🏷️ Apply Discount:");
        discPrompt.setFont(new Font("Segoe UI", Font.BOLD, 13));
        optionsPanel.add(discPrompt);

        discountComboBox = new JComboBox<>(new String[]{
            "No Discount (0%)",
            "10% Promotional Off",
            "₹2,000 Flat Off (Orders > ₹15,000)",
            "Coupon: AMIT25 (25% off, max ₹5,000)"
        });
        discountComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        discountComboBox.setBackground(Color.WHITE);
        discountComboBox.addActionListener(e -> handleDiscountChange());
        optionsPanel.add(discountComboBox);

        JLabel payPrompt = new JLabel("💳 Payment Method:");
        payPrompt.setFont(new Font("Segoe UI", Font.BOLD, 13));
        optionsPanel.add(payPrompt);

        paymentMethodComboBox = new JComboBox<>(new String[]{
            "UPI Payment (GooglePay / PhonePe)",
            "Credit/Debit Card",
            "Net Banking / PayPal",
            "Cash on Delivery"
        });
        paymentMethodComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        paymentMethodComboBox.setBackground(Color.WHITE);
        optionsPanel.add(paymentMethodComboBox);

        bottomPanel.add(optionsPanel, BorderLayout.WEST);

        // Center of bottom: Financial Summary with Colored Badges
        JPanel totalsPanel = new JPanel(new GridLayout(4, 1, 3, 3));
        totalsPanel.setOpaque(false);
        totalsPanel.setBorder(new EmptyBorder(0, 25, 0, 20));

        subtotalLabel = new JLabel("Subtotal: ₹0.00");
        subtotalLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtotalLabel.setForeground(new Color(51, 65, 85));

        discountLabel = new JLabel("Discount: -₹0.00");
        discountLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        discountLabel.setForeground(COLOR_DANGER_RED);

        taxLabel = new JLabel("GST (5.0%): ₹0.00");
        taxLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        taxLabel.setForeground(new Color(100, 116, 139));

        grandTotalLabel = new JLabel("Total Payable: ₹0.00");
        grandTotalLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        grandTotalLabel.setForeground(COLOR_ACCENT_BLUE);

        totalsPanel.add(subtotalLabel);
        totalsPanel.add(discountLabel);
        totalsPanel.add(taxLabel);
        totalsPanel.add(grandTotalLabel);

        bottomPanel.add(totalsPanel, BorderLayout.CENTER);

        // Right of bottom: Checkout Button
        JPanel checkoutPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 16));
        checkoutPanel.setOpaque(false);

        JButton checkoutBtn = createStyledButton("⚡ Proceed to Checkout", COLOR_ACCENT_BLUE, Color.WHITE);
        checkoutBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        checkoutBtn.setPreferredSize(new Dimension(220, 50));
        checkoutBtn.addActionListener(e -> handleCheckout());

        checkoutPanel.add(checkoutBtn);
        bottomPanel.add(checkoutPanel, BorderLayout.EAST);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private JPanel createCardPanel(String title) {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBackground(COLOR_CARD_BG);
        TitledBorder border = BorderFactory.createTitledBorder(
                new LineBorder(COLOR_BORDER_GRAY, 1),
                " " + title + " ",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14),
                COLOR_PRIMARY_NAVY
        );
        panel.setBorder(new CompoundBorder(border, new EmptyBorder(8, 8, 8, 8)));
        return panel;
    }

    private void styleTable(JTable table, Color headerBg, Color altRowBg) {
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setSelectionBackground(new Color(219, 234, 254)); // Soft light blue
        table.setSelectionForeground(Color.BLACK);
        table.setShowGrid(true);
        table.setGridColor(COLOR_BORDER_GRAY);

        // Header Styling
        JTableHeader header = table.getTableHeader();
        header.setBackground(headerBg);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setPreferredSize(new Dimension(header.getWidth(), 32));

        // Alternating row colors
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tbl, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(tbl, value, isSelected, hasFocus, row, col);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : altRowBg);
                }
                return c;
            }
        });
    }

    private JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(bgColor);
        btn.setForeground(fgColor);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(8, 16, 8, 16));
        return btn;
    }

    private void populateProductTable() {
        productTableModel.setRowCount(0);
        for (Product p : inventory) {
            productTableModel.addRow(new Object[]{
                p.getId(),
                p.getName(),
                String.format("%.2f", p.getPrice()),
                p.getStockQuantity()
            });
        }
    }

    private void handleAddToCart() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product from the inventory table.", 
                    "No Product Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Product selectedProduct = inventory.get(selectedRow);
        int quantity = (Integer) quantitySpinner.getValue();

        boolean added = customer.getCart().addProduct(selectedProduct, quantity);
        if (!added) {
            JOptionPane.showMessageDialog(this, 
                    "Cannot add " + quantity + " units.\nOnly " + selectedProduct.getStockQuantity() + " units available in stock.",
                    "Insufficient Stock", JOptionPane.ERROR_MESSAGE);
            return;
        }

        refreshCartDisplay();
    }

    private void handleRemoveCartItem() {
        int selectedRow = cartTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item from your cart to remove.", 
                    "No Item Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<CartItem> items = customer.getCart().getItems();
        CartItem selectedItem = items.get(selectedRow);
        customer.removeFromCart(selectedItem.getProduct().getId());

        refreshCartDisplay();
    }

    private void handleClearCart() {
        if (customer.getCart().isEmpty()) return;
        customer.getCart().clear();
        refreshCartDisplay();
    }

    private void handleDiscountChange() {
        int index = discountComboBox.getSelectedIndex();
        switch (index) {
            case 1 -> customer.applyDiscount(new PercentageDiscount(10.0));
            case 2 -> customer.applyDiscount(new FlatDiscount(2000.0, 15000.0));
            case 3 -> customer.applyDiscount(new CouponDiscount("AMIT25", 25.0, 5000.0));
            default -> customer.getCart().removeDiscount();
        }
        updateTotalsDisplay();
    }

    private void refreshCartDisplay() {
        cartTableModel.setRowCount(0);
        for (CartItem item : customer.getCart().getItems()) {
            cartTableModel.addRow(new Object[]{
                item.getProduct().getName(),
                String.format("%.2f", item.getProduct().getPrice()),
                item.getQuantity(),
                String.format("%.2f", item.getTotalPrice())
            });
        }
        updateTotalsDisplay();
    }

    private void updateTotalsDisplay() {
        Cart cart = customer.getCart();
        double subtotal = cart.calculateSubtotal();
        double discount = cart.calculateDiscountAmount();
        double tax = cart.calculateTax();
        double total = cart.calculateTotal();

        subtotalLabel.setText(String.format("Subtotal: ₹%.2f", subtotal));
        discountLabel.setText(String.format("Discount: -₹%.2f", discount));
        taxLabel.setText(String.format("GST (%.1f%%): ₹%.2f", cart.getTaxRatePercentage(), tax));
        grandTotalLabel.setText(String.format("Total Payable: ₹%.2f", total));
    }

    private void handleCheckout() {
        if (customer.getCart().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Your cart is empty! Add products before checking out.",
                    "Empty Cart", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 1. Choose Payment Method (Polymorphism)
        int selectedMethod = paymentMethodComboBox.getSelectedIndex();
        PaymentMethod paymentMethod = switch (selectedMethod) {
            case 0 -> new UPIPayment("amit@okaxis");
            case 1 -> new CreditCardPayment("4532 8812 3456 7890", customer.getName(), "12/28", "888");
            case 2 -> new PayPalPayment("amit@paypal");
            default -> new CashPayment("House #12, Park View Colony, Delhi");
        };

        // 2. Perform Checkout via Customer Model
        Bill bill = customer.checkout(paymentMethod);

        if (bill != null) {
            // Update inventory table to reflect reduced stock
            populateProductTable();
            refreshCartDisplay();

            // 3. Show Bill / Invoice in a Dialog
            showBillDialog(bill);
        } else {
            JOptionPane.showMessageDialog(this, "Checkout failed.", "Payment Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showBillDialog(Bill bill) {
        StringBuilder sb = new StringBuilder();
        sb.append("====================================================================\n");
        sb.append("                     OFFICIAL TAX INVOICE                           \n");
        sb.append("====================================================================\n");
        sb.append(String.format(" Invoice No: %-22s Date: %s\n", bill.getBillId(), bill.getPayment().getFormattedTimestamp()));
        sb.append(String.format(" Customer  : %-22s ID  : %s\n", bill.getCustomerName(), bill.getCustomerId()));
        sb.append("--------------------------------------------------------------------\n");
        sb.append(String.format("%-4s %-28s %-12s %-5s %-14s\n", "No.", "Item Description", "Price (₹)", "Qty", "Total (₹)"));
        sb.append("--------------------------------------------------------------------\n");

        int i = 1;
        for (CartItem item : bill.getPurchasedItems()) {
            sb.append(String.format("%-4d %-28s ₹%-11.2f %-5d ₹%-13.2f\n",
                    i++,
                    item.getProduct().getName(),
                    item.getProduct().getPrice(),
                    item.getQuantity(),
                    item.getTotalPrice()));
        }

        sb.append("--------------------------------------------------------------------\n");
        sb.append(String.format(" Subtotal:                                         ₹%13.2f\n", bill.getSubtotal()));
        sb.append(String.format(" Discount:                                        -₹%13.2f\n", bill.getDiscountAmount()));
        sb.append(String.format(" GST (5.0%%):                                      ₹%13.2f\n", bill.getTaxAmount()));
        sb.append("--------------------------------------------------------------------\n");
        sb.append(String.format(" GRAND TOTAL:                                      ₹%13.2f\n", bill.getTotalAmount()));
        sb.append("====================================================================\n");
        sb.append(" PAYMENT VERIFICATION:\n");
        sb.append("   Status         : " + bill.getPayment().getStatus() + "\n");
        sb.append("   Transaction ID : " + bill.getPayment().getTransactionId() + "\n");
        sb.append("   Method         : " + bill.getPayment().getPaymentMethod().getMethodName() + "\n");
        sb.append("   Details        : " + bill.getPayment().getPaymentMethod().getPaymentDetails() + "\n");
        sb.append("====================================================================\n");
        sb.append("         Thank you Amit for shopping with us! Have a great day!     \n");
        sb.append("====================================================================\n");

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        textArea.setEditable(false);
        textArea.setBackground(new Color(248, 250, 252));
        textArea.setBorder(new EmptyBorder(12, 12, 12, 12));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(590, 490));

        JOptionPane.showMessageDialog(this, scrollPane, "✅ Order Placed Successfully!", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ShoppingAppGUI().setVisible(true);
        });
    }
}
