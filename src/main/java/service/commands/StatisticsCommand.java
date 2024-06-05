package service.commands;

import service.NoteService;

public class StatisticsCommand implements Command {
    private final NoteService noteService;

    public StatisticsCommand(NoteService noteService) {
        this.noteService = noteService;
    }

    @Override
    public void execute() {
        noteService.statistics();
    }
}
