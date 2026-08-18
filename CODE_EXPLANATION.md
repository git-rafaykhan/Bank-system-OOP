# Bank Account Management System - Code & OOP Explanation Guide

This guide breaks down **every single file**, explaining the exact code line-by-line and showing how the four core **Object-Oriented Programming (OOP)** principles (**Abstraction**, **Encapsulation**, **Inheritance**, **Polymorphism**) work in practice.

---

## Table of Contents
1. [OOP Principles Summary](#1-oop-principles-summary)
2. [How the Classes Connect (Architecture)](#2-how-the-classes-connect-architecture)
3. [File 1: Account.java (Abstract Base Class)](#3-file-1-accountjava-abstract-base-class)
4. [File 2: SavingsAccount.java (Child Class)](#4-file-2-savingsaccountjava-child-class)
5. [File 3: CurrentAccount.java (Child Class)](#5-file-3-currentaccountjava-child-class)
6. [File 4: Bank.java (Manager Class)](#6-file-4-bankjava-manager-class)
7. [File 5: Main.java (Console Menu & Entry Point)](#7-file-5-mainjava-console-menu--entry-point)
8. [Step-by-Step Flow: How Methods Talk to Each Other](#8-step-by-step-flow-how-methods-talk-to-each-other)
9. [Common Viva / Exam Questions & Answers](#9-common-viva--exam-questions--answers)

---

## 1. OOP Principles Summary

| OOP Principle | What It Means | Where It Is in the Code |
| :--- | :--- | :--- |
| **1. Abstraction** | Hiding implementation details and only showing essential features. | `public abstract class Account`<br>`public abstract void withdraw(double amount);` |
| **2. Encapsulation** | Keeping data safe using `private` variables and controlled `getters`/methods. | `private int accountNumber;`<br>`private String holderName;`<br>`getAccountNumber()`, `getBalance()` |
| **3. Inheritance** | Child classes reusing code from a parent class using `extends`. | `SavingsAccount extends Account`<br>`CurrentAccount extends Account`<br>`super(accountNumber, holderName, balance);` |
| **4. Polymorphism** | "Many forms" — executing different behavior through the same method name. | Subclasses override `withdraw()` and `displayInfo()`.<br>`ArrayList<Account>` holds both Savings and Current accounts and calls the right `withdraw()` at runtime. |

---

## 2. How the Classes Connect (Architecture)

```text
               +-----------------------------+
               |        Account (Abstract)   |
               +-----------------------------+
               | - accountNumber: int        |
               | - holderName: String        |
               | # balance: double           |
               +-----------------------------+
               | + deposit(amount)           |
               | + withdraw(amount) [ABS]    |
               | + displayInfo()             |
               +-----------------------------+
                              ▲
                              │ extends (Inheritance)
              ┌───────────────┴───────────────┐
              │                               │
+-----------------------------+ +-----------------------------+
|       SavingsAccount        | |        CurrentAccount       |
+-----------------------------+ +-----------------------------+
| - interestRate: double      | | - overdraftLimit: double    |
+-----------------------------+ +-----------------------------+
| + addInterest()             | | + withdraw(amount) [OVERRIDE|
| + withdraw(amount) [OVERRIDE| | + displayInfo()    [OVERRIDE|
| + displayInfo()    [OVERRIDE| +-----------------------------+
+-----------------------------+ +-----------------------------+
              ▲                               ▲
              └───────────────┬───────────────┘
                              │ stored in
               +-----------------------------+
               |            Bank             |
               +-----------------------------+
               | - accounts: ArrayList<Account>
               +-----------------------------+
               | + addAccount()              |
               | + findAccountByNumber()     |
               | + depositToAccount()        |
               | + withdrawFromAccount()     |
               | + showAllAccounts()         |
               +-----------------------------+
                              ▲
                              │ used by
               +-----------------------------+
               |            Main             |
               | (Console Menu & Scanner)    |
               +-----------------------------+
```

---

## 3. File 1: `Account.java` (Abstract Base Class)

### The Code:
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

### Explanation & OOP in `Account.java`:
1. **`abstract class Account` (Abstraction)**:
   - We make `Account` abstract because an "Account" in general cannot exist without being a specific type (like Savings or Current).
   - Prevents anyone from doing `new Account()`.
2. **Access Modifiers (Encapsulation)**:
   - `private int accountNumber;` and `private String holderName;`: Hidden from outside modification.
   - `protected double balance;`: `protected` means child classes (`SavingsAccount`, `CurrentAccount`) can directly access and modify `balance`, but external classes cannot.
3. **Getters (`getAccountNumber()`, `getBalance()`)**:
   - Provide safe, read-only access to private fields.
4. **`deposit(double amount)`**:
   - Common logic shared by all accounts: checks `amount > 0`, then adds to `balance`.
5. **`public abstract void withdraw(double amount);` (Abstraction)**:
   - Notice there is no `{}` body! It has a semicolon `;`.
   - Every account *must* have a withdraw method, but because Savings and Current accounts have completely different withdrawal rules, the parent leaves the implementation to the child classes.
6. **`displayInfo()`**:
   - Prints the 3 basic fields. Child classes can call `super.displayInfo()` and add their own fields.

---

## 4. File 2: `SavingsAccount.java` (Child Class)

### The Code:
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

### Explanation & OOP in `SavingsAccount.java`:
1. **`extends Account` (Inheritance)**:
   - `SavingsAccount` automatically inherits `accountNumber`, `holderName`, `balance`, and `deposit()`.
2. **`super(...)` Constructor**:
   - `super(accountNumber, holderName, balance);` passes the first 3 values to the parent `Account` constructor.
   - `this.interestRate = interestRate;` sets its own unique variable.
3. **`addInterest()` Method**:
   - Calculates interest: $\text{interest} = \text{balance} \times (\text{interestRate} / 100)$.
   - Adds interest directly to `balance`.
4. **`@Override withdraw(double amount)` (Polymorphism - Method Overriding)**:
   - Implements the abstract `withdraw` from `Account`.
   - Rule: You can only withdraw if `amount <= balance`. No negative balance is allowed.
5. **`@Override displayInfo()`**:
   - Calls `super.displayInfo()` to print the basic info, then prints `interestRate`.

---

## 5. File 3: `CurrentAccount.java` (Child Class)

### The Code:
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

### Explanation & OOP in `CurrentAccount.java`:
1. **`extends Account` (Inheritance)**:
   - Reuses base account functionality and adds `overdraftLimit`.
2. **`@Override withdraw(double amount)` (Polymorphism - Method Overriding)**:
   - Unique Current Account rule:
   - If balance is PKR 10,000 and overdraft limit is PKR 5,000, you can withdraw up to PKR 15,000 (`balance + overdraftLimit`).
   - If you withdraw PKR 12,000, balance becomes `PKR -2000.0`.
   - If amount exceeds `balance + overdraftLimit`, it rejects the withdrawal.

---

## 6. File 4: `Bank.java` (Manager Class)

### The Code:
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

### Explanation & OOP in `Bank.java`:
1. **`ArrayList<Account> accounts` (Polymorphic Collection)**:
   - Notice the type is `<Account>`. Because of polymorphism, this single list can hold `SavingsAccount` objects and `CurrentAccount` objects at the same time.
2. **`findAccountByNumber(int accountNumber)`**:
   - Uses a standard indexed `for` loop:
     `for (int i = 0; i < accounts.size(); i++)`
   - Gets account with `accounts.get(i)` and checks if `acc.getAccountNumber() == accountNumber`.
   - Returns the `Account` object if found, or `null` if not found.
3. **`withdrawFromAccount(...)` (Runtime Dynamic Polymorphism)**:
   - When `acc.withdraw(amount)` is called:
     - If `acc` is a `SavingsAccount`, Java runs `SavingsAccount.withdraw()`.
     - If `acc` is a `CurrentAccount`, Java runs `CurrentAccount.withdraw()`.
   - `Bank` doesn't need to know which type it is; Java figures it out automatically at runtime!

---

## 7. File 5: `Main.java` (Console Menu & Entry Point)

### The Code:
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
            System.out.println("7. Exit");
            System.out.print("Enter your choice (1-7): ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid choice. Please enter a number between 1 and 7.");
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
                    System.out.println("Thank you for using the Bank Management System. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please select an option between 1 and 7.");
                    break;
            }
        }

        scanner.close();
    }
}
```

### Explanation of `Main.java`:
1. **`while (running)` Loop**:
   - Keeps the menu running continuously until the user enters `7` (Exit).
2. **`scanner.hasNextInt()` Check**:
   - Protects the system: If the user types letters like `abc` instead of a number, `hasNextInt()` catches it, prints a warning message, and restarts the loop without crashing the program.
3. **`scanner.nextLine()` after `nextInt()`/`nextDouble()`**:
   - Cleans the Enter key (newline character) from the input buffer so subsequent string inputs (`scanner.nextLine()`) read properly.
4. **`switch (choice)`**:
   - Routes each number `1` through `7` to the appropriate bank action.

---

## 8. Step-by-Step Flow: How Methods Talk to Each Other

When a user selects **Option 4 (Withdraw)**:
1. `Main.java` prompts for `accountNumber` (e.g. `101`) and `amount` (e.g. `500`).
2. `Main.java` calls `bank.withdrawFromAccount(101, 500)`.
3. `Bank.java` calls its internal helper `findAccountByNumber(101)`.
4. If found, `Bank.java` calls `acc.withdraw(500)`.
5. Java dynamically checks if `acc` is `SavingsAccount` or `CurrentAccount`:
   - If `SavingsAccount`: checks `amount <= balance`.
   - If `CurrentAccount`: checks `amount <= balance + overdraftLimit`.
6. Prints the success/failure message with the new balance in `PKR`.

---

## 9. Common Viva / Exam Questions & Answers

### Q1: Why did you make `Account` an abstract class instead of a regular class?
> **Answer**: Because a generic "Account" has no real-world meaning on its own without knowing if it is a Savings or Current Account. Making it abstract prevents direct instantiation (`new Account()`) and forces child classes to provide their own withdrawal implementation.

### Q2: Why is the `withdraw()` method abstract in `Account`, but `deposit()` is not?
> **Answer**: Deposit works the exact same way for all accounts (adding money to balance). However, withdrawal rules are completely different between Savings (no overdraft allowed) and Current (overdraft allowed up to limit). Therefore, `deposit()` is implemented in `Account`, while `withdraw()` is declared abstract.

### Q3: Why is `balance` declared as `protected` instead of `private`?
> **Answer**: `protected` allows child classes (`SavingsAccount` and `CurrentAccount`) to directly read and update the `balance` variable during deposits, withdrawals, and interest additions, while keeping it safe from external classes.

### Q4: How is Polymorphism demonstrated in this project?
> **Answer**: In two ways:
> 1. **Method Overriding**: Both `SavingsAccount` and `CurrentAccount` override the `withdraw()` and `displayInfo()` methods with their own specific logic.
> 2. **Dynamic Binding / Polymorphic List**: The `Bank` class stores all accounts in an `ArrayList<Account>`. When calling `acc.withdraw()`, Java automatically determines at runtime which version of `withdraw()` to execute based on the actual object type.

### Q5: What is Encapsulation and where is it used?
> **Answer**: Encapsulation is data hiding. In this project, variables like `accountNumber` and `holderName` are `private`, and access is only granted through getter methods (`getAccountNumber()`, `getHolderName()`). Balance modifications can only occur through validated methods (`deposit()`, `withdraw()`).
