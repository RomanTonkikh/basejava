package com.urise.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private List<String> textList;

    public ListSection() {
    }

    public ListSection(String... textList) {
        this(Arrays.asList(textList));

    }

    public ListSection(List<String> textList) {
        Objects.requireNonNull(textList, "textList must not be null");
        this.textList = textList;
    }

    public List<String> getTextList() {
        return textList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return textList.equals(that.textList);
    }

    @Override
    public int hashCode() {
        return textList.hashCode();
    }

    @Override
    public String toString() {
        return textList.toString();
    }
}
