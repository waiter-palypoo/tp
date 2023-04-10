# Nguyen Quang Anh - Project Portfolio Page

## Project: MoneyMaster

MoneyMaster is a CLI finance tracker which allows the user to keep track of their expenses in the past as well as future
expenses. The program is written in Java

The following is what I have contributed to the project.

* **New Feature**: Added `ExpenseManager`, `Expense`, `Ui`, `Parser` classes
    * Justification: Provide a basic skeleton code for the team to jump start the project more effectively.
    * Extra info on the implementation:
        * These classes are based on the basic/minimal OOP classes required in the iP. The `ExpenseManager` class is
          used to keep track of the expenses, the `Expense` class is used to contain information relating to an expense,
          the `Ui` class contains print functions for interactions between the user and the program, the `Parser` class
          is used to handle commands from the user.
        * These classes are added with a few basic features such as adding, deleting, listing and editing expenses.
* **New Feature**: Added ability to add the `Expense` onto a list.
    * Justification: This is the most basic feature for our app, to add the expenses onto an `ArrayList<Expenses>` to
      keep track of them.
    * Extra info on the implementation:
        * To add a new `Expense`, the user would be asked of 4 inputs. How much is the expense, on what date is the
          expense. The user will also be separately asked for the category of the expense (I.e: Food & Drinks,
          Transportation, etc).
        * All of these expenses will then be added onto an `ArrayList`.
* **New Feature**: Added ability to delete an `Expense` from the list.
    * Justification: As a financial tracker, we would like the user to be able to delete an expense from the list in the
      case of a false input or the expense has been refunded.
    * Extra info on the implementation:
        * The user can delete an expense simply by inputting the Id for the expense which is the position of the expense
          on the list.
        * Using an `ArrayList` makes the implementation of this feature convenient due to he flexibility of a dynamic
          array.
* **New Feature**: Added ability to edit the `Expense`.
    * Justification: As a financial tracker, we would like the user to be able to edit an expense in the case of a false
      input.
    * Extra info on the implementation:
        * The user simply have to input the Id for the expense which is the position of the expense on the list. The
          program will then ask the user which part of the expense is to be changed (I.e: Amount, Category, Date, etc).
          The program will then instruct the user how to make the specific change.
        * This feature is implemented mainly using setters from the `Expense` class.
* **New Feature**: Added ability to sort the `Expense` based on a certain amount threshold.
    * Justification: As a financial tracker, we would like the user to be able to look for expenses that are above or
      below a certain amount.
    * Extra info on the implementation:
        * This feature is implemented simply by filtering through the expenses and append those that meet the
          requirement onto another list before printing that list out for the user. This method of implementation is
          rather convenient as I can rely on existing methods that we already have to do it.
* **Code Contributed
  **: [Repo Sense Link](https://nus-cs2113-ay2223s2.github.io/tp-dashboard/?search=quanganh2810&breakdown=true)

* **Demo video**: Did the demo video for the project

* **Documentation**
    * Developer Guide:
        * Added class diagram for `ExpenseManager` class.
        * Added sequence diagram for `add expense`.
    * User Guide:
        * Added the command `add expense`/`add future expense`

