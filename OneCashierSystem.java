import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OneCashierSystem {

    private static final double TAX_RATE = 0.07;
    //Discount codes mapping to their respective discount rates 
    private static final Map<String, Double> DISCOUNT_CODES;
    static {
        DISCOUNT_CODES = new HashMap<>();
        DISCOUNT_CODES.put("SAVE10", 0.10);
        DISCOUNT_CODES.put("WELCOME5", 0.05);
        DISCOUNT_CODES.put("SUPER20", 0.20);
    }
    //Loyalty discount threshold and rate 
    private static final double LOYALTY_THRESHOLD = 100.0;
    private static final double LOYALTY_DISCOUNT = 0.05; // extra 5% discount

    private List<Item> items;
    private Scanner scanner;

    public OneCashierSystem() {
        items = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void run() {
        boolean anotherTransaction = true;
        while (anotherTransaction) {
            items.clear();
            System.out.println("\nWelcome Cashier System!");
            inputItems();
            double discountRate = inputDiscount();
            //Add loyalty discount if subtotal is greater than or equal to the threshold
            double subtotal = calculateSubtotal();
            if (subtotal >= LOYALTY_THRESHOLD) {
                System.out.println("Loyalty discount applied: additional 5% off subtotal");
                discountRate += LOYALTY_DISCOUNT;
            }
            Transaction transaction = new Transaction(discountRate, subtotal, items);
            printReceipt(transaction);
            saveReceiptToFile(transaction);
            anotherTransaction = askAnotherTransaction();
        }
    }

    private int inputQuantity() {
        while (true) {
            System.out.print("Enter quantity: ");
            try {
                int quantity = Integer.parseInt(scanner.nextLine().trim());
                if (quantity > 0) {
                    return quantity;
                }
            } catch (NumberFormatException e) {
                //ignore and show error below
            }
            System.out.println("Invalid quantity. Please enter a positive integer.");
        }
    }

    private double inputPrice() {
        while (true) {
            System.out.print("Enter price per unit: ");
            try {
                double price = Double.parseDouble(scanner.nextLine().trim());
                if (price >= 0.0) {
                    return price;
                }
            } catch (NumberFormatException e) {
                //ignore and show error below
            }
            System.out.println("Invalid price. Please enter a non-negative number.");
        }
    }

    private void inputItems() {
        while (true) {
            System.out.print("Enter item name (or press Enter to finish): ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                break;
            }
            int quantity = inputQuantity();
            double price = inputPrice();
            items.add(new Item(name, quantity, price));
        }
    }

    private double inputDiscount() {
        System.out.print("Enter discount code (or press Enter to skip): ");
        String code = scanner.nextLine().trim().toUpperCase();
        if (DISCOUNT_CODES.containsKey(code)) {
            System.out.printf("Discount code applied: %.0f%% off subtotal\n", DISCOUNT_CODES.get(code) * 100);
            return DISCOUNT_CODES.get(code);
        }
        return 0.0;
    }

    private double calculateSubtotal() {
        return items.stream().mapToDouble(item -> item.quantity * item.price).sum();
    }

    private boolean askAnotherTransaction() {
        System.out.print("Do you want to process another transaction? (y/n): ");
        String answer = scanner.nextLine().trim();
        return answer.equalsIgnoreCase("y");
    }

    private void printReceipt(Transaction transaction) {
        StringBuilder receipt = new StringBuilder();
        // Header with unique transaction ID and timestamp
        receipt.append("\n--- Receipt ---\n");
        receipt.append("Transaction ID: ").append(transaction.transactionId).append("\n");
        receipt.append("Date/Time: ").append(transaction.timestamp).append("\n");
        receipt.append(String.format("%-20s %-10s %-10s %-10s%n", "Item", "Quantity", "Price", "Total"));
        receipt.append("--------------------------------------------------\n");

        for (Item item : items) {
            double total = item.quantity * item.price;
            receipt.append(String.format("%-20s %-10d $%-9.2f $%-9.2f%n", item.name, item.quantity, item.price, total));
        }

        receipt.append("--------------------------------------------------\n");
        receipt.append(String.format("%-20s $%-9.2f%n", "Subtotal:", transaction.subtotal));
        if (transaction.discountRate > 0.0) {
            double discountAmount = transaction.subtotal * transaction.discountRate;
            receipt.append(String.format("%-20s -$%-9.2f%n", "Discount:", discountAmount));
        }
        double taxedSubtotal = transaction.subtotal - (transaction.subtotal * transaction.discountRate);
        double tax = taxedSubtotal * TAX_RATE;
        double grandTotal = taxedSubtotal + tax;
        receipt.append(String.format("%-20s $%-9.2f%n", "Tax (7%):", tax));
        receipt.append(String.format("%-20s $%-9.2f%n", "Total:", grandTotal));
        receipt.append("--------------------------------------------------\n");
        receipt.append("Thank you for shopping with us!\n");

        System.out.println(receipt);
        transaction.receiptContent = receipt.toString();
    }

    private void saveReceiptToFile(Transaction transaction) {
        String filename = "receipt_" + transaction.transactionId + ".txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            writer.println(transaction.receiptContent);
            System.out.println("Receipt saved to " + filename);
        } catch (IOException e) {
            System.out.println("Failed to save receipt to file.");
        }
    }

    //Represents a single transaction with its details
    private class Transaction {
        String transactionId;
        String timestamp;
        double discountRate;
        double subtotal;
        String receiptContent;
        List<Item> items;

        Transaction(double discountRate, double subtotal, List<Item> items) {
            this.discountRate = discountRate;
            this.subtotal = subtotal;
            this.items = new ArrayList<>(items);
            this.transactionId = generateTransactionId();
            this.timestamp = getCurrentTimestamp();
        }

        private String generateTransactionId() {
            return UUID.randomUUID().toString().substring(0, 8);
        }

        private String getCurrentTimestamp() {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return now.format(formatter);
        }
    }

    //Represents an individual item
    private static class Item {
        String name;
        int quantity;
        double price;

        public Item(String name, int quantity, double price) {
            this.name = name;
            this.quantity = quantity;
            this.price = price;
        }
    }

    public static void main(String[] args) {
        new OneCashierSystem().run();
    }
}
