module org.example.soleclipsado {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.soleclipsado to javafx.fxml;
    exports org.example.soleclipsado;
    exports org.example.soleclipsado.controllers;
    opens org.example.soleclipsado.controllers to javafx.fxml;
}