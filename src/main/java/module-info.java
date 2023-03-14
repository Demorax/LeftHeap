module com.example.semesc {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.semesc to javafx.fxml;
    exports com.example.semesc;
}