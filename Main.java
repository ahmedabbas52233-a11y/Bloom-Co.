package com.bloomshop;

import com.bloomshop.service.OrderService;
import com.bloomshop.ui.OrderMenu;
import com.bloomshop.ui.StatisticsDisplay;
import com.bloomshop.util.ConsoleHelper;

import java.util.Scanner;

/**
 * ╔══════════════════════════════════════════════════════╗
 * ║          Bloom & Co. — Flower Shop v2.0              ║
 * ╚══════════════════════════════════════════════════════╝
 *
 * Entry point and main menu loop.
 *
 * Architecture:
 *   model/      — Flower, FlowerColor, BouquetSize (enums), Bouquet, OrderStatistics
 *   service/    — OrderService  (business logic, capacity guard)
 *   ui/         — OrderMenu, StatisticsDisplay  (console presentation)
 *   util/       — ConsoleHelper  (validated I/O)
 *
 * Run:
 *   javac -d out -sourcepath src src/com/bloomshop/Main.java
 *   java  -cp out com.bloomshop.Main
 */
public class Main {

    public static void main(String[] args) {
        OrderService     orderService = new OrderService();
        OrderMenu        orderMenu    = new OrderMenu(orderService);

        try (Scanner scanner = new Scanner(System.in)) {
            printWelcome();

            while (true) {
                printMainMenu(orderService.getOrderCount());
                int choice = ConsoleHelper.readIntInRange(scanner, 1, 3, "\nEnter choice (1-3): ");

                switch (choice) {
                    case 1 -> orderMenu.run(scanner);
                    case 2 -> {
                        if (orderService.isEmpty()) {
                            System.out.println("\n  No orders yet — place an order first.");
                        } else {
                            StatisticsDisplay.print(orderService.getStatistics(), orderService.getOrders());
                        }
                    }
                    case 3 -> {
                        System.out.println("\n  Thank you for visiting Bloom & Co. Have a lovely day! 🌸");
                        return;
                    }
                }
            }
        }
    }

    private static void printWelcome() {
        System.out.println();
        System.out.println("  ╔══════════════════════════════════════════╗");
        System.out.println("  ║       🌸  Bloom & Co. Flower Shop 🌸      ║");
        System.out.println("  ║        Your premium bouquet builder       ║");
        System.out.println("  ╚══════════════════════════════════════════╝");
        System.out.println();
    }

    private static void printMainMenu(int orderCount) {
        ConsoleHelper.printSeparator();
        System.out.println("  MAIN MENU" +
                (orderCount > 0 ? "  [" + orderCount + " order" + (orderCount > 1 ? "s" : "") + " placed]" : ""));
        ConsoleHelper.printSeparator();
        System.out.println("  1.  Order a bouquet");
        System.out.println("  2.  View statistics & order history");
        System.out.println("  3.  Exit");
        ConsoleHelper.printSeparator();
    }
}
