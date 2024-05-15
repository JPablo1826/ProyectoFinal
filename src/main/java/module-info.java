open module co.edu.uniquindio.poo {
    requires javafx.controls;
    requires transitive javafx.graphics;
    requires static lombok;
    requires javafx.fxml;
    requires transitive jakarta.mail;
    requires com.google.zxing;
	requires com.google.zxing.javase;
    requires javafx.base;

    exports co.edu.uniquindio.poo to javafx.fxml;
}
