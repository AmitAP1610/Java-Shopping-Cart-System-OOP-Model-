# 🛒 Java Shopping Cart System (OOP Model)

An Object-Oriented Shopping Cart System built in pure Java demonstrating core OOP principles: **Classes & Objects, Encapsulation, Interfaces, and Polymorphism**.

Includes both an automated **Console Test Suite** and an interactive, modern **Desktop GUI** with real Indian market prices (₹ INR).

---

## 🎯 Key Features

- **Store Inventory**: Real-world products with live stock tracking.
- **Shopping Cart**: Add, update quantities, remove items, and clear cart.
- **Total Calculations**: Real-time Subtotal, GST (5%), and Grand Total.
- **Dynamic Discounts**: Polymorphic discount policies (Percentage, Flat, and Coupon).
- **Payment Processing**: Multiple payment methods (UPI, Credit Card, PayPal, Cash on Delivery).
- **Official Tax Invoice**: Itemized bill generation with transaction verification.

---

## 🏛️ OOP Concepts Demonstrated

| Concept | Implementation |
| :--- | :--- |
| **Classes & Objects** | `Product`, `Cart`, `Customer`, `Payment`, `CartItem`, `Bill` |
| **Encapsulation** | Private fields with strict validation checks, defensive copies |
| **Interfaces** | `Discount` and `PaymentMethod` interfaces |
| **Polymorphism (Dynamic)** | Runtime method overriding in discount strategies and payment providers |
| **Polymorphism (Static)** | Method overloading for `Cart.addProduct()` |

---

## 🚀 How to Run

### 1. Launch the Desktop GUI Window
```bash
# Windows 1-Click
run_gui.bat

# Or via Command Line
javac *.java
java ShoppingAppGUI
```

### 2. Run the Console Demo & Test Runner
```bash
# Windows 1-Click
run_console.bat

# Or via Command Line
java Main
```

---

## 👤 Author
- **Amit** (Shopping Cart System Model)
