# 🌸 Bloom & Co. — Flower Shop

![Build](https://github.com/YOUR-USERNAME/flower-shop-java/actions/workflows/build.yml/badge.svg)
![Java](https://img.shields.io/badge/Java-21-blue?logo=openjdk)
![License](https://img.shields.io/badge/license-MIT-green)

A refactored Java console application demonstrating OOP design principles, clean package architecture, and idiomatic Java 17+ patterns — built as a portfolio piece and structured for Spring Boot extension.

---

## Quick start

```bash
# Clone
git clone https://github.com/YOUR-USERNAME/flower-shop-java.git
cd flower-shop-java

# Compile (JDK 17+)
javac -d out -sourcepath src src/com/bloomshop/Main.java

# Run from classes
java -cp out com.bloomshop.Main

# Or run the pre-built JAR
java -jar BloomShop.jar
```

---

## Features

- 12 flower types · 7 colors · 3 sizes
- Live price preview before confirming each order
- Order confirmation step (accept / cancel)
- Up to 100 orders per session with a hard capacity guard
- Statistics: total revenue, min/max, average, price range, most popular flower & color
- Full order history with receipt-style formatting
- All input validated — no crashes on bad input or extra orders

---

## Pricing formula

```
totalPrice = (flowerMarkup + colorMarkup) × sizeMultiplier
```

| Example         | Markup |   | Example | Markup |   | Size   | Multiplier |
|-----------------|--------|---|---------|--------|---|--------|------------|
| Rose            | 1.2    |   | Red     | 9.3    |   | Small  | ×5.5       |
| Orchid          | 4.8    |   | Pink    | 8.2    |   | Medium | ×7.5       |
| Peony           | 8.6    |   | White   | 6.1    |   | Large  | ×9.5       |

---

## Architecture

```
com.bloomshop/
├── Main.java                    ← entry point & main menu loop
├── model/
│   ├── Flower.java              ← enum  (12 types, price markup per type)
│   ├── FlowerColor.java         ← enum  (7 colors, price markup per color)
│   ├── BouquetSize.java         ← enum  (3 sizes, price multiplier per size)
│   ├── Bouquet.java             ← immutable value object; price computed at construction
│   └── OrderStatistics.java     ← snapshot: revenue, min/max, averages, popularity
├── service/
│   └── OrderService.java        ← manages order list; enforces MAX_ORDERS
├── ui/
│   ├── OrderMenu.java           ← interactive ordering flow
│   └── StatisticsDisplay.java   ← formatted stats & history output
└── util/
    └── ConsoleHelper.java       ← validated Scanner I/O, menus, separators
```

---

## What changed from v1

| Area | v1 | v2 |
|------|----|----|
| Structure | 3 duplicate files | Single entry point, clear package layout |
| Domain model | Parallel `String[]` arrays | Typed enums with fields |
| Pricing logic | Hardcoded `1.2 / 1.3` for every order | Encapsulated in enum constructors |
| Order storage | `String[10][4]` — crashes at order #11 | `ArrayList<Bouquet>` with capacity guard |
| Statistics | Printed `"Summary statistics provided."` (stub) | Full `OrderStatistics` via Streams |
| Input validation | Scattered across files | Centralised in `ConsoleHelper` |
| OOP | All `static` methods, zero classes | Enum · Value object · Service · UI |
| Confirmation | None | Price preview + confirm/cancel |

---

## Roadmap

**Phase 3 — Spring Boot REST API**
```
POST /orders          → place an order, receive JSON receipt
GET  /orders          → paginated order history
GET  /statistics      → OrderStatistics as JSON
```

**Phase 4 — React frontend**
- Live bouquet builder with real-time price preview
- Revenue chart and order history dashboard
- Deployed on Vercel, API on Railway

**Testing**
- JUnit 5 unit tests for `OrderService` and `Bouquet` pricing
- Parameterised tests covering all 252 combinations (12 × 7 × 3)

---

## License

[MIT](LICENSE)
