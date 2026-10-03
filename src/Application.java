import java.util.Scanner;

public class Application {

    final int MAX_OPTIONS = 2;
    Scanner sc;

    Application() {
        this.sc = new Scanner(System.in);
        System.out.println(
                "\nWelcome to the Library management system.\n");
    }

    public void collectUserInput() {
        boolean appRunning = true;

        while (appRunning) {
            System.out.println("please, enter your wanted operation number between the following choices:");
            System.out.println("1. Open Account");
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
        System.out.println("Opening acount...");
    }
}
