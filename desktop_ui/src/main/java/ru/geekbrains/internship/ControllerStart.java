package ru.geekbrains.internship;

public class ControllerStart {

    private Command startApplicationCommand;
    private Command settingsCommand;
    private Command exitToOSCommand;

    public void setMainApp(StartWindow mainApp) {
        startApplicationCommand = new StartApplicationCommand(mainApp);
        settingsCommand = new SettingsCommand(mainApp);
        exitToOSCommand = new ExitToOSCommand(mainApp);
    }

    public void pressStartButton() {
        startApplicationCommand.execute();
    }

    public void pressSettingsButton() {
        settingsCommand.execute();
    }

    public void pressExitButton() {
        exitToOSCommand.execute();
    }
}
