/**
 *
 */
module com.hippotech {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;
    opens com.hippotech to javafx.fxml;
    opens com.hippotech.model to javafx.base;
    opens com.hippotech.controller to javafx.fxml;
    opens com.hippotech.controller.components to javafx.fxml;
    exports com.hippotech;
}