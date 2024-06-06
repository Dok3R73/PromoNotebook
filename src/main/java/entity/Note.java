package entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Класс Note представляет заметку.
 */
public class Note {

    private final String content;
    private final LocalDateTime creationTime;
    private final int length;

    public Note(String content) {
        this.content = content;
        this.creationTime = LocalDateTime.now();
        this.length = content.length();
    }

    public Note(String content, LocalDateTime creationDateTime) {
        this.content = content;
        this.creationTime = creationDateTime;
        this.length = content.length();
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm yyyy-MM-dd");
        String formattedCreationTime = creationTime.format(formatter);
        return "Заметка: " + content + '\n' +
                "Время создания: " + formattedCreationTime + '\n' +
                "Колличество символов: " + length;
    }
}
