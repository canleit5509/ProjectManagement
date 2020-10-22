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
        // Dialog title
        String NOTIFICATION_TITLE = "Thông báo";
        String WARNING_TITLE = "Warning";
        // Success
        String SUCCESS_ADD_PERSON = "Thêm thành công";
        String SUCCESS_ADD_PROJECT = "Thêm thành công";
        String SUCCESS_ADD_TASK = "Thêm thành công";
        String SUCCESS_UPDATE_PERSON = "Cập nhật thành công";
        String SUCCESS_UPDATE_PROJECT = "Cập nhật thành công";
        String SUCCESS_UPDATE_TASK = "Cập nhật thành công";
        String SUCCESS_DELETE_TASK = "Đã xóa";
        // Error
        String ERROR_DEADLINE_BEFORE_START_DATE = "Ngày deadline trước ngày bắt đầu!";
        String ERROR_FINISH_TIME_BEFORE_START_DATE = "Ngày hoàn thành trước ngày bắt đầu!";
        // To do
        String CHOOSE_A_PROJECT = "Vui lòng chọn dự án";
        String CHOOSE_A_PERSON = "Vui lòng chọn nhân sự";
        String CHOOSE_TASK_TITLE = "Vui lòng chọn tên công việc";
        String CHOOSE_START_DATE = "Vui lòng chọn ngày bắt đầu";
        String CHOOSE_DEADLINE = "Vui lòng chọn ngày deadline";
        String CHOOSE_PROCESSED = "Vui lòng nhập tiến độ công việc";
        String CHOOSE_A_TASK_TO_DELETE = "Vui lòng chọn công việc cần xóa";
        String CHOOSE_A_TASK_TO_UPDATE = "Vui lòng chọn công việc cần chỉnh sửa";
        // Confirm
        String CONFIRM_DELETE_TASK = "Bạn có chắc chắn muốn xóa công việc này?";

    }
    interface WindowTitleConstant {
        String ADD_TASK_TITLE = "Thêm công việc";
        String ADD_PERSON_TITLE = "Thêm nhân sự";
        String ADD_PROJECT_TITLE = "Thêm dự án";
        String UPDATE_PERSON_TITLE = "Cập nhật nhân sự";
        String UPDATE_TASK_TITLE = "Chỉnh sủa công việc";
        String DELETE_TASK_TITLE = "Xóa công việc";
        String PERSON_MANAGEMENT_TITLE = "Quản lý nhân sự";
        String PROJECT_MANAGEMENT_TITLE = "Quản lý dự án";
    }


    interface COLOR {
        String NAVY_BLUE = "#8896DE";
        String WHITE = "#FFFFFF";
        String SOFT_GREEN = "#6bfc5b";
        String DARK_GREEN = "#0d8500";
        String RED = "#d62a1e";
        String ORANGE = "#ffe987";
        String YELLOW = "#eaff00";
    }


    interface TIMELINEPANESPECS{
        double GRIDPANETOTALWIDTH = 9100;
        double OFFSETAUTOSCROLLTIMELINE = 5.4;
    }
}
