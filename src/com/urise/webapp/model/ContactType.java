package com.urise.webapp.model;

public enum ContactType {
    PHONE("Телефон"),
    SKYPE("Скайп") {
        @Override
        public String toHtml0(String value) {
            return getTitle() + ": " + "<a href=skype:>" + value + "</a>";
        }
    },
    EMAIL("Электронная почта") {
        @Override
        public String toHtml0(String value) {
            return "<a href=mailto:>" + value + "</a>";
        }
    },
    LINKEDIN("Аккаунт в LinkedIn"),
    GITHUB("Аккаунт в GitHub"),
    STACKOVERFLOW("Аккаунт в Stackoverflow"),
    HOMEPAGE("Домашняя страница");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    protected String toHtml0(String value) {
        return title + ": " + value;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : toHtml0(value);
    }
}
