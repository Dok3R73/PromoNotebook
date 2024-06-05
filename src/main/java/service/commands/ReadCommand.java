package service.commands;

import service.NoteService;

/**
 * Класс WriteCommand представляет команду для чтения записанных заметок.
 */
public class ReadCommand implements Command {
    private final NoteService noteService;

    public ReadCommand(NoteService noteService) {
        this.noteService = noteService;
    }

    @Override
    public void execute() {
        noteService.readNotes();
    }
}
