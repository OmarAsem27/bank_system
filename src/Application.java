import java.util.List;
import java.util.Scanner;

public class Application {

    final int MAX_OPTIONS = 3;
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
            System.out.println("2. Print Customers");
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
                this.printCustomers();
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
                    // Range check for account types (must be 1, 2, or 3)
                    if (tempInp >= 1 && tempInp <= 3) {
                        inp = tempInp; // Valid choice; breaks the loop
                    } else {
                        System.out.println("Error: Please select a valid choice for account types (1, 2, or 3).");
                    }
                } else {
                    // Standard positive number check
                    if (tempInp >= 0) {
                        inp = tempInp; // Valid ID; breaks the loop
                    } else {
                        System.out.println("Error: The number must be greater than or equal to 0.");
                    }
                }
            } else {
                // User entered non-integer text (e.g., "abc")
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

}
