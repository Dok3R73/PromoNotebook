package service;

import service.commands.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Класс CommandService представляет собой сервис, который обрабатывает команды пользователя и взаимодействует с NoteService.
 */
public class CommandService {
    private final Scanner scanner = new Scanner(System.in);
    private final NoteService noteService;
    private final Map<String, Command> commands;

    public CommandService() {
        this.noteService = new NoteService();
        this.commands = new HashMap<>();
        this.commands.put("#write", new WriteCommand(noteService));
        this.commands.put("#read", new ReadCommand(noteService));
        this.commands.put("#statistics", new StatisticsCommand(noteService));
        this.commands.put("#search", new SearchCommand(noteService));
    }

    /**
     * Запускает CommandService, позволяя пользователю вводить команды и выполнять их.
     */
    public void run() {
        while (true) {
            System.out.println("Для записи новой заметки введите #write, для чтения уже записанных заметок введите #read");
            System.out.println("Для получения статистики введите #statistics");
            System.out.println("Для поиска заметок по дате введите #search");
            System.out.println("Для выхода введите #exit");
            String commandKey = scanner.nextLine();

            if (commandKey.equals("#exit")) {
                break;
            } else {
                Command command = commands.get(commandKey);
                if (command != null) {
                    command.execute();
                }
            }
        }
    }
}
