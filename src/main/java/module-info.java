module co.edu.uniquindio.poo {
    requires javafx.controls;
    requires lombok;
    requires javafx.fxml;

    opens co.edu.uniquindio.poo to javafx.fxml;
    exports co.edu.uniquindio.poo;
}
