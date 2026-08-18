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
