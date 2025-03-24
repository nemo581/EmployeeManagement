package model;

public enum Department {
    TransportDepartment ("Транспортный Отдел");

    String title;
    Department(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
