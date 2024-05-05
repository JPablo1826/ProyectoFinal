open module co.edu.uniquindio.poo {
    requires javafx.controls;
    requires transitive javafx.graphics;
    requires static lombok;
    requires javafx.fxml;
    requires transitive jakarta.mail;

    exports co.edu.uniquindio.poo to javafx.fxml;
}
