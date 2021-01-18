package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Organization extends AbstractSection {
    private final Link homePage;
    private final List<Position> positions = new ArrayList<>();

    public Organization(String name, String url, LocalDate startDate, LocalDate endDate, String title, String description) {
        this.homePage = new Link(name, url);
        this.positions.add(new Position(startDate, endDate, title, description));
    }

    public Link getHomePage() {
        return homePage;
    }

    public List<Position> getPeriods() {
        return positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization organization = (Organization) o;

        if (!homePage.equals(organization.homePage)) return false;
        return positions.equals(organization.positions);
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + positions.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return homePage + "" + positions;
    }
}
