import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static ExpenseManager manager = new ExpenseManager();
    static final String[] CATEGORIES = {"FOOD", "TRAVEL", "BILLS", "SHOPPING", "OTHER"};

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1:
                    addExpense();
                    break;
                case 2:
                    viewExpenses();
                    break;
                case 3:
                    editExpense();
                    break;
                case 4:
                    deleteExpense();
                    break;
                case 5:
                    monthlySummary();
                    break;
                case 6:
                    categoryReport();
                    break;
                case 7:
                    manager.save();
                    System.out.println("Data saved. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Enter a number from 1 to 7.");
            }
        }
    }

    // ---------- Menu ----------

    static void printMenu() {
        System.out.println();
        System.out.println("==============================");
        System.out.println("     EXPENSE TRACKER v1.0");
        System.out.println("==============================");
        System.out.println("1. Add expense");
        System.out.println("2. View all expenses");
        System.out.println("3. Edit expense");
        System.out.println("4. Delete expense");
        System.out.println("5. Monthly summary");
        System.out.println("6. Category report");
        System.out.println("7. Exit");
        System.out.println("------------------------------");
    }

    // ---------- Actions ----------

    static void addExpense() {
        double amount = readAmount(-1);
        String category = readCategory(null);
        String date = readDate(null);
        System.out.print("Enter note: ");
        String note = scanner.nextLine().trim();

        Expense e = manager.add(date, category, amount, note);
        System.out.println("Expense added successfully (ID: " + e.getId() + ")");
    }

    static void viewExpenses() {
        ArrayList<Expense> list = manager.getAll();
        if (list.isEmpty()) {
            System.out.println("No expenses recorded yet.");
            return;
        }
        printTable(list);
    }

    static void editExpense() {
        int id = readInt("Enter expense ID to edit: ");
        Expense e = manager.findById(id);
        if (e == null) {
            System.out.println("Error: No expense found with ID " + id + ".");
            return;
        }

        System.out.println("Press Enter to keep the current value.");
        double amount = readAmount(e.getAmount());
        String category = readCategory(e.getCategory());
        String date = readDate(e.getDate());
        System.out.print("Enter note [" + e.getNote() + "]: ");
        String note = scanner.nextLine().trim();
        if (note.isEmpty()) {
            note = e.getNote();
        }

        manager.edit(id, date, category, amount, note);
        System.out.println("Expense updated.");
    }

    static void deleteExpense() {
        int id = readInt("Enter expense ID to delete: ");
        if (manager.delete(id)) {
            System.out.println("Expense deleted.");
        } else {
            System.out.println("Error: No expense found with ID " + id + ".");
        }
    }

    static void monthlySummary() {
        System.out.print("Enter month (yyyy-MM): ");
        String month = scanner.nextLine().trim();

        ArrayList<Expense> list = manager.getByMonth(month);
        if (list.isEmpty()) {
            System.out.println("No expenses found for " + month + ".");
            return;
        }

        double total = manager.getTotal(list);
        Expense highest = manager.getHighest(list);

        System.out.println();
        System.out.println("--- Summary for " + month + " ---");
        System.out.printf("Total spent    : %.2f%n", total);
        System.out.println("Transactions   : " + list.size());
        System.out.printf("Highest expense: %s (%.2f)%n", highest.getNote(), highest.getAmount());
        System.out.printf("Average        : %.2f%n", total / list.size());
    }

    static void categoryReport() {
        ArrayList<Expense> all = manager.getAll();
        if (all.isEmpty()) {
            System.out.println("No expenses recorded yet.");
            return;
        }

        double grandTotal = manager.getTotal(all);

        System.out.println();
        System.out.println("--- Spending by Category ---");
        for (String category : CATEGORIES) {
            double categoryTotal = manager.getCategoryTotal(category);
            double percent = categoryTotal / grandTotal * 100;

            // One '#' for every 5 percent
            String bar = "";
            for (int i = 0; i < (int) (percent / 5); i++) {
                bar += "#";
            }

            System.out.printf("%-9s: %9.2f  (%5.1f%%)  %s%n", category, categoryTotal, percent, bar);
        }
    }

    // ---------- Printing ----------

    static void printTable(ArrayList<Expense> list) {
        System.out.println();
        System.out.printf("%-4s| %-11s| %-9s| %10s | %s%n", "ID", "Date", "Category", "Amount", "Note");
        System.out.println("----+------------+----------+------------+------------------");
        for (Expense e : list) {
            System.out.printf("%-4d| %-11s| %-9s| %10.2f | %s%n",
                    e.getId(), e.getDate(), e.getCategory(), e.getAmount(), e.getNote());
        }
        System.out.println("----+------------+----------+------------+------------------");
        System.out.printf("Total: %.2f%n", manager.getTotal(list));
    }

    // ---------- Input helpers ----------

    // Keeps asking until the user types a whole number
    static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // currentValue = -1 means "no current value" (used when adding)
    static double readAmount(double currentValue) {
        while (true) {
            if (currentValue >= 0) {
                System.out.print("Enter amount [" + currentValue + "]: ");
            } else {
                System.out.print("Enter amount: ");
            }
            String input = scanner.nextLine().trim();

            if (input.isEmpty() && currentValue >= 0) {
                return currentValue;
            }
            try {
                double amount = Double.parseDouble(input);
                if (amount <= 0) {
                    System.out.println("Amount must be greater than 0.");
                } else {
                    return amount;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }

    // current = null means "no current value" (used when adding)
    static String readCategory(String current) {
        System.out.print("Categories: ");
        for (int i = 0; i < CATEGORIES.length; i++) {
            System.out.print(CATEGORIES[i]);
            if (i < CATEGORIES.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();

        while (true) {
            if (current != null) {
                System.out.print("Enter category [" + current + "]: ");
            } else {
                System.out.print("Enter category: ");
            }
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.isEmpty() && current != null) {
                return current;
            }
            for (String category : CATEGORIES) {
                if (category.equals(input)) {
                    return input;
                }
            }
            System.out.println("Invalid category. Choose one from the list.");
        }
    }

    // Empty input = keep current value (edit) or use today's date (add)
    static String readDate(String current) {
        while (true) {
            if (current != null) {
                System.out.print("Enter date (yyyy-MM-dd) [" + current + "]: ");
            } else {
                System.out.print("Enter date (yyyy-MM-dd) [Enter for today]: ");
            }
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                if (current != null) {
                    return current;
                }
                return LocalDate.now().toString();
            }
            try {
                LocalDate.parse(input); // throws an exception if the format is wrong
                return input;
            } catch (Exception e) {
                System.out.println("Invalid date. Use the format yyyy-MM-dd.");
            }
        }
    }
}
