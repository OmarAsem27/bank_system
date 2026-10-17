import java.util.ArrayList;
import java.util.List;

public class Bank {

    List<Customer> customers = new ArrayList<>();
    List<Account> accounts = new ArrayList<>();
    int nextCustomerId = 0;
    int nextAccountId = 0;

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

    private List<Account> findAccountsByCustomerId(int id) {
        return this.accounts
                .stream()
                .filter(acc -> acc.customer.id == id)
                .toList(); // Collects the results into a new List
    }

}
