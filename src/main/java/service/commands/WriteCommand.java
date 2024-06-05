package service.commands;

import service.NoteService;

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
