/**
 *
 */
module com.hippotech {
    requires javafx.controls;
    requires javafx.fxml;
    opens com.hippotech to javafx.fxml;
    exports com.hippotech;
}