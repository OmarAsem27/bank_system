import java.time.LocalDateTime;

public class Transaction {

    int id;
    float amount;
    LocalDateTime createdAt;
    String status; // Created, Pending, and Succeeded
    Account account;

    public Transaction(int id, float amount, LocalDateTime createdAt, String status, Account account) throws Exception {
        this.validateAccount(account);
        this.validateAmount(amount);
        this.validateStatus(status);
        this.validateCreatedAt(createdAt);

        this.id = id;
        this.amount = amount;
        this.createdAt = createdAt;
        this.status = status;
        this.account = account;
    }

    private void validateAmount(float amount) throws Exception {
        if (amount <= 0) {
            throw new Exception("The amount is invalid.");
        }
    }

    private void validateAccount(Account account) throws Exception {
        if (account == null) {
            throw new Exception("The transaction has to be assigned to valid account.");
        }
    }

    private void validateStatus(String status) throws Exception {
        if (!"Pending".equals(status) && !"Success".equals(status) && !"Created".equals(status)) {
            throw new Exception("The status must be Created or Pending or Success.");
        }
    }

    private void validateCreatedAt(LocalDateTime createdAt) throws Exception {
        LocalDateTime now = LocalDateTime.now();
        if (!now.isAfter(createdAt)) {
            throw new Exception("The time is invalid.");
        }
    }

    public void markSucceeded() throws Exception {
        if (this.status.equals("Succeeded")) {
            throw new Exception("This member is already inactive.");
        }
        this.status = "Succeeded";
    }

}
