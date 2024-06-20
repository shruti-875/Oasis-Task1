module org.example.onlinereservation {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires mysql.connector.java;

    opens org.example.onlinereservation to javafx.fxml;
    exports org.example.onlinereservation;
}