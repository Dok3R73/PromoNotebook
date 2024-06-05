package service.commands;

import service.NoteService;

/**
 * Класс WriteCommand представляет команду поиска заметок по дате.
 */
public class SearchCommand implements Command {
    private final NoteService noteService;

    public SearchCommand(NoteService noteService) {
        this.noteService = noteService;
    }

    @Override
    public void execute() {
        noteService.searchDate();
    }
}
