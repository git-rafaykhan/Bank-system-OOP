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
