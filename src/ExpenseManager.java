import java.util.ArrayList;

public class ExpenseManager {

    private ArrayList<Expense> expenses;
    private int nextId;

    public ExpenseManager() {
        expenses = CsvStorage.load();
        nextId = findNextId();
    }

    // Next id = highest existing id + 1
    private int findNextId() {
        int max = 0;
        for (Expense e : expenses) {
            if (e.getId() > max) {
                max = e.getId();
            }
        }
        return max + 1;
    }

    // Add a new expense and return it
    public Expense add(String date, String category, double amount, String note) {
        Expense expense = new Expense(nextId, date, category, amount, note);
        nextId++;
        expenses.add(expense);
        save();
        return expense;
    }

    public ArrayList<Expense> getAll() {
        return expenses;
    }

    // Returns the expense with this id, or null if not found
    public Expense findById(int id) {
        for (Expense e : expenses) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    // Returns true if deleted, false if the id was not found
    public boolean delete(int id) {
        Expense expense = findById(id);
        if (expense == null) {
            return false;
        }
        expenses.remove(expense);
        save();
        return true;
    }

    // Returns true if edited, false if the id was not found
    public boolean edit(int id, String date, String category, double amount, String note) {
        Expense expense = findById(id);
        if (expense == null) {
            return false;
        }
        expense.setDate(date);
        expense.setCategory(category);
        expense.setAmount(amount);
        expense.setNote(note);
        save();
        return true;
    }

    // All expenses whose date starts with the month, e.g. "2026-10"
    public ArrayList<Expense> getByMonth(String month) {
        ArrayList<Expense> result = new ArrayList<>();
        for (Expense e : expenses) {
            if (e.getDate().startsWith(month)) {
                result.add(e);
            }
        }
        return result;
    }

    // Sum of amounts in a list
    public double getTotal(ArrayList<Expense> list) {
        double total = 0;
        for (Expense e : list) {
            total += e.getAmount();
        }
        return total;
    }

    // Sum of amounts for one category
    public double getCategoryTotal(String category) {
        double total = 0;
        for (Expense e : expenses) {
            if (e.getCategory().equals(category)) {
                total += e.getAmount();
            }
        }
        return total;
    }

    // Expense with the highest amount in a list (null if the list is empty)
    public Expense getHighest(ArrayList<Expense> list) {
        Expense highest = null;
        for (Expense e : list) {
            if (highest == null || e.getAmount() > highest.getAmount()) {
                highest = e;
            }
        }
        return highest;
    }

    public void save() {
        CsvStorage.save(expenses);
    }
}
