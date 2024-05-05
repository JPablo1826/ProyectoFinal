module co.edu.uniquindio.poo {
    requires javafx.controls;
    requires lombok;
    requires javafx.fxml;
    requires transitive jakarta.mail;

    opens co.edu.uniquindio.poo to javafx.fxml;
    exports co.edu.uniquindio.poo;
}
