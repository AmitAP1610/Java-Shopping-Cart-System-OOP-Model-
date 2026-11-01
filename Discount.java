/**
 * Interface representing a discount policy.
 * 
 * OOP Concept: Interface & Polymorphism
 * - Declares the contract for applying discounts.
 * - Allows different discount algorithms (percentage, flat, coupon)
 *   to be used interchangeably at runtime.
 */
public interface Discount {
    /**
     * Calculates the discount amount based on the given subtotal.
     * 
     * @param subtotal the gross cart subtotal before discount
     * @return the monetary amount to be deducted
     */
    double calculateDiscount(double subtotal);

    /**
     * Returns a human-readable description of the discount.
     */
    String getDescription();
}

