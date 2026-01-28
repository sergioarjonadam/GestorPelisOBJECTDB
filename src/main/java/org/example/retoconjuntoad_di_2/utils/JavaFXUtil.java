package org.example.retoconjuntoad_di_2.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.IOException;

/**
 * Clase de utilidad para gestionar la ventana principal y operaciones comunes de JavaFX.
 * <p>
 * Esta clase proporciona métodos estáticos para simplificar operaciones comunes
 * relacionadas con la gestión de ventanas, cambio de escenas y visualización
 * de diálogos modales en aplicaciones JavaFX.
 * </p>
 * <p>
 * Mantiene una referencia estática a la ventana principal (Stage) de la aplicación,
 * lo que permite acceder a ella desde cualquier parte del código sin necesidad
 * de pasarla como parámetro.
 * </p>
 *
 * @author Sistema de Gestión de Películas
 * @version 1.0
 */
public class JavaFXUtil {

    /**
     * Ventana principal (Stage) de la aplicación JavaFX.
     * Esta referencia estática permite acceder a la ventana principal desde
     * cualquier parte de la aplicación.
     */
    @Getter
    private static Stage stage;

    /**
     * Constructor privado para prevenir la instanciación de esta clase de utilidad.
     * Todos los métodos son estáticos y no se requiere crear instancias.
     */
    private JavaFXUtil() {}

    /**
     * Inicializa la referencia estática a la ventana principal de la aplicación.
     * <p>
     * Este método debe ser llamado una vez al inicio de la aplicación, típicamente
     * desde el método {@link javafx.application.Application#start(Stage)}.
     * </p>
     *
     * @param stage La ventana principal de la aplicación JavaFX.
     */
    public static void initStage(Stage stage) {
        JavaFXUtil.stage = stage;
    }

    /**
     * Carga un archivo FXML y cambia la escena actual de la ventana principal.
     * <p>
     * Este método carga un archivo FXML especificado, crea una nueva escena con
     * el contenido cargado, y la establece como la escena activa de la ventana principal.
     * La ventana se centra en la pantalla y se muestra automáticamente.
     * </p>
     * <p>
     * El método también devuelve el controlador asociado al archivo FXML cargado,
     * lo que permite acceder a los métodos y propiedades del controlador después
     * de cargar la vista.
     * </p>
     *
     * @param <T> Tipo del controlador asociado al archivo FXML.
     * @param fxml Ruta del archivo FXML a cargar, relativa al classpath.
     *              Por ejemplo: "/org/example/retoconjuntoad_di_2/login-view.fxml"
     * @return El controlador asociado al archivo FXML cargado, o {@code null} si
     *         ocurre un error durante la carga.
     */
    public static <T> T setScene(String fxml){
        try{
            FXMLLoader loader = new FXMLLoader(JavaFXUtil.class.getResource(fxml));
            Parent root = loader.load();
            T controller = loader.getController();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
            return controller;
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Muestra un diálogo modal (Alert) con el tipo, título, encabezado y contenido especificados.
     * <p>
     * Este método crea y muestra un diálogo modal que bloquea la interacción con
     * la ventana principal hasta que el usuario lo cierre. El diálogo se muestra
     * como ventana secundaria de la ventana principal.
     * </p>
     * <p>
     * Los diferentes tipos de Alert permiten mostrar diferentes iconos y comportamientos:
     * <ul>
     *   <li>{@link Alert.AlertType#INFORMATION}: Mensaje informativo</li>
     *   <li>{@link Alert.AlertType#WARNING}: Advertencia</li>
     *   <li>{@link Alert.AlertType#ERROR}: Error</li>
     *   <li>{@link Alert.AlertType#CONFIRMATION}: Confirmación (con botones Sí/No)</li>
     * </ul>
     * </p>
     *
     * @param type Tipo de alerta que determina el icono y comportamiento del diálogo.
     * @param title Título de la ventana del diálogo.
     * @param header Texto del encabezado del diálogo (puede ser {@code null}).
     * @param content Texto del contenido principal del diálogo.
     */
    public static void showModal(Alert.AlertType type, String title, String header, String content){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.initOwner(stage);
        alert.showAndWait();
    }

}
