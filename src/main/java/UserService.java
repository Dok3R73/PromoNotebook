import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UserService {

    private final List<Note> notes = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public void run() {

        while (true) {
            System.out.println("Для записи новой заметки введите #write, для чтения уже записанных заметок введите #read");
            System.out.println("Для получения статистики введите #statistics");
            System.out.println("Для поиска заметок по дате введите #search");
            System.out.println("Для выхода введите #exit");
            String command = scanner.nextLine();

            if (command.equals("#exit")) {
                break;
            } else if (command.equals("#write")) {
                recordNote();
            } else if (command.equals("#read")) {
                readNotes();
            } else if (command.equals("#statistics")) {
                statistics();
            } else if (command.equals("#search")) {
                searchDate();
            }
        }
    }

    private void recordNote() {
        System.out.println("Введите вашу новую заметку:");
        recordNoteToFile(new Note(scanner.nextLine()));
    }

    private void readNotes() {
        if (notes.isEmpty()) {
            System.out.println("Список пуст");
        } else {
            for (Note note : notes) {
                System.out.println(note + "\n");
            }
        }
    }

    private void getFile() {
        File file = new File(System.getProperty("user.home") + "/Desktop/notes.txt");
        if (file.exists()) {
            try {
                Scanner fileScanner = new Scanner(file);
                while (fileScanner.hasNextLine()) {
                    String noteLine = fileScanner.nextLine();
                    int lastIndex = noteLine.lastIndexOf(",");
                    int lastIndex2 = noteLine.lastIndexOf("|");
                    if (lastIndex != -1 && lastIndex2 != -1) {
                        String noteText = noteLine.substring(0, lastIndex).trim();
                        String dateTimeString = noteLine.substring(lastIndex + 1, lastIndex2).trim();
                        LocalDateTime creationDateTime = LocalDateTime.parse(dateTimeString);
                        notes.add(new Note(noteText, creationDateTime));
                    }
                }
                fileScanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void recordNoteToFile(Note note) {
        notes.add(note);
        try {
            FileWriter fileWriter = new FileWriter(System.getProperty("user.home") + "/Desktop/notes.txt", true);
            fileWriter.write(note.getContent() + ", " + note.getCreationTime() + " | " + note.getLength() + "\n");
            fileWriter.close();
            System.out.println("Заметка успешно записана!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void statistics() {
        int sum = 0;
        for (Note note : notes) {
            sum += note.getLength();
        }
        System.out.println("Колличество заметок: " + notes.size());
        System.out.println("Общее колличество символов: " + sum);
        System.out.println("Самый продуктивный день: " + maxDay(notes) + '\n');
    }

    public LocalDate maxDay(List<Note> notes) {
        return notes.stream()
                .collect(Collectors.groupingBy(
                        note -> note.getCreationTime().toLocalDate(),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    private void searchDate() {
        System.out.println("Введите дату в формате ГГГГ-ММ-ДД");
        String dateString = scanner.nextLine();

        LocalDate searchDate = null;
        boolean validInput = true;

        while (validInput) {
            try {
                searchDate = LocalDate.parse(dateString);
                validInput = false;
            } catch (DateTimeParseException e) {
                System.out.println("Неправильный формат даты. Пожалуйста, введите дату в формате ГГГГ-ММ-ДД:");
                dateString = scanner.nextLine();
            }
        }

        LocalDate finalSearchDate = searchDate;
        List<Note> matchingNotes = notes.stream()
                .filter(note -> note.getCreationTime().toLocalDate().equals(finalSearchDate))
                .collect(Collectors.toList());

        if (matchingNotes.isEmpty()) {
            System.out.println("Заметки не найдены");
        } else {
            System.out.println("Найденные заметки:");
            for (Note note : matchingNotes) {
                System.out.println(note + "\n");
            }
        }
    }
}
