package ru.geekbrains.internship;

public class ExitToMainMenuCommand implements Command {

    private final StartWindow mainApp;

    ExitToMainMenuCommand(StartWindow mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public void execute() {
        mainApp.paint(mainApp.getStage());
    }
}
