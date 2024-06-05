package service;

import entity.Note;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class NoteService {
    private final FileManagement fileManagement;
    private final List<Note> notes;
    private final Scanner scanner;

    public NoteService() {
        this.fileManagement = new FileManagement();
        this.notes = fileManagement.getFile();
        this.scanner = new Scanner(System.in);
    }

    public void recordNote() {
        System.out.println("Введите вашу новую заметку:");
        Note note = new Note(scanner.nextLine());
        notes.add(note);
        fileManagement.recordNoteToFile(note);
    }

    public void readNotes() {
        if (notes.isEmpty()) {
            System.out.println("Список пуст");
        } else {
            for (Note note : notes) {
                System.out.println(note + "\n");
            }
        }
    }

    public void statistics() {
        int sum = 0;
        for (Note note : notes) {
            sum += note.getLength();
        }
        System.out.println("Колличество заметок: " + notes.size());
        System.out.println("Общее колличество символов: " + sum);
        System.out.println("Самый продуктивный день: " + maxDay(notes) + '\n');
    }

    public void searchDate() {
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
}
