import java.util.Optional;

public class Customer {

    int id;
    String name;
    String status; // Active or Blocked
    Optional<Object> accounts;

    public Customer(int id, String name, String status, Optional<Object> accounts) throws Exception {
        this.validateName(name);
        this.validateStatus(status);

        this.id = id;
        this.name = name;
        this.status = status;
        this.accounts = accounts;
    }

    private void validateName(String name) throws Exception {
        if (name.length() < 3 || !name.matches("^[a-zA-Z\\s]+$")) {
            throw new Exception("The name must be at least 3 characters long and does not contain any numbers.");
        }
    }

    private void validateStatus(String status) throws Exception {
        if (status != "active" || status != "blocked") {
            throw new Exception("The status must be active or blocked.");
        }

    }
}
