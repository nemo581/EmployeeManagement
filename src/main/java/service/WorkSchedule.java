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
        this.date_last = LocalDate.of(date.getYear(), 12, 31);
    }

    public void getWorkSchedule(EmployeeData data) {
        for (Employee emp : data.getEmployeeList()) {
            workScheduleService(data, emp, emp.getShift());
        }
    }

/*
        >>>> T-E-S-T  V-E-R-S-I-O-N <<<<
*/
    private void workScheduleService(EmployeeData employeeData, Employee emp, int sh) {
        int wd = sh - 1;
        int wd_index = 0;
        int vol;
        boolean is_first = true;
        LocalDate tmp = date_first;

        while (!tmp.equals(date_last)) {
            vol = tmp.getDayOfWeek().getValue();
            if (is_first) {
                if (vol == 6 && weekend_days[wd][wd_index] == vol) {
                    employeeData.addWorkSchedule(tmp, 1, emp.getId());
                    wd_index++;
                } else if (vol == 7) {
                    if (weekend_days[wd][0] == vol) {
                        employeeData.addWorkSchedule(tmp, 1, emp.getId());
                        wd_index++;
                    } else if (weekend_days[wd][wd_index + 1] == vol) {
                        employeeData.addWorkSchedule(tmp, 1, emp.getId());
                        wd++;
                    } else {
                        employeeData.addWorkSchedule(tmp, 0, emp.getId());
                        wd_index++;
                    }
                } else if (vol == 1 && weekend_days[wd][1] == vol) {
                    employeeData.addWorkSchedule(tmp, 1, emp.getId());
                    wd++;
                } else {
                    employeeData.addWorkSchedule(tmp, 0, emp.getId());
                    if (vol == 1) wd++;
                }

                is_first = false;
                if (wd == 3) {
                    wd = 0;
                    wd_index = 0;
                }
            } else {
                if (weekend_days[wd][wd_index] == vol) {
                    employeeData.addWorkSchedule(tmp, 1, emp.getId());
                    wd_index++;
                } else {
                    employeeData.addWorkSchedule(tmp, 0, emp.getId());
                }
                if (wd_index == 2) {
                    wd++;
                    if (wd == 3) wd = 0;
                    wd_index = 0;
                }
            }
            tmp = tmp.plusDays(1);
        }
    }
}