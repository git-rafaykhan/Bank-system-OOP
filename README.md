# Bank Account Management System (Java OOP)

A simple, beginner-friendly console-based Banking System built in Java to demonstrate the four fundamental principles of Object-Oriented Programming (OOP): **Encapsulation**, **Inheritance**, **Polymorphism**, and **Abstraction**.

> [!TIP]
> **Looking for a line-by-line breakdown and OOP Viva/Exam guide?** Check out **[CODE_EXPLANATION.md](file:///d:/Hp/Desktop/bank-sys-oop/CODE_EXPLANATION.md)**.

---

## Table of Contents
1. [Project Overview](#project-overview)
2. [Project Structure](#project-structure)
3. [The Four Core OOP Principles in the Code](#the-four-core-oop-principles-in-the-code)
4. [Class-by-Class Detailed Explanation](#class-by-class-detailed-explanation)
   - [Account.java](#1-accountjava-abstract-base-class)
   - [SavingsAccount.java](#2-savingsaccountjava-child-class)
   - [CurrentAccount.java](#3-currentaccountjava-child-class)
   - [Bank.java](#4-bankjava-manager-class)
   - [Main.java](#5-mainjava-console-ui--entry-point)
5. [Key Business Logic & Formulas](#key-business-logic--formulas)
6. [How Input Validation & Error Handling Works](#how-input-validation--error-handling-works)
7. [How to Compile and Run](#how-to-compile-and-run)
8. [Sample Execution Flow](#sample-execution-flow)
9. [Complete Source Code (For Easy Memorization)](#complete-source-code-for-easy-memorization)
   - [Account.java](#1-accountjava)
   - [SavingsAccount.java](#2-savingsaccountjava)
   - [CurrentAccount.java](#3-currentaccountjava)
   - [Bank.java](#4-bankjava)
   - [Main.java](#5-mainjava)

---

## Project Overview

This project simulates a bank management system where a bank can manage multiple types of customer accounts:
- **Savings Account**: Offers interest calculation on the balance and strictly prohibits withdrawals exceeding the available balance.
- **Current Account**: Supports an overdraft limit, allowing account holders to withdraw more than their balance up to a specified limit.

The currency used across all operations is **PKR** (Pakistani Rupee).

---

## Project Structure

```text
bank-sys-oop/
├── src/
│   ├── Account.java          # Abstract parent class
│   ├── SavingsAccount.java   # Child class for savings accounts
│   ├── CurrentAccount.java   # Child class for current/checking accounts
│   ├── Bank.java             # Holds and manages the list of accounts
│   └── Main.java             # Entry point and interactive console menu
└── README.md                 # Complete documentation
```

---

## The Four Core OOP Principles in the Code

### 1. Abstraction
- **Definition**: Hiding internal implementation details and showing only essential features to the user.
- **In this project**: 
  - `Account` is defined as an `abstract class`. You cannot instantiate a generic `new Account()`; you must create either a `SavingsAccount` or a `CurrentAccount`.
  - The method `withdraw(double amount)` is declared `abstract` in `Account.java`. It defines *what* every account must do (withdraw money), but leaves *how* it is done to the specific account types.

### 2. Encapsulation
- **Definition**: Bundling data (variables) and methods that operate on the data into a single unit, and restricting direct access using access modifiers.
- **In this project**:
  - Fields like `accountNumber`, `holderName`, `interestRate`, and `overdraftLimit` are declared `private`.
  - `balance` is declared `protected` so derived classes can directly access it while keeping it protected from external direct modification.
  - Public getter methods (`getAccountNumber()`, `getHolderName()`, `getBalance()`) provide controlled, read-only access to internal state.
  - Balance modifications can only happen through validated operations (`deposit()`, `withdraw()`, `addInterest()`).

### 3. Inheritance
- **Definition**: A mechanism where a new class inherits properties and behaviors from an existing class (code reuse).
- **In this project**:
  - `SavingsAccount extends Account`
  - `CurrentAccount extends Account`
  - Both child classes inherit common attributes (`accountNumber`, `holderName`, `balance`) and methods (`deposit()`, `getAccountNumber()`, `displayInfo()`) using `super(...)` constructors.

### 4. Polymorphism
- **Definition**: The ability of an object to take on many forms (dynamic method dispatch at runtime).
- **In this project**:
  - **Method Overriding**: Both `SavingsAccount` and `CurrentAccount` provide their own unique implementation of `withdraw(double amount)` and `displayInfo()`.
  - **Polymorphic Collection**: `Bank.java` maintains a single `ArrayList<Account> accounts`. It can store both `SavingsAccount` and `CurrentAccount` objects together. When `acc.withdraw(...)` or `acc.displayInfo()` is called, Java dynamically calls the correct subclass version at runtime.

---

## Class-by-Class Detailed Explanation

### 1. `Account.java` (Abstract Base Class)
Located at: `src/Account.java`

- **Fields**:
  - `private int accountNumber`: Unique numeric identifier for the account.
  - `private String holderName`: Full name of the account owner.
  - `protected double balance`: Current account balance in PKR (accessible by child classes).
- **Constructor**:
  - `Account(int accountNumber, String holderName, double balance)`: Initializes base fields.
- **Methods**:
  - `getAccountNumber()`: Returns account number.
  - `getHolderName()`: Returns account holder name.
  - `getBalance()`: Returns current balance.
  - `deposit(double amount)`: Validates that `amount > 0`, increases balance, and prints confirmation.
  - `abstract void withdraw(double amount)`: Abstract method enforced on all child classes.
  - `displayInfo()`: Prints standard account details (account number, holder name, and balance).

---

### 2. `SavingsAccount.java` (Child Class)
Located at: `src/SavingsAccount.java`

- **Inherits from**: `Account`
- **Additional Field**:
  - `private double interestRate`: Annual/period interest rate percentage (e.g., `5.0` for 5%).
- **Constructor**:
  - Calls `super(accountNumber, holderName, balance)` and sets `interestRate`.
- **Methods**:
  - `getInterestRate()`: Returns the interest rate.
  - `addInterest()`:
    $$\text{interest} = \text{balance} \times \left(\frac{\text{interestRate}}{100}\right)$$
    $$\text{balance} = \text{balance} + \text{interest}$$
    Calculates interest, adds it to the balance, and prints the updated balance.
  - `withdraw(double amount)`:
    - If `amount <= 0`: Rejects as invalid amount.
    - If `amount <= balance`: Deducts amount from balance.
    - If `amount > balance`: Rejects with `"Insufficient balance!"`.
  - `displayInfo()`: Calls `super.displayInfo()` and appends account type and interest rate.

---

### 3. `CurrentAccount.java` (Child Class)
Located at: `src/CurrentAccount.java`

- **Inherits from**: `Account`
- **Additional Field**:
  - `private double overdraftLimit`: Maximum amount the balance can go negative (e.g., `PKR 5000.0`).
- **Constructor**:
  - Calls `super(accountNumber, holderName, balance)` and sets `overdraftLimit`.
- **Methods**:
  - `getOverdraftLimit()`: Returns the overdraft limit.
  - `withdraw(double amount)`:
    - If `amount <= 0`: Rejects as invalid amount.
    - If `amount <= balance + overdraftLimit`: Deducts amount from balance (balance can become negative down to `-overdraftLimit`).
    - If `amount > balance + overdraftLimit`: Rejects with `"Withdrawal exceeds overdraft limit!"`.
  - `displayInfo()`: Calls `super.displayInfo()` and appends account type and overdraft limit.

---

### 4. `Bank.java` (Manager Class)
Located at: `src/Bank.java`

- **Field**:
  - `private ArrayList<Account> accounts`: Dynamic list holding all account objects.
- **Methods**:
  - `addAccount(Account account)`: Adds a new `Account` (Savings or Current) to the list.
  - `findAccountByNumber(int accountNumber)`: Iterates through the list using a standard `for` loop and returns the matching `Account` or `null` if not found.
  - `depositToAccount(int accountNumber, double amount)`: Finds the account and invokes its `deposit(amount)` method.
  - `withdrawFromAccount(int accountNumber, double amount)`: Finds the account and invokes its polymorphic `withdraw(amount)` method.
  - `applyInterestToAccount(int accountNumber)`: Finds the account, verifies it is a `SavingsAccount`, and calls `addInterest()`.
  - `showAllAccounts()`: Iterates through all accounts in the list and calls `acc.displayInfo()`.

---

### 5. `Main.java` (Console UI & Entry Point)
Located at: `src/Main.java`

- Manages the application lifecycle through a `while (running)` loop.
- Uses `java.util.Scanner` to read user console input.
- Provides a clean text menu with 8 options:
  1. **Create Savings Account**: Prompts for account number, holder name, initial balance, and interest rate.
  2. **Create Current Account**: Prompts for account number, holder name, initial balance, and overdraft limit.
  3. **Deposit**: Prompts for account number and deposit amount.
  4. **Withdraw**: Prompts for account number and withdrawal amount.
  5. **View Account Details**: Prompts for account number and displays full details.
  6. **View All Accounts**: Lists every account registered in the system.
  7. **Apply Interest**: Prompts for savings account number and applies interest.
  8. **Exit**: Gracefully exits the application.

---

## Key Business Logic & Formulas

| Feature | Formula / Rule | Example |
| :--- | :--- | :--- |
| **Deposit** | `balance = balance + amount` (where `amount > 0`) | Deposit PKR 5,000 into PKR 10,000 balance $\rightarrow$ New balance = PKR 15,000 |
| **Savings Withdrawal** | Allowed only if `amount <= balance` | Balance PKR 5,000 $\rightarrow$ Withdraw PKR 6,000 is **Rejected** |
| **Current Withdrawal** | Allowed if `amount <= balance + overdraftLimit` | Balance PKR 2,000, Overdraft PKR 3,000 $\rightarrow$ Withdraw PKR 4,000 $\rightarrow$ Balance = PKR -2,000 |
| **Interest Calculation** | $\text{Interest} = \text{balance} \times (\text{rate} / 100)$ | Balance PKR 30,000, Rate 13% $\rightarrow$ Interest = PKR 3,900 $\rightarrow$ Balance = PKR 33,900 |

---

## How Input Validation & Error Handling Works

1. **Non-Integer Menu Input**:
   - `scanner.hasNextInt()` is checked before reading menu choices.
   - Entering letters (e.g. `abc`) will not crash the application with `InputMismatchException`; it displays an error message and prompts again.
2. **Negative Amounts**:
   - `deposit()` and `withdraw()` reject numbers $\le 0$ with descriptive error messages.
3. **Non-Existent Accounts**:
   - Operations on invalid account numbers display `"Account not found!"` without crashing.

---

---

## How to Compile and Run

You can run the project using any of the following methods:

### Method 1: Running from Project Root Directory (`bank-sys-oop/`)

1. **Open Terminal / Command Prompt** in the project root folder (`bank-sys-oop`).
2. **Compile all source files**:
   ```bash
   javac src/*.java
   ```
3. **Run the application**:
   ```bash
   java -cp src Main
   ```

---

### Method 2: Running from Inside the `src/` Directory

If your terminal is inside the `src` folder (e.g. `bank-sys-oop/src`):

1. **Navigate to `src` (if not already there)**:
   ```bash
   cd src
   ```
2. **Compile all Java files**:
   ```bash
   javac *.java
   ```
3. **Run the compiled program**:
   ```bash
   java Main
   ```
   *(Or with modern Java 11+, you can directly run: `java Main.java`)*

---

### Method 3: Running Inside an IDE (VS Code, IntelliJ, Eclipse)

1. Open the project folder (`bank-sys-oop`) in your IDE.
2. Open `src/Main.java`.
3. Click the **Run** / **Play** button or right-click `Main.java` and select **Run 'Main.main()'**.

---

### Common Terminal Mistakes & Fixes

- **Mistake**: `java Main.class`
  - **Error**: `ClassNotFoundException: Main/class`
  - **Fix**: Do not include `.class` when running compiled code. Use `java Main` (or `java -cp src Main`).
- **Mistake**: `java .\Main.java` inside root instead of `src`
  - **Fix**: Always specify the folder or compile first (`javac src/*.java` then `java -cp src Main`).

---

## Sample Execution Flow

```text
=== Bank Management System ===
1. Create Savings Account
2. Create Current Account
3. Deposit
4. Withdraw
5. View Account Details
6. View All Accounts
7. Exit
Enter your choice (1-7): 1
Enter Account Number: 101
Enter Account Holder Name: Nazish Khan
Enter Initial Balance: 30000
Enter Interest Rate (%): 13
Account added successfully!

=== Bank Management System ===
...
Enter your choice (1-7): 3
Enter Account Number: 101
Enter Deposit Amount: 5000
Deposited: PKR 5000.0

=== Bank Management System ===
...
Enter your choice (1-7): 5
Enter Account Number: 101

--- Account Details ---
Account Number: 101
Holder Name: Nazish Khan
Balance: PKR 35000.0
Account Type: Savings Account
Interest Rate: 13.0%

=== Bank Management System ===
...
Enter your choice (1-7): 7
Thank you for using the Bank Management System. Goodbye!
```

---

## Complete Source Code (For Easy Memorization)

### 1. `Account.java`
```java
public abstract class Account {
    private int accountNumber;
    private String holderName;
    protected double balance;

    public Account(int accountNumber, String holderName, double balance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: PKR " + amount);
        } else {
            System.out.println("Invalid deposit amount!");
        }
    }

    public abstract void withdraw(double amount);

    public void displayInfo() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Holder Name: " + holderName);
        System.out.println("Balance: PKR " + balance);
    }
}
```

---

### 2. `SavingsAccount.java`
```java
public class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(int accountNumber, String holderName, double balance, double interestRate) {
        super(accountNumber, holderName, balance);
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void addInterest() {
        double interest = balance * (interestRate / 100.0);
        balance += interest;
        System.out.println("Interest of PKR " + interest + " added. New balance: PKR " + balance);
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount!");
        } else if (amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn: PKR " + amount + ". Remaining balance: PKR " + balance);
        } else {
            System.out.println("Insufficient balance! Current balance: PKR " + balance);
        }
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Account Type: Savings Account");
        System.out.println("Interest Rate: " + interestRate + "%");
    }
}
```

---

### 3. `CurrentAccount.java`
```java
public class CurrentAccount extends Account {
    private double overdraftLimit;

    public CurrentAccount(int accountNumber, String holderName, double balance, double overdraftLimit) {
        super(accountNumber, holderName, balance);
        this.overdraftLimit = overdraftLimit;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount!");
        } else if (amount <= balance + overdraftLimit) {
            balance -= amount;
            System.out.println("Withdrawn: PKR " + amount + ". Remaining balance: PKR " + balance);
        } else {
            System.out.println("Withdrawal exceeds overdraft limit of PKR " + overdraftLimit + "! Current balance: PKR " + balance);
        }
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Account Type: Current Account");
        System.out.println("Overdraft Limit: PKR " + overdraftLimit);
    }
}
```

---

### 4. `Bank.java`
```java
import java.util.ArrayList;

public class Bank {
    private ArrayList<Account> accounts;

    public Bank() {
        accounts = new ArrayList<>();
    }

    public void addAccount(Account account) {
        accounts.add(account);
        System.out.println("Account added successfully!");
    }

    public Account findAccountByNumber(int accountNumber) {
        for (int i = 0; i < accounts.size(); i++) {
            Account acc = accounts.get(i);
            if (acc.getAccountNumber() == accountNumber) {
                return acc;
            }
        }
        return null;
    }

    public void depositToAccount(int accountNumber, double amount) {
        Account acc = findAccountByNumber(accountNumber);
        if (acc != null) {
            acc.deposit(amount);
        } else {
            System.out.println("Account not found!");
        }
    }

    public void withdrawFromAccount(int accountNumber, double amount) {
        Account acc = findAccountByNumber(accountNumber);
        if (acc != null) {
            acc.withdraw(amount);
        } else {
            System.out.println("Account not found!");
        }
    }

    public void applyInterestToAccount(int accountNumber) {
        Account acc = findAccountByNumber(accountNumber);
        if (acc == null) {
            System.out.println("Account not found!");
        } else if (acc instanceof SavingsAccount) {
            SavingsAccount savAcc = (SavingsAccount) acc;
            savAcc.addInterest();
        } else {
            System.out.println("Interest can only be applied to Savings Accounts!");
        }
    }

    public void showAllAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts found in the bank.");
            return;
        }

        System.out.println("\n--- All Bank Accounts ---");
        for (int i = 0; i < accounts.size(); i++) {
            Account acc = accounts.get(i);
            System.out.println("-------------------------");
            acc.displayInfo();
        }
        System.out.println("-------------------------");
    }
}
```

---

### 5. `Main.java`
```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank();
        boolean running = true;

        while (running) {
            System.out.println("\n=== Bank Management System ===");
            System.out.println("1. Create Savings Account");
            System.out.println("2. Create Current Account");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. View Account Details");
            System.out.println("6. View All Accounts");
            System.out.println("7. Apply Interest");
            System.out.println("8. Exit");
            System.out.print("Enter your choice (1-8): ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid choice. Please enter a number between 1 and 8.");
                if (scanner.hasNext()) {
                    scanner.next();
                }
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Account Number: ");
                    int savAccNum = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Account Holder Name: ");
                    String savName = scanner.nextLine();
                    System.out.print("Enter Initial Balance: ");
                    double savBalance = scanner.nextDouble();
                    System.out.print("Enter Interest Rate (%): ");
                    double interestRate = scanner.nextDouble();
                    scanner.nextLine();

                    SavingsAccount savAccount = new SavingsAccount(savAccNum, savName, savBalance, interestRate);
                    bank.addAccount(savAccount);
                    break;

                case 2:
                    System.out.print("Enter Account Number: ");
                    int curAccNum = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Account Holder Name: ");
                    String curName = scanner.nextLine();
                    System.out.print("Enter Initial Balance: ");
                    double curBalance = scanner.nextDouble();
                    System.out.print("Enter Overdraft Limit: ");
                    double overdraftLimit = scanner.nextDouble();
                    scanner.nextLine();

                    CurrentAccount curAccount = new CurrentAccount(curAccNum, curName, curBalance, overdraftLimit);
                    bank.addAccount(curAccount);
                    break;

                case 3:
                    System.out.print("Enter Account Number: ");
                    int depAccNum = scanner.nextInt();
                    System.out.print("Enter Deposit Amount: ");
                    double depAmount = scanner.nextDouble();
                    scanner.nextLine();
                    bank.depositToAccount(depAccNum, depAmount);
                    break;

                case 4:
                    System.out.print("Enter Account Number: ");
                    int withAccNum = scanner.nextInt();
                    System.out.print("Enter Withdrawal Amount: ");
                    double withAmount = scanner.nextDouble();
                    scanner.nextLine();
                    bank.withdrawFromAccount(withAccNum, withAmount);
                    break;

                case 5:
                    System.out.print("Enter Account Number: ");
                    int viewAccNum = scanner.nextInt();
                    scanner.nextLine();
                    Account acc = bank.findAccountByNumber(viewAccNum);
                    if (acc != null) {
                        System.out.println("\n--- Account Details ---");
                        acc.displayInfo();
                    } else {
                        System.out.println("Account not found!");
                    }
                    break;

                case 6:
                    bank.showAllAccounts();
                    break;

                case 7:
                    System.out.print("Enter Account Number: ");
                    int intAccNum = scanner.nextInt();
                    scanner.nextLine();
                    bank.applyInterestToAccount(intAccNum);
                    break;

                case 8:
                    System.out.println("Thank you for using the Bank Management System. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please select an option between 1 and 8.");
                    break;
            }
        }

        scanner.close();
    }
}
```
