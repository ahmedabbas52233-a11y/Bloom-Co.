package com.bloomshop.service;

import com.bloomshop.model.Bouquet;
import com.bloomshop.model.BouquetSize;
import com.bloomshop.model.Flower;
import com.bloomshop.model.FlowerColor;
import com.bloomshop.model.OrderStatistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages the in-memory order list and delegates pricing to {@link Bouquet}.
 *
 * The original code had two bugs fixed here:
 *   1. ArrayIndexOutOfBoundsException past MAX_ORDERS — replaced with a
 *      growable {@link ArrayList} and explicit capacity check.
 *   2. BouquetOrders.java used hardcoded prices (1.2 / 1.3) instead of the
 *      per-flower / per-color arrays — all pricing now lives in the enums.
 */
public class OrderService {

    private static final int MAX_ORDERS = 100;

    private final List<Bouquet> orders = new ArrayList<>();

    // ── Commands ──────────────────────────────────────────────────────────────

    /**
     * Creates a new bouquet, records it, and returns it.
     *
     * @throws IllegalStateException if the order limit has been reached
     */
    public Bouquet placeOrder(Flower flower, FlowerColor color, BouquetSize size) {
        if (orders.size() >= MAX_ORDERS) {
            throw new IllegalStateException(
                    "Order limit of " + MAX_ORDERS + " reached. " +
                    "Please view statistics and start a new session.");
        }
        Bouquet bouquet = new Bouquet(flower, color, size);
        orders.add(bouquet);
        return bouquet;
    }

    // ── Queries ───────────────────────────────────────────────────────────────

    /** Returns an unmodifiable view of all orders (insertion order). */
    public List<Bouquet> getOrders() {
        return Collections.unmodifiableList(orders);
    }

    /** Computes fresh statistics from the current order list. */
    public OrderStatistics getStatistics() {
        return OrderStatistics.from(orders);
    }

    public int getOrderCount() {
        return orders.size();
    }

    public boolean isEmpty() {
        return orders.isEmpty();
    }

    public boolean isFull() {
        return orders.size() >= MAX_ORDERS;
    }
}
