# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

## Design & implementation

## Storage Component

<img src="diagrams/storage.puml" width="280" />

The `Storage` class handles the validation and creation of the `duke_data.txt` file, which contains all the necessary 
data regarding the user's budget and expenses.
### Sequence Explanation
* Upon creation of the `Storage` object, the constructor first checks whether the directory and file
`${CWD}/data/duke_data.txt` exists, and creates them otherwise.
* Before entering the main loop, `Duke` will first call `Storage::loadDataExpenses()`, which reads from `duke_data.txt`
and initializes and *returns* a `ExpenseManager` object containing the budget, `Expenses` and `FutureExpenses` from the data file.
* Duke then uses the returned `ExpenseManager` object for the current session
* After every command, `Duke` will then call `Storage::saveExpenses(ExpenseManager)`, which serializes the current state of
the `ExpenseManager` and writes the serialized data into `duke_data.txt`, making sure that the latest state of the app is always saved.

## Product scope
### Target user profile

{Describe the target user profile}

### Value proposition

{Describe the value proposition: what problem does it solve?}

## User Stories

|Version| As a ... | I want to ... | So that I can ...|
|--------|----------|---------------|------------------|
|v1.0|new user|see usage instructions|refer to them when I forget how to use the application|
|v2.0|user|find a to-do item by name|locate a to-do without having to go through the entire list|

## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}
