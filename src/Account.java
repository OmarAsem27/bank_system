public class Account {

    int id;
    float balance;
    String status; // Active or Blocked
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
}
