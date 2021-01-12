package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Company extends Section {
    private final String nameCompany;
    private final String url;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String title;
    private final String description;


    public Company(String nameCompany, String url, LocalDate startDate, LocalDate endDate, String title, String description) {
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

        Company company = (Company) o;

        if (!nameCompany.equals(company.nameCompany)) return false;
        if (!url.equals(company.url)) return false;
        if (!startDate.equals(company.startDate)) return false;
        if (!endDate.equals(company.endDate)) return false;
        if (!title.equals(company.title)) return false;
        return description.equals(company.description);
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
