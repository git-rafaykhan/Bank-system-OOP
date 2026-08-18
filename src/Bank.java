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
