package com.bloomshop.ui;

import com.bloomshop.model.Bouquet;
import com.bloomshop.model.BouquetSize;
import com.bloomshop.model.Flower;
import com.bloomshop.model.FlowerColor;
import com.bloomshop.service.OrderService;
import com.bloomshop.util.ConsoleHelper;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Drives the interactive bouquet-ordering flow.
 * Separated from the main menu so each UI concern has its own class.
 */
public final class OrderMenu {

    private final OrderService orderService;

    public OrderMenu(OrderService orderService) {
        this.orderService = orderService;
    }

    /** Full ordering flow: select flower → color → size → confirm → receipt. */
    public void run(Scanner scanner) {
        if (orderService.isFull()) {
            System.out.println("  Order limit reached. View statistics to review your orders.");
            return;
        }

        ConsoleHelper.printHeader("NEW BOUQUET ORDER");

        // ── Flower selection ─────────────────────────────────────────────────
        Flower[] flowers = Flower.values();
        String[] flowerNames = Arrays.stream(flowers)
                .map(f -> String.format("%-14s  ($%.1f markup)", f.getDisplayName(), f.getPriceMarkup()))
                .toArray(String[]::new);
        ConsoleHelper.printOptions("Flower type", flowerNames);
        int fi = ConsoleHelper.readIntInRange(scanner, 1, flowers.length) - 1;
        Flower flower = flowers[fi];

        // ── Color selection ──────────────────────────────────────────────────
        FlowerColor[] colors = FlowerColor.values();
        String[] colorNames = Arrays.stream(colors)
                .map(c -> String.format("%-8s  ($%.1f markup)", c.getDisplayName(), c.getPriceMarkup()))
                .toArray(String[]::new);
        ConsoleHelper.printOptions("Color", colorNames);
        int ci = ConsoleHelper.readIntInRange(scanner, 1, colors.length) - 1;
        FlowerColor color = colors[ci];

        // ── Size selection ───────────────────────────────────────────────────
        BouquetSize[] sizes = BouquetSize.values();
        String[] sizeNames = Arrays.stream(sizes)
                .map(s -> String.format("%-8s  (×%.1f multiplier)", s.getDisplayName(), s.getMultiplier()))
                .toArray(String[]::new);
        ConsoleHelper.printOptions("Size", sizeNames);
        int si = ConsoleHelper.readIntInRange(scanner, 1, sizes.length) - 1;
        BouquetSize size = sizes[si];

        // ── Price preview & confirmation ────────────────────────────────────
        double preview = (flower.getPriceMarkup() + color.getPriceMarkup()) * size.getMultiplier();
        System.out.println();
        ConsoleHelper.printSeparator();
        System.out.printf("  Preview  →  %s · %s · %s%n",
                flower.getDisplayName(), color.getDisplayName(), size.getDisplayName());
        System.out.printf("  Total price: $%.2f%n", preview);
        ConsoleHelper.printSeparator();
        int confirm = ConsoleHelper.readIntInRange(scanner, 1, 2,
                "  Confirm order? (1=Yes / 2=Cancel): ");

        if (confirm == 2) {
            System.out.println("  Order cancelled.");
            return;
        }

        // ── Place order ──────────────────────────────────────────────────────
        Bouquet bouquet = orderService.placeOrder(flower, color, size);
        System.out.println();
        System.out.println("  ✓  Order placed! (#" + orderService.getOrderCount() + ")");
        System.out.println("  " + bouquet.toReceiptLine());
    }
}
