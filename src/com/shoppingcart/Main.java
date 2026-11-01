package com.shoppingcart;

import com.shoppingcart.discount.*;
import com.shoppingcart.model.*;
import com.shoppingcart.payment.*;

/**
 * Demonstration and Test Runner for the Shopping Cart System.
 * Customer: Amit | Currency: Indian Rupee (₹ INR) | Email removed.
 * 
 * Demonstrates:
 * 1. OOP Concepts:
 *    - Classes & Objects: Product, Cart, Customer, Payment, CartItem, Bill.
 *    - Encapsulation: Private member variables, getters/setters with validation, defensive copies.
 *    - Interfaces: Discount, PaymentMethod.
 *    - Polymorphism:
 *      * Dynamic dispatch (Method Overriding): Multiple Discount types, Multiple PaymentMethod types.
 *      * Static dispatch (Method Overloading): Cart.addProduct(Product) vs Cart.addProduct(Product, int).
 * 2. Requirements:
 *    - Add products to a cart.
 *    - Calculate totals (subtotal, discounts, taxes, final total in ₹).
 *    - Apply discounts (Percentage, Flat, Coupon).
 *    - Generate bills with itemized invoice and payment verification.
 */
public class Main {
    public static void main(String[] args) {
        printHeader("SHOPPING CART SYSTEM - OOP DEMONSTRATION (₹ INR)");

        // =========================================================================
        // STEP 1: Creating Products & Demonstrating Encapsulation (Validation)
        // =========================================================================
        System.out.println("[STEP 1] Creating Store Inventory (Classes, Objects & Encapsulation)...");
        Product laptop = new Product("P101", "Apple MacBook Air 13\" (M3, 16GB)", 114900.00, 8);
        Product smartphone = new Product("P102", "Google Pixel 9 Pro (128GB)", 109999.00, 12);
        Product headphones = new Product("P103", "Sony WH-1000XM5 ANC Headphones", 29990.00, 15);
        Product smartwatch = new Product("P104", "Apple Watch Series 10 (42mm GPS)", 46900.00, 10);
        Product ssd = new Product("P105", "Samsung 990 PRO 1TB Gen4 SSD", 10999.00, 25);
        Product mouse = new Product("P106", "Logitech MX Master 3S Mouse", 8995.00, 20);
        Product keyboard = new Product("P107", "Keychron K2 Mechanical Keyboard", 7499.00, 14);
        Product hub = new Product("P108", "Anker 555 8-in-1 USB-C Hub", 4499.00, 30);

        System.out.println("Available Products (Official Market Prices in ₹):");
        System.out.println("  * " + laptop);
        System.out.println("  * " + smartphone);
        System.out.println("  * " + headphones);
        System.out.println("  * " + smartwatch);
        System.out.println("  * " + ssd);
        System.out.println("  * " + mouse);
        System.out.println("  * " + keyboard);
        System.out.println("  * " + hub);

        // Demonstrating Encapsulation & Validation logic in Product
        System.out.println("\nTesting Encapsulation with invalid input (Negative price):");
        try {
            new Product("P999", "Faulty Item", -500.00, 5);
        } catch (IllegalArgumentException e) {
            System.out.println("  [PROTECTED] Encapsulation Protected State: " + e.getMessage());
        }

        // =========================================================================
        // STEP 2: Creating Customer Amit and Adding Products (Method Overloading)
        // =========================================================================
        printSubheader("[STEP 2] Customer Initialization & Adding Items to Cart");
        Customer customer1 = new Customer("AMIT-01", "Amit", "+91-9876543210");
        System.out.println("Customer logged in: " + customer1);

        System.out.println("\n--- Adding products using Overloaded Methods (Polymorphism) ---");
        // Overloaded method 1: addProduct(Product)
        customer1.addToCart(laptop); 

        // Overloaded method 2: addProduct(Product, int quantity)
        customer1.addToCart(headphones, 1);
        customer1.addToCart(mouse, 1);
        customer1.addToCart(keyboard, 1);

        // Preview Cart
        customer1.getCart().printCartPreview();

        // Testing stock boundary check in Cart
        System.out.println("\nTesting Stock Validation when adding excessive quantity:");
        customer1.addToCart(keyboard, 25); // only 14 in stock

        // =========================================================================
        // STEP 3: Modifying Cart (Updating Quantity & Removing Items)
        // =========================================================================
        printSubheader("[STEP 3] Updating Cart and Removing Items");
        System.out.println("Amit updates mouse quantity to 2 and removes keyboard...");
        customer1.getCart().updateQuantity("P106", 2);
        customer1.removeFromCart("P107");

        customer1.getCart().printCartPreview();

        // =========================================================================
        // STEP 4: Applying Discounts (Interface & Polymorphism Demo)
        // =========================================================================
        printSubheader("[STEP 4] Applying Discounts (Polymorphism via Discount Interface)");
        
        // Polymorphic reference 1: PercentageDiscount (10% Off)
        Discount tenPercentOff = new PercentageDiscount(10.0);
        customer1.applyDiscount(tenPercentOff);
        System.out.printf("  Current Subtotal: ₹%.2f | Discount Amount: -₹%.2f | Total: ₹%.2f%n",
                customer1.getCart().calculateSubtotal(),
                customer1.getCart().calculateDiscountAmount(),
                customer1.getCart().calculateTotal());

        // Polymorphic reference 2: FlatDiscount (₹2,000 off on orders over ₹15,000)
        Discount flatTwoThousand = new FlatDiscount(2000.0, 15000.0);
        customer1.applyDiscount(flatTwoThousand);
        System.out.printf("  Current Subtotal: ₹%.2f | Discount Amount: -₹%.2f | Total: ₹%.2f%n",
                customer1.getCart().calculateSubtotal(),
                customer1.getCart().calculateDiscountAmount(),
                customer1.getCart().calculateTotal());

        // Polymorphic reference 3: CouponDiscount ("AMIT25" with ₹5,000 max cap)
        Discount couponDiscount = new CouponDiscount("AMIT25", 25.0, 5000.0);
        customer1.applyDiscount(couponDiscount);
        System.out.printf("  Current Subtotal: ₹%.2f | Discount Amount: -₹%.2f | Total: ₹%.2f%n",
                customer1.getCart().calculateSubtotal(),
                customer1.getCart().calculateDiscountAmount(),
                customer1.getCart().calculateTotal());

        // =========================================================================
        // STEP 5: Checkout, Payment Processing & Bill Generation
        // =========================================================================
        printSubheader("[STEP 5] Checkout & Payment Processing via UPI (Interface & Polymorphism)");

        // Choosing Payment Method 1: UPI
        PaymentMethod upi = new UPIPayment("amit@okaxis");

        System.out.println("Processing customer checkout via " + upi.getMethodName() + "...");
        Bill bill1 = customer1.checkout(upi);

        if (bill1 != null) {
            bill1.printBill();
        }

        // Verify remaining stock of purchased items
        System.out.println("Inventory stock status after Amit's purchase:");
        System.out.println("  * " + laptop);
        System.out.println("  * " + headphones);
        System.out.println("  * " + mouse);

        printHeader("ALL TESTS COMPLETED SUCCESSFULLY");
        System.out.println("\n💡 Tip: To launch the Graphical User Interface (GUI), run:");
        System.out.println("   run_gui.bat  OR  java -cp bin com.shoppingcart.ui.ShoppingAppGUI\n");
    }

    private static void printHeader(String title) {
        System.out.println("\n" + "=".repeat(75));
        System.out.println("   " + title);
        System.out.println("=".repeat(75));
    }

    private static void printSubheader(String title) {
        System.out.println("\n" + "-".repeat(75));
        System.out.println(" " + title);
        System.out.println("-".repeat(75));
    }
}
