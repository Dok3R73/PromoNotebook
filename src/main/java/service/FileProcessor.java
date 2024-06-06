package service;

import entity.Note;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Класс FileManagement проводит оперции с файлом.
 */
public class FileProcessor {

    /**
     * Получает список заметок из файла.
     *
     * @return список заметок
     */
    public List<Note> getFile() {
        File file = new File("src/main/resources/notes.txt");
        List<Note> notes = new ArrayList<>();
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
        return notes;
    }

    /**
     * Записывает заметку в файл.
     *
     * @param note заметка для записи
     */
    public void recordNoteToFile(Note note) {
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/notes.txt", true);
            fileWriter.write(note.getContent() + ", " + note.getCreationTime() + " | " + note.getLength() + "\n");
            fileWriter.close();
            System.out.println("Заметка успешно записана!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
