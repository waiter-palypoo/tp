# Wilson Oh's Project Portfolio Page

## Project: MoneyMaster

MoneyMaster is a finance tracker which offers a variety of easy to use features which allows
you to manage your budget in the most efficient manner. It is a CLI (Command Line Interface)
application, and is written in Java.

Given below are my contributions to the project:

* **New Feature**: Added the ability to list expenses sorted by date, name or amount
  * Justification: This feature allows the user to conveniently list their expenses
in any way they need
  * Extra info on the implementation:
    * This feature is implemented in a way such that adding new "keys" to sort the expenses by is very easy. You simply
    need to write a new function which takes in a `Arraylist<Expense>` and return a `ArrayList<Expense>` sorted in the
    way you want, and then add the function to the `switch` statement.
* **New Feature**: Added local storage for the application
  * Completeness: The `storage` feature can be considered "complete" as our application as of now is simple enough such that
  all the necessary data can be stored in a single text file in a format we chose. Information such as the remaining
  budget, currency set, and all expenses are being stored and persisted throughout different sessions.
  * Looking forward: In the future if we decide to add more complicated features, a more conventional data storage format
  should be considered, such as `JSON/YAML/TOML`etc. This is because there are already tons of tried and tested parsing tools
  for those file formats, which will make our storage solution standardized, more maintainable and extensible.
* **New Feature**: Added ability to set the currency used in the application
  * Justification: As this application is meant to used by people all around the world, being able to
use the app in the user's home currency is of utmost importance
  * Extra info on the implementation:
    * As I did not wish to complicate the implementation of the application too much, the currency rates
are currently being hardcoded in a `exchange_rates.txt` file under `src/main/resources`. This has the advantage of not
requiring an API key from the user and the latency from fetching the updated list of exchange rates
    * The currency feature is implemented in a way such that the underlying expenses and balance is stored
    in terms of **SGD**. Only when the user requests to list the expenses or view the current balance, the
    price of each expense is then converted to the `currency` set.
    * This also means that when the user calls `add expense`, the price of the added expense is first normalized into
    **SGD** before being added in.
    * The currency is **SGD** by default, and any other currency being set will be stored in local storage so that
    it will persist throughout different sessions.

* **Code Contributed**: [Repo Sense Link](https://nus-cs2113-ay2223s2.github.io/tp-dashboard/?search=wilsonoh&breakdown=true)

* **Project Management**: Managed releases `v1.0` to `v2.1` (3 releases) on GitHub, created issues and assigned tags.

* **Documentation**
  * Developer Guide:
    * Added sequence diagrams and explanations for the `Storage` and `Parser` class
    * Added implementaion details and sequence diagram for the `set currency` feature
  * User Guide:
    * Added instructions for using the `set/get currency` commands
    * Added detailed instructions for the `edit currency` command
    * Added examples and expected output for `check upcoming expenses`, `pay future expense` and `bye`
