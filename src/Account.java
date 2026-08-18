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
