package service;

import entity.Note;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Класс NoteService представляет сервис для работы с заметками.
 */
public class NoteService {
    private final FileManagement fileManagement;
    private final List<Note> notes;
    private final Scanner scanner;

    public NoteService() {
        this.fileManagement = new FileManagement();
        this.notes = fileManagement.getFile();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Записывает новую заметку.
     */
    public void recordNote() {
        System.out.println("Введите вашу новую заметку:");
        Note note = new Note(scanner.nextLine());
        notes.add(note);
        fileManagement.recordNoteToFile(note);
    }

    /**
     * Выводит все записанные заметки.
     */
    public void readNotes() {
        if (notes.isEmpty()) {
            System.out.println("Список пуст");
        } else {
            for (Note note : notes) {
                System.out.println(note + "\n");
            }
        }
    }

    /**
     * Выводит общую статистику о заметках.
     */
    public void statistics() {
        int sum = 0;
        for (Note note : notes) {
            sum += note.getLength();
        }
        System.out.println("Колличество заметок: " + notes.size());
        System.out.println("Общее колличество символов: " + sum);
        System.out.println("Самый продуктивный день: " + maxDay(notes) + '\n');
    }

    /**
     * Выполняет поиск заметок по дате введеной пользователем.
     */
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

    /**
     * Возвращает дату, в которую было создано наибольшее количество заметок.
     *
     * @param notes список заметок
     * @return дата с наибольшим количеством заметок
     */
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
