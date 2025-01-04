module com.example.cfuanalysis {
    requires javafx.controls;
    requires javafx.fxml;
    requires opencv;
    requires java.desktop;

    opens com.example.cfuanalysis to javafx.fxml;
    exports com.example.cfuanalysis;
    exports learning;
    opens learning to javafx.fxml;
    exports com.example.archived;
    opens com.example.archived to javafx.fxml;
}