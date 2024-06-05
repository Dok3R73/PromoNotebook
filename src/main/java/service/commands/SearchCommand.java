package service.commands;

import service.NoteService;

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
