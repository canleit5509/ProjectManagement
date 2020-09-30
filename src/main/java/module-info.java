/**
 *
 */
module com.hippotech {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    opens com.hippotech to javafx.fxml;
    exports com.hippotech;
}