package com.urise.webapp.model;

import java.util.Objects;

public class TextSection extends Section {
    private final String textField;

    public TextSection(String textField) {
        Objects.requireNonNull(textField, "textField must not be null");
        this.textField = textField;
    }

    public String getTextField() {
        return textField;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return textField.equals(that.textField);
    }

    @Override
    public int hashCode() {
        return Objects.hash(textField);
    }

    @Override
    public String toString() {
        return textField;
    }
}
