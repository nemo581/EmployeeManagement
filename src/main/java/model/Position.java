package model;

public enum Position {
    HEAD_OF_THE_TRANSPORT_DEPARTMENT ("Руководитель ТРО"),
    GROUP_MANAGER ("Руководитель группы ТРО"),
    SENIOR_MECHANIC ("Руководитель группы ТО"),
    BUSINESS_COACH ("Бизнес тренер"),
    SENIOR_LOGISTICS ("Старший логист"),
    LOGISTICIAN ("Логист"),
    MECHANIC ("Механик"),
    DRIVER ("Водитель-экспедитор"),
    LOADER ("Грузчик");

    private final String title;
    Position(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
}
