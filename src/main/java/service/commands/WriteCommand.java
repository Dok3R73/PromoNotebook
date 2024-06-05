package service.commands;

import service.NoteService;

/**
 * Класс WriteCommand представляет команду для записи заметки.
 */
public class WriteCommand implements Command {
    private final NoteService noteService;

    public WriteCommand(NoteService noteService) {
        this.noteService = noteService;
    }

    @Override
    public void execute() {
        noteService.recordNote();
    }
}
