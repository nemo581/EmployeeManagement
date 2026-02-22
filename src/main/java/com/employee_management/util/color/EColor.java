package com.employee_management.util.color;

public enum EColor {
    RED("\u001b[31"),
    REST("\u001b[0m");
    private final String title;

    EColor(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
