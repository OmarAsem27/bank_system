import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Application {

    final int MAX_OPTIONS = 6;
    Scanner sc;
    Bank bank;

    Application() {
        this.sc = new Scanner(System.in);
        this.bank = new Bank();
        System.out.println(
                "\nWelcome to the Bank system.\n");
    }

    public void collectUserInput() {
        boolean appRunning = true;

        while (appRunning) {
            System.out.println("please, enter your wanted operation number between the following choices:");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit Money");
            System.out.println("3. Print Customers");
            System.out.println("4. Print Transactions");
            System.out.println("5. Print Balance");
            System.out.println(MAX_OPTIONS + ". Exit");

            if (sc.hasNextInt()) {
                int inp = sc.nextInt();
                sc.nextLine();

                if (inp >= 1 && inp <= MAX_OPTIONS) {
                    if (inp == MAX_OPTIONS) {
                        appRunning = false;
                    }

                    this.processUserRequest(inp);
                    continue;
                }
            } else {
                sc.nextLine();
            }
            System.out.printf("Invalid choice! Please enter a number between 1 and %d.%n%n", MAX_OPTIONS);
        }
    }

    private void processUserRequest(int input) {
        switch (input) {
            case 1:
                this.openAccount();
                break;
            case 2:
                this.depositMoney();
                break;
            case 3:
                this.printCustomers();
                break;
            case 4:
                this.printTransactions();
                break;
            case 5:
                this.printBalance();
                break;
            case MAX_OPTIONS:
                this.exitApp();
                break;
            default:
                break;
        }
    }

    private void exitApp() {
        System.out.println("Thank you for using our application.");
        System.exit(0);
    }

    private void openAccount() {
        int customerId = getValidInputId("customer", false);
        System.out.print("Customer name: ");
        String name = sc.nextLine();

        int accType = getValidInputId("account", true);
        String mappedType = mapAccountType(accType);

        try {
            Account acc = this.bank.createAccount(customerId, name, mappedType);
            System.out.printf(
                    "Account opened successfully with type: %s and for customer named: %s and customer id: %d.%n",
                    acc.accountType,
                    acc.customer.name,
                    acc.customer.id);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private int getValidInputId(String inputName, boolean isAccountType) {
        int inp = -1;

        System.out.printf("Enter the ID of the %s.%n", inputName);
        if (isAccountType) {
            System.out.println("1. Savings Account.");
            System.out.println("2. Current Account.");
            System.out.println("3. Business Account.");
        }

        while (inp == -1) {
            if (this.sc.hasNextInt()) {
                int tempInp = this.sc.nextInt();
                this.sc.nextLine(); // Clear the newline character left in the buffer

                if (isAccountType) {
                    if (tempInp >= 1 && tempInp <= 3) {
                        inp = tempInp;
                    } else {
                        System.out.println("Error: Please select a valid choice for account types (1, 2, or 3).");
                    }
                } else {
                    if (tempInp >= 0) {
                        inp = tempInp;
                    } else {
                        System.out.println("Error: The number must be greater than or equal to 0.");
                    }
                }
            } else {
                System.out.println("Invalid input, please enter a valid whole number.");
                this.sc.nextLine(); // Clear the invalid input from the buffer
            }
        }

        return inp;
    }

    private String mapAccountType(int type) {
        switch (type) {
            case 1:
                return "Savings";
            case 2:
                return "Current";
            case 3:
                return "Business";
            default:
                return "";
        }
    }

    private void printCustomers() {
        List<Customer> customers = this.bank.customers;

        if (customers.size() > 0) {
            for (int i = 0; i < customers.size(); i++) {

                System.out.printf(
                        "Customer Number %d of %d: \nID is %d, name is %s, status is %s, accounts is %s%n",
                        i + 1,
                        customers.size(),
                        customers.get(i).id,
                        customers.get(i).name,
                        customers.get(i).status,
                        customers.get(i).accounts);
            }
        } else {
            System.out.println("no customers were found.\n");
        }
        this.collectUserInput();
    }

    private void depositMoney() {
        int accountId = getValidInputId("Account", false);
        System.out.println("Enter the amount to be deposited:");
        boolean isValid = false;
        float amnt = -1;

        while (!isValid) {
            if (this.sc.hasNextFloat()) {
                amnt = this.sc.nextFloat();
                this.sc.nextLine(); // Clear the buffer line

                if (amnt >= 0) {
                    isValid = true;
                } else {
                    System.out.println("Failed. Please enter a valid non-negative amount:");
                }
            } else {
                System.out.println("Invalid input. Please enter a number:");
                this.sc.nextLine();
            }
        }

        try {
            this.bank.depositMoney(accountId, amnt);
            System.out.println("Transaction done successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void printTransactions() {
        List<Transaction> transactions = this.bank.transactions;

        if (transactions.size() > 0) {
            for (int i = 0; i < transactions.size(); i++) {

                System.out.printf(
                        "Transaction Number %d of %d: \nID is %d, amount is %s, createdAt is %s, status is %s, account is %s%n",
                        i + 1,
                        transactions.size(),
                        transactions.get(i).id,
                        transactions.get(i).amount,
                        transactions.get(i).createdAt,
                        transactions.get(i).status,
                        transactions.get(i).account);
            }
        } else {
            System.out.println("no transactions were found.\n");
        }
        this.collectUserInput();
    }

    private void printBalance() {
        System.out.println(this.bank.accounts.getFirst().toString());
    }

}
