package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class CompanySection extends AbstractSection {
    private final List<Organization> listOrganization;

    public CompanySection(List<Organization> listOrganization) {
        Objects.requireNonNull(listOrganization, "listCompany must not be null");
        this.listOrganization = listOrganization;
    }

    public List<Organization> getListPosition() {
        return listOrganization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanySection that = (CompanySection) o;
        return listOrganization.equals(that.listOrganization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listOrganization);
    }

    @Override
    public String toString() {
        return listOrganization.toString();
    }
}
