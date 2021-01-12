package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class CompanySection extends Section {
    private final List<Company> listCompany;

    public CompanySection(List<Company> listCompany) {
        Objects.requireNonNull(listCompany, "listCompany must not be null");
        this.listCompany = listCompany;
    }

    public List<Company> getListCompany() {
        return listCompany;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanySection that = (CompanySection) o;
        return listCompany.equals(that.listCompany);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listCompany);
    }

    @Override
    public String toString() {
        return listCompany.toString();
    }
}
