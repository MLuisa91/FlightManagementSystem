module com.donoso.easyflight {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires org.apache.httpcomponents.client5.httpclient5;  // <-- added this line
    requires org.apache.httpcomponents.core5.httpcore5;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires static lombok;

    opens com.donoso.easyflight to javafx.fxml;
    exports com.donoso.easyflight;
    exports com.donoso.easyflight.controlador;
    exports com.donoso.easyflight.pojos;
    exports com.donoso.easyflight.vista;
    opens com.donoso.easyflight.controlador;
    exports com.donoso.easyflight.crud;
    opens com.donoso.easyflight.crud;
    exports com.donoso.easyflight.contexto;
    opens com.donoso.easyflight.contexto;
    exports com.donoso.easyflight.responses;
    opens com.donoso.easyflight.responses;

}