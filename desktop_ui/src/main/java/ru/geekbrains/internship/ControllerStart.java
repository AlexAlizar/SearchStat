package ru.geekbrains.internship;

public class ControllerStart {

    private Command startApplicationCommand;
    private Command settingsCommand;

    public void setMainApp(StartWindow mainApp) {
        startApplicationCommand = new StartApplicationCommand(mainApp);
        settingsCommand = new SettingsCommand(mainApp);
    }

    public void pressStartButton() {
        startApplicationCommand.execute();
    }

    public void pressSettingsButton() {
        settingsCommand.execute();
    }
}
