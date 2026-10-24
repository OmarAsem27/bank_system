import java.util.ArrayList;
import java.util.List;

public class Account {

    int id;
    float balance;
    String status; // Active or Blocked

    @Override
    public String toString() {
        return "Account [id=" + id + ", balance=" + balance + ", status=" + status + ", accountType=" + accountType
                + "]";
    }

    String accountType;
    Customer customer;

    public Account(int id, float balance, String status, String accountType, Customer customer) throws Exception {
        this.validateType(accountType);
        this.id = id;
        this.balance = balance;
        this.status = status;
        this.accountType = accountType;
        this.customer = customer;
    }

    private void validateType(String type) throws Exception {
        if (!"Current".equals(type) && !"Savings".equals(type) && !"Business".equals(type)) {
            throw new Exception("The type must be Current or Savings or Business.");
        }
    }

    public void deposit(float amount) throws Exception {
        if (this.status.equals("Blocked")) {
            throw new Exception("The account is blocked.");
        }
        if (amount <= 0) {
            throw new Exception("The amount is invalid.");
        }
        this.balance += amount;
    }
}
