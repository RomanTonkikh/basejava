package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Position extends AbstractSection {
    private final Link homePage;
    private final List<Period> periods = new ArrayList<>();

    public Position(String name, String url, LocalDate startDate, LocalDate endDate, String title, String description) {
        this.homePage = new Link(name, url);
        this.periods.add(new Period(startDate, endDate, title, description));
    }

    public Link getHomePage() {
        return homePage;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (!homePage.equals(position.homePage)) return false;
        return periods.equals(position.periods);
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + periods.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return homePage + "" + periods;
    }
}
