package ru.geekbrains.internship;

public class CancelAuthorizationCommand implements Command {

    private final StartWindow mainApp;

    CancelAuthorizationCommand(StartWindow mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public void execute() {
        mainApp.paint(mainApp.getStage());
    }
}
