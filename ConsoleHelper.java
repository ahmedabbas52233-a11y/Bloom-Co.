package com.bloomshop.util;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Utility class that centralises all console I/O and input validation.
 * Keeps the menu and service classes free of Scanner noise.
 */
public final class ConsoleHelper {

    private ConsoleHelper() {}   // static-only utility

    // ── Separators ────────────────────────────────────────────────────────────

    public static void printSeparator() {
        System.out.println("─".repeat(55));
    }

    public static void printHeader(String title) {
        printSeparator();
        System.out.println("  " + title);
        printSeparator();
    }

    // ── Numbered option menu ─────────────────────────────────────────────────

    /**
     * Prints a numbered list of options and prompts the user.
     *
     * @param label   section heading (e.g. "Flower type")
     * @param options display names
     */
    public static void printOptions(String label, String[] options) {
        System.out.println("\n" + label + ":");
        for (int i = 0; i < options.length; i++) {
            System.out.printf("  %2d. %s%n", i + 1, options[i]);
        }
        System.out.print("  Your choice: ");
    }

    // ── Validated integer input ───────────────────────────────────────────────

    /**
     * Reads an integer in [min, max] from {@code scanner}, re-prompting on
     * bad input. Never throws — swallows all {@link InputMismatchException}s.
     */
    public static int readIntInRange(Scanner scanner, int min, int max) {
        while (true) {
            try {
                if (!scanner.hasNextInt()) {
                    System.out.printf("  Invalid input — enter a number between %d and %d: ", min, max);
                    scanner.next();
                    continue;
                }
                int value = scanner.nextInt();
                if (value < min || value > max) {
                    System.out.printf("  Out of range — enter a number between %d and %d: ", min, max);
                } else {
                    return value;
                }
            } catch (InputMismatchException e) {
                System.out.printf("  Invalid input — enter a number between %d and %d: ", min, max);
                scanner.next();
            }
        }
    }

    /**
     * Prints a prompt then delegates to {@link #readIntInRange}.
     */
    public static int readIntInRange(Scanner scanner, int min, int max, String prompt) {
        System.out.print(prompt);
        return readIntInRange(scanner, min, max);
    }
}
