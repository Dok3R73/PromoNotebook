import service.UserService;

/**
 * Класс Main является точкой входа в программу.
 */
public class Main {

    public static void main(String[] args) {
    UserService userService = new UserService();
    userService.run();
    }
}
