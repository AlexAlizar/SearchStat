package ru.geekbrains.internship;

import javafx.scene.control.Alert;

public class AboutCommand implements Command {

    private final StartWindow mainApp;

    AboutCommand(StartWindow mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public void execute() {
        new AlertHandler(Alert.AlertType.INFORMATION, "О программе", "SearchStat",
                '\u00A9' + " 2018 HardDevTeam");
    }
}
