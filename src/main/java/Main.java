import service.CommandService;

/**
 * Класс Main является точкой входа в программу.
 */
public class Main {

    public static void main(String[] args) {
    CommandService commandService = new CommandService();
    commandService.run();
    }
}
