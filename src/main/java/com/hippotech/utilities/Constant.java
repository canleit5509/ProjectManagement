package com.hippotech.utilities;

public interface Constant {

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


    interface Color {
        String NAVY_BLUE = "#8896DE";
        String WHITE = "#FFFFFF";
        String SOFT_GREEN = "#6bfc5b";
        String DARK_GREEN = "#0d8500";
        String RED = "#d62a1e";
        String ORANGE = "#ffe987";
        String YELLOW = "#eaff00";
    }


    interface TimeLinePaneSpecs {
        double GRIDPANE_TOTAL_WIDTH = 9100;
        double GRID_PANE_WIDTH = 960;
        int NUMCOLS = 9;
    }

    interface FXMLPage {
        String ADD_TASK_VIEW = "/com/hippotech/AddTaskView.fxml";
        String UPDATE_TASK_VIEW = "/com/hippotech/UpdateTaskView.fxml";
        String PERSON_MANAGEMENT = "/com/hippotech/PersonManagement.fxml";
        String PROJECT_MANAGEMENT = "/com/hippotech/ProjectManagement.fxml";
        String DAY_ROW = "/com/hippotech/components/DayRow.fxml";
        String TABLE_TITLE = "/com/hippotech/components/TableTitle.fxml";
        String WEEK_TITLE = "/com/hippotech/components/WeekTitle.fxml";
        String ADD_PROJECT = "/com/hippotech/AddProject.fxml";
        String ADD_PERSON = "/com/hippotech/AddPerson.fxml";
        String UPDATE_PERSON = "/com/hippotech/UpdatePerson.fxml";
        String UPDATE_PROJECT = "/com/hippotech/UpdateProject.fxml";
    }
}
