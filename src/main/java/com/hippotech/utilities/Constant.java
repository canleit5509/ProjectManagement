package com.hippotech.utilities;

public interface Constant {
    interface PrimaryConstant {
        String PROJECT_NAME = "Project";
        String TASK_NAME = "Task";
        String EMPLOYEE = "Employee";
        String DATE_START = "Start";
        String DEADLINE = "Deadline";
        String FINISH_DATE = "Finish";
        String EXPECTED_TIME = "Expected";
        String FINISH_TIME = "Finish Time";
        String PROCESS = "Process%";
    }

    interface DialogConstant {
        String NOTIFICATION_TITLE = "Thông báo";
        String SUCCESS_ADD_PERSON = "Thêm thành công";

    }

    interface ManagerPerson{
        String NOTIFICATION_TITLE = "Thông báo";
        String NOTIFICATION_CONTENT = "Vui lòng chọn nhân viên";
        String UPDATE_PERSON_WINDOW_TITLE = "Cập nhật nhân sự";
    }

    interface ManagerProject{

        String NOTIFICATION_TITLE = "Thêm dự án";
//        String
    }

    interface COLOR {
        String WHITE = "#FFFFFF";
        String SOFT_GREEN = "#6bfc5b";
        String DARK_GREEN = "#0d8500";
        String RED = "#d62a1e";
        String ORANGE = "#ffe987";
        String YELLOW = "#eaff00";
    }
}
