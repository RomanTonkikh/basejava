package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Position extends AbstractSection {
    private final String nameCompany;
    private final String url;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String title;
    private final String description;


    public Position(String nameCompany, String url, LocalDate startDate, LocalDate endDate, String title, String description) {
        Objects.requireNonNull(title, "title must not be null");
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(endDate, "endDate must not be null");
        this.nameCompany = nameCompany;
        this.url = url;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (!nameCompany.equals(position.nameCompany)) return false;
        if (!url.equals(position.url)) return false;
        if (!startDate.equals(position.startDate)) return false;
        if (!endDate.equals(position.endDate)) return false;
        if (!title.equals(position.title)) return false;
        return description.equals(position.description);
    }

    @Override
    public int hashCode() {
        int result = nameCompany.hashCode();
        result = 31 * result + url.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return nameCompany + " (" + url + ")" + "\n" +
                startDate + " - " + endDate + "  " + title + "\n" +
                description;
    }
}
