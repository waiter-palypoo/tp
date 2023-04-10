# Goh Jing Hong - Project Portfolio Page

## Project: MoneyMaster
MoneyMaster is a CLI based desktop application that helps the user keep track of their finances. It keeps track of the 
user's past expenditure and expected future payments in order to help the user better manage their finances.

### Summary of Contributions
* **New Feature** - Added the ability to add and delete future expenses
  * What it does: Allows the user to add or remove a payment to be settled in the future.
  * Justification: Allows the user to better manage their finances by being able to keep track of future expenses.
* **New Feature** - Added the ability to list all future expenses
  * What it does: Allows the user to see a list of all existing future expenses and also checks if the user has
  sufficient balance to pay for each and all of them.
  * Justification: Lets the user see a complete overview of their future expenses and tell if they can afford the future
  payments.
* **New Feature** - Added the ability to list upcoming future expenses in a certain time period
  * What it does: Allows the user to see the future expenses upcoming within a period of 1 week, 1 month or 3 months, 
  and informs the user if they have sufficient balance to pay for them in the given timeframe.
  * Justification: Allows the user to see which future expenses are more urgent, letting them manage their finances
  better.
* **New Feature** - Added the ability to pay for a future expense
  * What it does: Allows the user to set a future expense as paid, changing it to a past expense
  * Justification: Lets the user conveniently manage the transition between future and paid expenses.
* **New Feature** - Added a warning on startup if a future expense is past its due date.
  * What it does: When the application is launched, a check is performed on the saved list of future expenses. If any of
  them are past their due date in system time, the user is alerted.
  * Justification: Serves as a reminder to the user if they have yet to pay (or indicate paid) for a future expense.
* **Code contributed**: [RepoSense Link](https://nus-cs2113-ay2223s2.github.io/tp-dashboard/?search=waiter-palypoo&breakdown=true)
* **Contributions to DG**: Added implementation and sequence diagram for pay feature, added architecture diagram.