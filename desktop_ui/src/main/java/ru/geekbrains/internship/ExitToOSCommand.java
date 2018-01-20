package ru.geekbrains.internship;

public class ExitToOSCommand implements Command {

    private final StartWindow mainApp;

    ExitToOSCommand(StartWindow mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}
