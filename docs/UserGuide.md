# User Guide

## Introduction

Money Master is a Command Line Interface (CLI) based desktop application which will help the user track and manage their
expenses quickly and effectively.

+ [Quick Start](#quick-start)
+ [Features](#features)
    + [Add Expense](#add-expense--add-expense--add-future-expense)
    + [List Expenses](#list-expenses--list-expenses)
    + [List Future Expenses](#list-future-expenses--list-future-expenses)
    + [Currency](#currency--set-currency--get-currency)
    + [Edit Expense](#edit-expense--edit-expense--edit-future-expense)
    + [Delete Expense](#delete-expense--delete-expense--delete-future-expense)
    + [List Expenditure by Category](#list-expenditure-by-category--list-expenditure-by-category)
    + [Set Balance](#set-balance-set-balance)
    + [Check Balance](#check-balance-check-balance)
    + [Check Upcoming Expenses](#check-upcoming-expenses--check-upcoming-expenses)
    + [Pay Future Expenses](#pay-future-expenses--pay)
    + [Exit the Application](#exit-application--bye)
+ [FAQ](#faq)
+ [Command Summary](#command-summary)

## Quick Start

1. Ensure that you have Java 11 or above installed.
2. Download the latest version of Money Master from [here](https://github.com/AY2223S2-CS2113-W13-4/tp/releases).
3. Put the JAR file into an empty folder.
4. Open a command window and change the current working directory to the directory that the JAR file is located in using
   the following command:

```
cd [PATH_TO_JAR_DIRECTORY]
```

5. Run Money Master Applicatiom

```
java -jar tp.jar
```

## Features

### Add Expense: `add expense` / `add future expense`

Input `add expense`/`add future expense` followed by the name of the expenses, the amount and the date. The program will
then ask you for the category of the expense.

**Example:**

```
add expense Tuition $/3.50 d/20230304
--------------------------------------------------------------------------------
Which of the following category is this expense? Input a single number!
1. Food & Drinks
2. Shopping
3. Transportation
4. Life & Entertainment
5. Investments
6. Communication & Technology
7. Others
--------------------------------------------------------------------------------
1
```

**Expected outcome:**

```
--------------------------------------------------------------------------------
Roger, the following expense has been added!
Spent 3.50 SGD on Tuition in the Food & Drinks category on 2023-03-04
--------------------------------------------------------------------------------
```

**Note:**
If there are more than one input for the same parameters (multiple $/ or d/), the program will accept the first one
specified.
---

### List Expenses: `list expenses`

Run the command followed by choosing what to sort the expenses by

#### Sort the listed expenses:

* By Date Added / Expense ID
* By Name
* By Price

**Example:**

```
list expenses
--------------------------------------------------------------------------------
How would you like your expenses to be sorted?
  1. By date added / Expense ID
  2. By Name
  3. By Amount
--------------------------------------------------------------------------------
1
--------------------------------------------------------------------------------
```

**Expected outcome:**

```
--------------------------------------------------------------------------------
Here are the list of your expenses !

1. Spent 21.00 SGD on test in the Communication & Technology category on 2021-02-02
2. Spent 22.00 SGD on test2 in the Life & Entertainment category on 2021-01-01
--------------------------------------------------------------------------------

```

---

### List Future Expenses: `list future expenses`

Lists all the upcoming expenses and displays total amount due with total balance.\
Displays a warning if the balance is insufficient.

**Example:**

`list future expenses`

**Expected outcome:**

```
--------------------------------------------------------------------------------
Here is the list of your future expenses: 
1. Upcoming payment test in the Shopping category due on 2025-01-01. Warning: Insufficient Balance!
2. Upcoming payment test2 in the Life & Entertainment category due on 2026-01-01. Warning: Insufficient Balance!
--------------------------------------------------------------------------------
You have 2 future expenses in total.
Total amount due: 300.00 SGD
Total balance: -43.00 SGD
--------------------------------------------------------------------------------
Warning: You have insufficient balance to pay for all future expenses!
--------------------------------------------------------------------------------
```

---

### Currency: `set currency` / `get currency`

Set/get the currency used by the app

#### Usage:

```
set currency {USD/MYR/CAD etc.}
```

---

### Edit Expense: `edit expense` / `edit future expense`

#### Usage:

```
edit expense id/ID in/(amount|date|category)
edit future expense id/ID in/(amount|date|category)
```

Upon entering the command, a prompt will be given for the user to enter the new value

#### Examples:

```
edit expense id/2 in/amount
edit expense id/3 in/category
```

#### Expected Output

```
edit expense id/1 in/amount
Enter a new amount spent! Just enter a number!
200
Change in amount successful! Balance has also been recalculated
```

---

### Delete Expense: `delete expense` / `delete future expense`

Deletes an expense from the current list of past expenses or future expenses based on the command entered.

**Format:** `delete expense id/EXPENSE_ID` / `delete future expense id/EXPENSE_ID`

* Deletes the expense according to its id in the expense list.
* 'EXPENSE_ID' must be an integer starting from 1.
* Out of bounds 'EXPENSE_ID' will result in an error message

**Example:**

`delete expense id/1`

**Expected outcome:**

```
--------------------------------------------------------------------------------
Noted. I've removed this expense:
Spent $150.0 on book in the Others category on 2023-02-01
--------------------------------------------------------------------------------
```

**Example:**

`delete future expense id/1`

**Expected outcome:**

```
--------------------------------------------------------------------------------
Noted. I've removed this expense:
Upcoming payment book in the wrong input category due on 2023-06-06.
--------------------------------------------------------------------------------
```

---

### List Expenditure by Category: `list expenditure by category`

Lists the total expenditure across different categories.

**Format:** `list expenditure by category`

**Example:**

`list expenditure by category`

**Expected outcome:**

```
--------------------------------------------------------------------------------
Food & Drinks - $100.0
Others - $0.0
Investments - $0.0
Transportation - $0.0
Communication & Technology - $2000.0
Shopping - $0.0
Life & Entertainment - $0.0
--------------------------------------------------------------------------------
```

---

### Set balance `set balance`

Sets the total budget to the entered amount.

**Format:** `set balance $/AMOUNT`

**Example:**

`set balance $/1000`

**Expected outcome:**

```
--------------------------------------------------------------------------------
Your budget has been set to 1000.00 SGD
--------------------------------------------------------------------------------
```

**Note:**
This command will set the budget to the input value and will not deduct previous `Expense` input from this value.
---

### Check balance `check balance`

Displays the current balance.

**Format:** `check balance`

**Example:**

`check balance`

**Expected outcome:**

```
--------------------------------------------------------------------------------
Your current balance is: 1000.00 SGD
--------------------------------------------------------------------------------
```

---

## FAQ

**Q**: How do I transfer my data to another computer?

**A**: {your answer here}

## Command Summary

* Add expense `add expense NAME $/AMOUNT d/DATE`
* Edit expense `edit expense id/EXPENSE_ID in/"AMOUNT/DATE/CATEGORY"`
* Delete expense `delete expense id/EXPENSE_ID`
* Set balance `set balance $/AMOUNT`
* List expenses `list expenses`
* List expenditure across categories `list expenditure by category`
* Filter expenses by amount `expenses above $/AMOUNT` / `expenses below $/AMOUNT`
* Check balance `check balance`
* Set currency `set currency CURRENCY_SYMBOL`
* Get currency `get currency`
* Add future expense `add future expense NAME $/AMOUNT d/DATE`
* Edit future expense `edit future expense id/EXPENSE_ID in/"AMOUNT/DATE/CATEGORY"`
* Delete future expense `delete future expense id/EXPENSE_ID`
* List future expenses `list future expenses`
* Check upcoming expenses `check upcoming expenses`
* Pay future expense `pay EXPENSE_ID`
* Exit application `bye`
