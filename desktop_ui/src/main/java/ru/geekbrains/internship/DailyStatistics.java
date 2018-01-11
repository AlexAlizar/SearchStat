package ru.geekbrains.internship;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DailyStatistics implements Statistics {
    private final StringProperty date;
    private final IntegerProperty quantity;

    DailyStatistics(String date, int quantity) {
        this.date = new SimpleStringProperty(date);
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    public StringProperty dateProperty() {
        return date;
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }
}
