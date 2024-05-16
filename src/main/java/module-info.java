module mat {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires lombok;
    requires java.logging;
    requires java.sql;

    opens mat to javafx.fxml;
    exports mat;
    exports mat.model;
    exports mat.util;
    opens mat.util to javafx.fxml;
}