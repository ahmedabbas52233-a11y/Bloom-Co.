package com.bloomshop.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Immutable value object representing a single bouquet configuration.
 * Price is calculated once at construction time using the formula:
 *   totalPrice = (flowerMarkup + colorMarkup) × sizeMultiplier
 */
public final class Bouquet {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");

    private final Flower         flower;
    private final FlowerColor    color;
    private final BouquetSize    size;
    private final double         totalPrice;
    private final LocalDateTime  orderedAt;

    public Bouquet(Flower flower, FlowerColor color, BouquetSize size) {
        this.flower     = flower;
        this.color      = color;
        this.size       = size;
        this.totalPrice = (flower.getPriceMarkup() + color.getPriceMarkup()) * size.getMultiplier();
        this.orderedAt  = LocalDateTime.now();
    }

    // ── Getters ──────────────────────────────────────────────────────────────

    public Flower        getFlower()     { return flower; }
    public FlowerColor   getColor()      { return color; }
    public BouquetSize   getSize()       { return size; }
    public double        getTotalPrice() { return totalPrice; }
    public LocalDateTime getOrderedAt()  { return orderedAt; }

    // ── Display ───────────────────────────────────────────────────────────────

    /**
     * One-line receipt summary, e.g.  "Rose · Red · Large → $101.25"
     */
    public String toReceiptLine() {
        return String.format("%-14s · %-8s · %-8s → $%.2f",
                flower.getDisplayName(),
                color.getDisplayName(),
                size.getDisplayName(),
                totalPrice);
    }

    @Override
    public String toString() {
        return String.format("Bouquet{flower=%s, color=%s, size=%s, price=%.2f, orderedAt=%s}",
                flower, color, size, totalPrice, orderedAt.format(FORMATTER));
    }
}
