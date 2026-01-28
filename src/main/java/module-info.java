module org.example.retoconjuntoad_di_2 {
    requires javafx.controls;
    requires javafx.fxml;

    // JPA (ObjectDB actúa como implementación en el classpath)
    requires jakarta.persistence;
    requires java.naming;
    requires static lombok;

    opens org.example.retoconjuntoad_di_2 to javafx.fxml;
    opens org.example.retoconjuntoad_di_2.model.user to javafx.base;
    opens org.example.retoconjuntoad_di_2.model.pelicula to javafx.base;
    opens org.example.retoconjuntoad_di_2.model.copia to javafx.base;
    exports org.example.retoconjuntoad_di_2.controllers;
    opens org.example.retoconjuntoad_di_2.controllers to javafx.fxml;

    exports org.example.retoconjuntoad_di_2;
}
