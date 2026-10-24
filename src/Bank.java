import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Bank {

    List<Customer> customers = new ArrayList<>();
    List<Account> accounts = new ArrayList<>();
    List<Transaction> transactions = new ArrayList<>();
    int nextCustomerId = 0;
    int nextAccountId = 0;
    int nextTransactionId = 0;

    public Account createAccount(int id, String name, String accountType) throws Exception {
        Customer customer = this.findCustomerById(id);
        Account newAccount = null;
        Customer newCustomer;

        if (customer == null) {
            try {
                newCustomer = new Customer(nextCustomerId, name);
            } catch (Exception e) {
                throw new Exception("\nFailed to create a new customer: " + e.getMessage());
            }

            try {
                newAccount = new Account(nextAccountId, 0, "Active", accountType, newCustomer);
                newCustomer.accounts.add(newAccount);
                this.nextAccountId++;
                this.nextCustomerId++;
                this.accounts.add(newAccount);
                this.customers.add(newCustomer);

            } catch (Exception e) {
                throw new Exception("\nFailed to create a new account: " + e.getMessage());
            }
        } else {
            Account foundAcc = customer.accounts.stream()
                    .filter(targetAccount -> targetAccount.accountType.equals(accountType))
                    .findFirst().orElse(null);

            if (foundAcc != null) {
                throw new Exception("This customer has an account with the same type.");
            }
            newAccount = new Account(nextAccountId, 0, "Active", accountType, customer);
            customer.accounts.add(newAccount);
            this.accounts.add(newAccount);
            this.nextAccountId++;
        }
        return newAccount;
    }

    private Customer findCustomerById(int id) {
        return this.customers
                .stream()
                .filter(c -> c.id == id)
                .findFirst()
                .orElse(null);
    }

    private Account findAccountById(int id) {
        return this.accounts
                .stream()
                .filter(acc -> acc.id == id)
                .findFirst()
                .orElse(null);
    }

    public void depositMoney(int id, float amount) throws Exception {
        System.out.println("acc id: " + id + " amount: " + amount);
        Account account = this.findAccountById(id);
        if (account == null) {
            throw new Exception("Account not found.");
        }

        LocalDateTime now = LocalDateTime.now();

        try {
            Transaction transaction = new Transaction(this.nextTransactionId, amount, now, "Created", account);
            account.deposit(amount);
            transaction.markSucceeded();
            this.transactions.add(transaction);
            this.nextTransactionId++;
        } catch (Exception e) {
            throw new Exception("\nFailed to create a new Transaction: " + e.getMessage());
        }

    }

}
