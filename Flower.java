package com.bloomshop.model;

/**
 * Represents available flower types with their base price markup.
 * Replacing the raw parallel arrays from the original implementation.
 */
public enum Flower {
    ROSE("Rose", 1.2),
    TULIP("Tulip", 5.3),
    DAISY("Daisy", 3.0),
    SUNFLOWER("Sunflower", 8.0),
    LILY("Lily", 7.5),
    ORCHID("Orchid", 4.8),
    CARNATION("Carnation", 2.8),
    HYDRANGEA("Hydrangea", 6.5),
    LAVENDER("Lavender", 9.2),
    MARIGOLD("Marigold", 1.9),
    PEONY("Peony", 8.6),
    CHRYSANTHEMUM("Chrysanthemum", 3.5);

    private final String displayName;
    private final double priceMarkup;

    Flower(String displayName, double priceMarkup) {
        this.displayName = displayName;
        this.priceMarkup = priceMarkup;
    }

    public String getDisplayName() { return displayName; }
    public double getPriceMarkup()  { return priceMarkup; }

    @Override
    public String toString() { return displayName; }
}
