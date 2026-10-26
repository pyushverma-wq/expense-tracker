# 💸 Expense tracker

A simple console expense tracker written in core Java that saves your spending to a CSV file

![Java](https://img.shields.io/badge/java-17+-orange.svg) ![CSV](https://img.shields.io/badge/storage-csv-green.svg)

## [✨] : What it does

Keep track of where your money goes, right from the terminal. This app lets you add expenses, browse them in a clean table, and see monthly and category-wise summaries. Everything is saved to a CSV file, so your data is still there the next time you open it :)

## [🎯] : Key features

- **Full expense management**: add, view, edit and delete expenses with an amount, category, date and note
- **Monthly summary**: see total spent, number of transactions, highest expense and the average for any month
- **Category report**: spending per category with percentages and a simple text bar chart
- **Input validation**: invalid amounts, dates, categories and expense ids are handled without crashing
- **Smart defaults**: press Enter to use today's date when adding, or to keep the current value when editing
- **Persistent data**: expenses are stored in `data/expenses.csv` and remain after closing the app
- **Zero dependencies**: core Java only, no libraries or build tools needed

## [🚀] : How to use

```bash
# clone the repo
git clone https://github.com/yourusername/expense-tracker.git
cd expense-tracker

# compile
javac -d out src/*.java

# run
java -cp out Main
```

1. Pick an option from the menu by typing its number
2. Follow the prompts for the amount, category, date and note
3. Press Enter at the date prompt to use today's date
4. View your expenses or open a report any time
5. Choose option 7 to save and exit

The `data/` folder and `expenses.csv` file are created automatically the first time you add an expense.

## [🖥️] : Sample input and output

**Menu**

```
==============================
     EXPENSE TRACKER v1.0
==============================
1. Add expense
2. View all expenses
3. Edit expense
4. Delete expense
5. Monthly summary
6. Category report
7. Exit
------------------------------
Choose an option: 
```

**Adding an expense**

```
Choose an option: 1
Enter amount: 250
Categories: FOOD, TRAVEL, BILLS, SHOPPING, OTHER
Enter category: food
Enter date (yyyy-MM-dd) [Enter for today]: 
Enter note: Lunch at cafe
Expense added successfully (ID: 4)
```

**Viewing all expenses**

```
Choose an option: 2

ID  | Date       | Category |     Amount | Note
----+------------+----------+------------+------------------
1   | 2026-10-20 | TRAVEL   |     120.00 | Bus pass
2   | 2026-10-22 | BILLS    |     599.00 | Mobile recharge
3   | 2026-10-24 | SHOPPING |    1450.00 | Headphones
4   | 2026-10-26 | FOOD     |     250.00 | Lunch at cafe
----+------------+----------+------------+------------------
Total: 2419.00
```

**Editing an expense**

```
Choose an option: 3
Enter expense ID to edit: 4
Press Enter to keep the current value.
Enter amount [250.0]: 300
Categories: FOOD, TRAVEL, BILLS, SHOPPING, OTHER
Enter category [FOOD]: 
Enter date (yyyy-MM-dd) [2026-10-26]: 
Enter note [Lunch at cafe]: Lunch and coffee
Expense updated.
```

**Monthly summary**

```
Choose an option: 5
Enter month (yyyy-MM): 2026-10

--- Summary for 2026-10 ---
Total spent    : 2469.00
Transactions   : 4
Highest expense: Headphones (1450.00)
Average        : 617.25
```

**Category report**

```
Choose an option: 6

--- Spending by Category ---
FOOD     :    300.00  ( 12.2%)  ##
TRAVEL   :    120.00  (  4.9%)  
BILLS    :    599.00  ( 24.3%)  ####
SHOPPING :   1450.00  ( 58.7%)  ###########
OTHER    :      0.00  (  0.0%)  
```

**Handling invalid input**

```
Choose an option: 1
Enter amount: abc
Please enter a valid amount.
Enter amount: -50
Amount must be greater than 0.
Enter amount: 80
Categories: FOOD, TRAVEL, BILLS, SHOPPING, OTHER
Enter category: games
Invalid category. Choose one from the list.

Choose an option: 4
Enter expense ID to delete: 99
Error: No expense found with ID 99.
```

## [🗂️] : Project structure

```
expense-tracker/
├── README.md
├── .gitignore
├── data/
│   └── sample_expenses.csv
└── src/
    ├── Main.java            # menu loop, Scanner input, printing
    ├── Expense.java         # id, date, category, amount, note
    ├── ExpenseManager.java  # add, edit, delete, list, summaries
    └── CsvStorage.java      # load and save expenses.csv
```

**CSV format**

```
id,date,category,amount,note
1,2026-10-20,TRAVEL,120.0,Bus pass
2,2026-10-22,BILLS,599.0,Mobile recharge
3,2026-10-24,SHOPPING,1450.0,Headphones
```

## [🔧] : Java concepts used

| concept | where it is used in the app |
|---------|-----------------------------|
| **Classes and objects** | `Expense` models a single expense with its fields and getters |
| **ArrayList** | holds all expenses in memory while the app runs |
| **Scanner** | reads menu choices and user input from the console |
| **File I/O** | `BufferedReader` and `PrintWriter` read and write the CSV file |
| **Exception handling** | `try/catch` for bad numbers, invalid dates and file errors |
| **LocalDate** | gets today's date and validates the date format |
| **String methods** | `split`, `trim`, `startsWith` for parsing the CSV and filtering by month |
| **printf** | formats the table and reports into neat columns |

## [🧠] : How it works

1. On startup, `CsvStorage` loads every saved expense from `data/expenses.csv`
2. `Main` shows the menu and reads the user's choice
3. `ExpenseManager` handles the logic for adding, editing, deleting and summarising expenses
4. After every change, the list is written back to the CSV file
5. The next expense id is always the highest existing id plus one, so ids never repeat

The code is split into layers: storage, logic and user interface. Only `CsvStorage` touches the file, only `Main` prints and reads input, and `ExpenseManager` holds the rules in between.

## [🔮] : Future enhancements

- **Package structure and Maven**: organise the code into packages and add a proper build file
- **Unit tests**: JUnit tests for the manager and storage classes
- **Database storage**: swap the CSV file for SQLite or MySQL using JDBC
- **Search and filter**: find expenses by category, date range or amount
- **Budget limits**: set a monthly budget per category and get a warning when it is exceeded
- **Export**: save monthly reports as a text or PDF file
- **REST API version**: rebuild it with Spring Boot and add authentication

## [🤝] : Contributing

Contributions welcome! Feel free to submit issues or pull requests.

## [🏢] : Acknowledgments

- Built as a beginner Java project to practice object-oriented design and file handling
