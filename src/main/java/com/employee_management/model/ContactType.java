package com.employee_management.model;

public enum ContactType {
    PERSONAL("personal"),
    WORK("work");

    private final String title;
    ContactType(String title) {
        this.title = title;
    }

    public String getValue() {
        return title;
    }

    public static ContactType fromTitle(String title) {
        if (title == null) return null;
        for (ContactType t : values()) {
            if (t.title.equalsIgnoreCase(title)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Unknown email type: " + title);
    }
    @Override
    public String toString() {
        return title;
    }
}
