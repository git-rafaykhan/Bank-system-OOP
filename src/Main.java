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
