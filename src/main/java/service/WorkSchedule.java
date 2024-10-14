package service;

import model.Employee;
import repository.EmployeeData;

import java.time.LocalDate;

public class WorkSchedule {
    private final int[][] weekend_days = new int[][] {{6, 7}, {6, 1}, {7, 1}};
    private final LocalDate date_first;
    private final LocalDate date_last;


    public WorkSchedule(LocalDate date) {
        this.date_first = date.withDayOfMonth(1);
        this.date_last = date.withDayOfMonth(date.getMonth().length(date.isLeapYear()));
    }

    public void getWorkSchedule(EmployeeData data) {
        for (Employee emp : data.getEmployeeList()) {
            workScheduleService(emp, emp.getShift());
        }
    }

    private void workScheduleService(Employee emp, int sh) {
        int wd = sh - 1;
        int wd_index = 0;
        int vol;
        boolean is_first = true;

        for (int i = date_first.getDayOfMonth(); i <= date_last.getDayOfMonth(); i++) {
            vol = LocalDate.of(date_first.getYear(), date_first.getMonth(), i).getDayOfWeek().getValue();
            if (is_first) {
                if (vol == 6 && weekend_days[wd][wd_index] == vol) {
                    emp.setTestDate(LocalDate.of(date_first.getYear(), date_first.getMonth(), i), 1);
                    wd_index++;
                } else if (vol == 7) {
                    if (weekend_days[wd][0] == vol) {
                        emp.setTestDate(LocalDate.of(date_first.getYear(), date_first.getMonth(), i), 1);
                        wd_index++;
                    } else if (weekend_days[wd][wd_index + 1] == vol) {
                        emp.setTestDate(LocalDate.of(date_first.getYear(), date_first.getMonth(), i), 1);
                        wd++;
                    } else {
                        emp.setTestDate(LocalDate.of(date_first.getYear(), date_first.getMonth(), i), 0);
                        wd_index++;
                    }
                } else if (vol == 1 && weekend_days[wd][1] == vol) {
                    emp.setTestDate(LocalDate.of(date_first.getYear(), date_first.getMonth(), i), 1);
                    wd++;
                } else {
                    emp.setTestDate(LocalDate.of(date_first.getYear(), date_first.getMonth(), i), 0);
                    if (vol == 1) wd++;
                }
                is_first = false;
                if (wd == 3) {
                    wd = 0;
                    wd_index = 0;
                }
            } else {
                if (weekend_days[wd][wd_index] == vol) {
                    emp.setTestDate(LocalDate.of(date_first.getYear(), date_first.getMonth(), i), 1);
                    wd_index++;
                } else {
                    emp.setTestDate(LocalDate.of(date_first.getYear(), date_first.getMonth(), i), 0);
                }
                if (wd_index == 2) {
                    wd++;
                    if (wd == 3) wd = 0;
                    wd_index = 0;
                }
            }
        }
    }
}