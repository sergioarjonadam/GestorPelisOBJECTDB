package org.example.retoconjuntoad_di_2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.retoconjuntoad_di_2.utils.JavaFXUtil;


import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Inicializar datos de ejemplo en la base de datos ObjectDB
        DatabaseSeeder.seedIfEmpty();

        JavaFXUtil.initStage(stage);
        JavaFXUtil.setScene("/org/example/retoconjuntoad_di_2/login-view.fxml");

    }
}
