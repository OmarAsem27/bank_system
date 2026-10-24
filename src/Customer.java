import java.util.ArrayList;
import java.util.List;

public class Customer {

    int id;
    String name;
    String status; // Active or Blocked
    List<Account> accounts;

    public Customer(int id, String name) throws Exception {
        this.validateName(name);
        // this.validateStatus(status);

        this.id = id;
        this.name = name;
        this.status = "Active";
        // this.status = status;
        this.accounts = new ArrayList<>();
    }

    private void validateName(String name) throws Exception {
        if (name != null && (name.length() < 3 || name.length() > 60 || !name.matches("^[a-zA-Z\\s]+$"))) {
            throw new Exception("The name must be at least 3 characters long and does not contain any numbers.");
        }
    }

    private void validateStatus(String status) throws Exception {
        if (!"Active".equals(status) && !"Blocked".equals(status)) {
            throw new Exception("The status must be active or blocked.");
        }
    }

    @Override
    public String toString() {
        return "Customer [id=" + id + ", name=" + name + ", status=" + status + ", accounts=" + accounts + "]";
    }

}
