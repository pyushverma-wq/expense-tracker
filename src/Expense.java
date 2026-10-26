public class Expense {

    private int id;
    private String date;      // format: yyyy-MM-dd
    private String category;
    private double amount;
    private String note;

    public Expense(int id, String date, String category, double amount, String note) {
        this.id = id;
        this.date = date;
        this.category = category;
        this.amount = amount;
        this.note = note;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public String getNote() {
        return note;
    }

    // Setters (used when editing)
    public void setDate(String date) {
        this.date = date;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setNote(String note) {
        this.note = note;
    }

    // Convert this expense into one line of the CSV file
    public String toCsv() {
        // Commas inside the note would break the CSV, so replace them
        String safeNote = note.replace(",", " ");
        return id + "," + date + "," + category + "," + amount + "," + safeNote;
    }

    // Build an Expense from one line of the CSV file
    // Returns null if the line is not valid
    public static Expense fromCsv(String line) {
        String[] parts = line.split(",", 5);
        if (parts.length < 5) {
            return null;
        }
        try {
            int id = Integer.parseInt(parts[0].trim());
            String date = parts[1].trim();
            String category = parts[2].trim();
            double amount = Double.parseDouble(parts[3].trim());
            String note = parts[4].trim();
            return new Expense(id, date, category, amount, note);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
