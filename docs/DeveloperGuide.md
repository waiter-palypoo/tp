# Developer Guide

## Table of Contents

+ [Acknowledgements](#acknowledgements)
+ [Design](#design)
    * [Architecture](#architecture)
    * [Parser Component](#parser-component)
    * [UI Component](#ui-component)
    * [Storage Component](#storage-component)
    * [ExpenseManager Component](#expensemanager-component)
+ [Implementation](#implementation)
+ [Product Scope](#product-scope)
    * [Target User Profile](#target-user-profile)
    * [Value Proposition](#value-proposition)
+ [User Stories](#user-stories)
+ [Non-Functional Requirements](#non-functional-requirements)
+ [Glossary](#glossary)
+ [Instructions for Manual Testing](#instructions-for-manual-testing)

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the
original source as well}

## Design

### Architecture

### Parser Component
The `Parser` class handles the parsing of user input, executing the appropriate tasks
or throwing relevant errors.
* `Parser` first receives user input from `Duke` through `Parser::handleCmd`
* Depending on the command given, `Parser` will then execute the command on the `ExpenseManager`instance, or throw a `DukeException`, which will be caught by `Duke`
### **Sequence Diagram**
<img alt="parser sequence diagram" src="diagrams/parser.png" width="280" />

### UI Component

The API of this component is specified in Ui.java \
The Ui class contains several static methods which display messages to the user when the user interacts with program.\
Types of messages displayed:

+ Instructions: Information message that describes the list of all commands of the application.
+ Greeting Messages: Messages shown to greet the user and acknowledge their exit.
+ Error Messages: Messages shown to inform the user that their input is invalid.
+ Category Choice: Displays category choices of expense to the user. \
  The class also prints text such as lines and the formatted list.

<img alt="ui class diagram" src="diagrams/UiComponentClassDiagram.png" width="280" />

### Storage Component

<img alt="storage sequence diagram" src="diagrams/storage.png" width="280" />

The `Storage` class handles the validation and creation of the `duke_data.txt` file, which contains all the necessary
data regarding the user's budget and expenses.

### ExpenseManager Component

The `ExpenseManager` class keeps track of the list of the class `Expense` and `FutureExpense` as well as
the `TotalBudget`. The class also contains methods that involve the creating, editing, listing of these items.

<img alt="ExpenseManager class diagram" src="diagrams/ExpenseManagerClassDiagram.png" width="700" />

### Sequence Explanation

* Upon creation of the `Storage` object, the constructor first checks whether the directory and file
  `${CWD}/data/duke_data.txt` exists, and creates them otherwise.
* Before entering the main loop, `Duke` will first call `Storage::loadDataExpenses()`, which reads from `duke_data.txt`
  and initializes and *returns* a `ExpenseManager` object containing the budget, `Expenses` and `FutureExpenses` from
  the data file.
* Duke then uses the returned `ExpenseManager` object for the current session
* After every command, `Duke` will then call `Storage::saveExpenses(ExpenseManager)`, which serializes the current state
  of
  the `ExpenseManager` and writes the serialized data into `duke_data.txt`, making sure that the latest state of the app
  is always saved.

## Implementation

## Product scope

### Target user profile

Our target user profile are people who want to be financially responsible and want to keep a track of their spending to
make informed financial decisions. The users include students, small business owners etc. who are interested in managing
their finances efficiently.

### Value proposition

Money master provides a fast and streamlined way for users to manage their finances. It helps the user manage their
finances quickly and efficiently by providing a convenient way to track their daily expenses. It allows the user to
create and track their budgets to prevent overspending and adjusting their expenses accordingly.

## User Stories

| Version | As a ...              | I want to ...                                       | So that I can ...                                                                      |
|---------|-----------------------|-----------------------------------------------------|----------------------------------------------------------------------------------------|
| v1.0    | budgeted student      | set a budget limit from which expenses are deducted | track my expenses and avoid overrunning my budget                                      |
| v1.0    | new user              | add expenses                                        | plan my my expenditure based on the remaining budget                                   |
| v1.0    | forgetful user        | view my expenses                                    | remove incorrect or outdated entries from my transaction history                       |
| v1.0    | careless user         | edit my expenses                                    | modify or correct the entries in my transaction history                                |
| v1.0    | careless user         | delete my expenses                                  | remove incorrect or outdated entries from my transaction history                       |
| v1.0    | responsible user      | sort my expenses by date, name or amount            | have a clear timeline of how my budget has changed from the past to now                |
| v1.0    | responsible user      | add future payments                                 | budget accordingly based on expenses that will happen later on                         |
| v1.0    | careless user         | edit future payments                                | modify or correct the entries of my upcoming expenses                                  |
| v1.0    | careless user         | delete future payments                              | remove incorrect entries from my upcoming expenses                                     |
| v1.0    | responsible user      | view upcoming expenses within a period              | stay informed of the upcoming expenses within that time period and budget accordingly  |
| v1.0    | responsible user      | pay future payments                                 | complete future payments and add the expenses to transaction history                   |
| v1.0    | responsible user      | remain informed of the remaining budget             | complete my future payments by managing other expenses                                 |
| v2.0    | responsible user      | view my expenditure across different categories     | see which category is taking up the most budget                                        |
| v2.0    | international student | use the app in my home currency                     | conveniently make calculations                                                         |
| v2.0    | responsible user      | filter expenses by price                            | easily check which expenses cost more than $X amount, which can help with my budgeting |

## Non-Functional Requirements

1. Program should work on any operating system supporting Java 11 or above.
2. Does not require an active connection to the Internet to use the application.
3. Program should be accessible to users with no prior programming experience.
4. Program data will be saved persistently.
5. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be
   able to accomplish most of the tasks faster using commands than using the mouse.

## Glossary

* *glossary item* - Definition

## Instructions for Manual Testing

### Launch

1. Download the JAR file and move it into an empty folder.
2. On a command line application, change the current working directory to the same folder as the JAR file and run the
   app using:\
   `java -jar tp.jar`
3. Expected: The app's welcome message is printed onto the terminal along with list of all commands.

### Add

#### Add past expense

1. Prerequisites:
    - Ensure that a valid date is entered
    - Date is entered in the format: yyyymmdd
    - Ensure all fields are entered
2. Test case:
   `add expense book $/100 d/20230101`\
   Expected: Terminal shows choose category message. Enter a category number. Expense entry is added. Terminal shows
   successful message along with expenditure details (name, amount, date, category).

#### Add future expense

1. Prerequisites:
    - Ensure that a valid date is entered
    - Date is entered in the format: yyyymmdd
    - Ensure all fields are entered
2. Test case:
   `add future expense bill $/200 d/20230505`\
   Expected: Terminal shows choose category message. Enter a category number. Expense entry is added. Terminal shows
   successful message along with expenditure details (name, date, category).

#### Entering incomplete information

Test case: `add expense book $/100` or `add expense book d/20220101` or `add expense book`\
Expected: Terminal shows error message.

### Delete

#### Delete past expense

1. Prerequisites:
    - Ensure that a valid expense id is entered
    - Ensure all fields are entered
2. Test case:
   `delete expense id/1`\
   Expected: Expense entry is deleted. Terminal shows successful message along with expenditure details (name, amount,
   date, category).

#### Delete future expense

1. Prerequisites:
    - Ensure that a valid future expense id is entered
    - Ensure all fields are entered
2. Test case:
   `delete future expense id/1`\
   Expected: Expense entry is deleted. Terminal shows successful message along with expenditure details (name, date,
   category).

#### Entering incomplete information

Test case: `delete expense` or `delete expense id/12`\
Expected: Terminal shows error message.

### Edit

#### Edit past expense

1. Prerequisites:
    - Ensure that a valid expense id is entered
    - Ensure that a valid date is entered
    - Date is entered in the format: yyyymmdd
    - Ensure all fields are entered

2. Test case:
   `edit expense id/1 in/"amount/date/category"`\
   Enter type of the field to edit in the above format. \
   Expected: Terminal shows message to enter the information for the chosen field to edit. Successful message is shown
   on entering the information.

#### Edit future expense

1. Prerequisites:
    - Ensure that a valid expense id is entered
    - Ensure that a valid date is entered
    - Date is entered in the format: yyyymmdd
    - Ensure all fields are entered

2. Test case:
   `edit future expense id/1 in/"amount/date/category"`\
   Enter type of the field to edit in the above format. \
   Expected: Terminal shows message to enter the information for the chosen field to edit. Successful message is shown
   on entering the information.

### List

#### List past expense

Test case:
`list expenses`\
Expected: Terminal shows message to enter the number associated with the sorting type of expenses (by date/name/amount).
List of expenses is displayed based on the entered sorting type. If expense list is empty, terminal displays required
message.

#### List future expense

Test case:
`list future expenses`\
Expected: Terminal shows list of upcoming payments with the total amount due and current balance. A warning message is
displayed if the balance is insufficient.

### Balance

#### Set balance

1. Prerequisites:
    - Ensure all fields are entered

2. Test case:
   `set balance $/2000`\
   Expected: Terminal shows successful message.

#### Check balance

Test case:
`check balance`\
Expected: Terminal shows remaining balance.

#### Entering incomplete information

Test case: `set balance`\
Expected: Terminal shows error message.

### Upcoming Expenses

#### Check upcoming expenses

Test case:
`check upcoming expenses`\
Expected: Terminal shows message to choose period of upcoming expenses. Upcoming payments for the entered time period
are displayed with the total amount due and current balance. A warning message is displayed if the balance is
insufficient.

#### Pay upcoming expenses

1. Prerequisites:
    - Ensure that a valid future expense id is entered
    - Ensure all fields are entered
2. Test case:
   `pay 1`\
   Expected: Terminal shows successful message. The paid future expense is added to list of past expenses.

### Currency

#### Set currency

1. Prerequisites:
    - Ensure that a valid currency symbol is entered
2. Test case:
   `set currency USD`\
   Expected: Terminal shows successful message. 

#### Get currency

Test case:
`get currency`\
Expected: Terminal shows the currently set currency.

### List expenditure by category

Test case:
`list expenditure by category`\
Expected: Terminal displays the total expenditure across all categories.

### Filter expenses

#### Expenses above an amount
1. Prerequisites:
    - Ensure all fields are entered

2. Test case:
   `expenses above $/150`\
   Expected: Terminal displays all expenses of amount greater than the entered amount.

#### Expenses below an amount
1. Prerequisites:
    - Ensure all fields are entered

2. Test case:
   `expenses below $/2000`\
   Expected: Terminal displays all expenses of amount lower than the entered amount.

### Shutdown

After the testing is done, type the command `bye` to exit the program\
Terminal will display a goodbye message and the application would close in the command-line interface.
