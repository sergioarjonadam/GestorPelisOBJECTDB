package org.example.retoconjuntoad_di_2;

import javafx.application.Application;

/**
 * Clase lanzadora (Launcher) de la aplicación.
 * <p>
 * Esta clase contiene el método {@code main} que inicia la aplicación JavaFX.
 * Es el punto de entrada estándar para ejecutar la aplicación desde la línea de comandos
 * o desde un IDE.
 * </p>
 * <p>
 * Utiliza {@link Application#launch(Class, String...)} para iniciar la aplicación JavaFX,
 * lo que permite que JavaFX configure correctamente el entorno antes de llamar al método
 * {@link App#start(javafx.stage.Stage)}.
 * </p>
 *
 * @author Sistema de Gestión de Películas
 * @version 1.0
 */
public class Launcher {
    
    /**
     * Método principal que inicia la aplicación JavaFX.
     * <p>
     * Este método es el punto de entrada de la aplicación. Delega el inicio
     * de la aplicación JavaFX al framework mediante {@link Application#launch(Class, String...)}.
     * </p>
     *
     * @param args Argumentos de línea de comandos pasados a la aplicación.
     *              Actualmente no se utilizan en esta aplicación.
     */
    public static void main(String[] args) {
        Application.launch(App.class, args);
    }
}
