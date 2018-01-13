package ru.geekbrains.internship;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StringList implements Statistics {
    private final StringProperty id;
    private final StringProperty name;

    StringList(String id, String name) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
    }

    public StringProperty idProperty() {
        return id;
    }

    public StringProperty nameProperty() {
        return name;
    }

}
