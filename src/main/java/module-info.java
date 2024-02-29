module com.planemanagement.planemanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.planemanagement.planemanagement to javafx.fxml;
    exports com.planemanagement.planemanagement;
    exports com.planemanagement.planemanagement.controlador;
    exports com.planemanagement.planemanagement.modelo;
    exports com.planemanagement.planemanagement.vista;
    opens com.planemanagement.planemanagement.controlador;

}