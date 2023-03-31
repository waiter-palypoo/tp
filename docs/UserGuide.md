# User Guide

## Introduction

Money Master is a Command Line Interface (CLI) based desktop application which will help the user track and manage their expenses quickly and effectively.

+ [Quick Start](#quick-start)
+ [Features](#features)
    + [Add Expense: `add expense`](#add-expense--add-expense)
    + [Edit Expense: `edit expense`](#edit-expense--edit-expense)
    + [Delete Expense: `delete expense`](#delete-expense--delete-expense)
    + [Set Balance: `set balance`](#set-balance--set-balance)
    + [List Expenses `list expenses`](#list-expenses--list-expenses)
    + [Check Balance `check balance`](#check-balance--check-balance)
    + [Add Future Expense: `add future expense`](#add-expense--add-future-expense)
    + [Edit Future Expense: `edit future expense`](#edit-expense--edit-future-expense)
    + [Delete Future Expense: `delete future expense`](#delete-future-expense--delete-future-expense)
    + [List Future Expenses `list future expenses`](#list-expenses--list-future-expenses)
    + [Check Upcoming Expenses: `check upcoming expenses`](#check-upcoming-expenses--check-upcoming-expenses)
    + [Pay Future Expenses: `pay`](#pay-future-expenses--pay)
    + [Exit the Application: `bye`](#exit-application--bye)
+ [FAQ](#faq)
+ [Command Summary](#command-summary)

## Quick Start
1. Ensure that you have Java 11 or above installed.
2. Download the latest version of Money Master from [here](https://github.com/AY2223S2-CS2113-W13-4/tp/releases).
3. Put the JAR file into an empty folder.
4. Open a command window and change the current working directory to the directory that the JAR file is located in using the following command:
```
cd [PATH_TO_JAR_DIRECTORY]
```
5. Run Money Master Applicatiom
```
java -jar tp.jar
```

## Features

### List Expenses: `list expenses`
Run the command followed by choosing what to sort the expenses by
#### Sort the listed expenses:
* By Date Added
* By Name
* By Price
---
### `set currency` / `get currency`
Set/get the currency used by the app
#### Usage:
```
set currency {USD/MYR/CAD etc.}
```
---
### Delete Expense: `delete expense`
Deletes an expense from the current list of expenses.

**Format:** `delete expense id/EXPENSE_ID`

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


## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: {your answer here}

## Command Summary

* Add expense `add expense NAME $/AMOUNT d/DATE`
* Edit expense `edit expense id/EXPENSE_ID in/"AMOUNT/DATE/CATEGORY"`
* Delete expense `delete expense id/EXPENSE_ID`
* Set balance `set balance $/AMOUNT`
* List expenses `list expenses`
* Check balance `check balance`
* Add future expense `add future expense NAME $/AMOUNT d/DATE`
* Edit future expense `edit future expense id/EXPENSE_ID in/"AMOUNT/DATE/CATEGORY"`
* Delete future expense `delete future expense id/EXPENSE_ID`
* List future expenses `list future expenses`
* Check upcoming expenses `check upcoming expenses`
* Pay future expense `pay EXPENSE_ID`
* Exit application `bye`
