package org.example.retoconjuntoad_di_2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.retoconjuntoad_di_2.utils.JavaFXUtil;


import java.io.IOException;

/**
 * Clase principal de la aplicación JavaFX.
 * <p>
 * Esta clase extiende {@link Application} y representa el punto de entrada
 * de la aplicación de gestión de películas y copias. Se encarga de inicializar
 * la base de datos con datos de ejemplo si está vacía y mostrar la ventana de login.
 * </p>
 * <p>
 * La aplicación utiliza ObjectDB como base de datos embebida y JavaFX para
 * la interfaz gráfica de usuario.
 * </p>
 *
 * @author Sistema de Gestión de Películas
 * @version 1.0
 */
public class App extends Application {
    
    /**
     * Método principal de inicio de la aplicación JavaFX.
     * <p>
     * Este método se ejecuta automáticamente cuando se lanza la aplicación.
     * Realiza las siguientes acciones:
     * <ul>
     *   <li>Inicializa datos de ejemplo en la base de datos ObjectDB si está vacía</li>
     *   <li>Configura la ventana principal (Stage) de JavaFX</li>
     *   <li>Carga y muestra la vista de login</li>
     * </ul>
     * </p>
     *
     * @param stage La ventana principal de la aplicación JavaFX.
     * @throws IOException Si ocurre un error al cargar el archivo FXML de la vista de login.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Inicializar datos de ejemplo en la base de datos ObjectDB
        DatabaseSeeder.seedIfEmpty();

        JavaFXUtil.initStage(stage);
        JavaFXUtil.setScene("/org/example/retoconjuntoad_di_2/login-view.fxml");

    }
}
