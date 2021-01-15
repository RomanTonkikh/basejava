package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class Company extends AbstractSection {
    private final List<Position> listPosition;

    public Company(List<Position> listPosition) {
        Objects.requireNonNull(listPosition, "listCompany must not be null");
        this.listPosition = listPosition;
    }

    public List<Position> getListPosition() {
        return listPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company that = (Company) o;
        return listPosition.equals(that.listPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listPosition);
    }

    @Override
    public String toString() {
        return listPosition.toString();
    }
}
