package org.example.retoconjuntoad_di_2.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.retoconjuntoad_di_2.model.pelicula.Pelicula;
import org.example.retoconjuntoad_di_2.model.pelicula.PeliculaRepository;
import org.example.retoconjuntoad_di_2.utils.DataProvider;
import org.example.retoconjuntoad_di_2.utils.JavaFXUtil;

import java.net.URL;
import java.time.Year;
import java.util.ResourceBundle;

/**
 * Controlador para gestionar los detalles de una película.
 * Permite crear y guardar una nueva película en el sistema.
 */
public class PeliculaDetailController implements Initializable {

    /**
     * Campo de texto para ingresar el título de la película.
     */
    @FXML private TextField txtTitulo;
    
    /**
     * Campo de texto para ingresar el género de la película.
     */
    @FXML private TextField txtGenero;
    
    /**
     * Campo de texto para ingresar el año de lanzamiento de la película.
     */
    @FXML private TextField txtAnio;
    
    /**
     * Campo de texto para ingresar el director de la película.
     */
    @FXML private TextField txtDirector;
    
    /**
     * Área de texto para ingresar la descripción de la película.
     */
    @FXML private TextArea txtDescripcion;

    /**
     * Repositorio para gestionar las operaciones CRUD de películas.
     */
    private PeliculaRepository peliculaRepository;
    
    /**
     * Año mínimo permitido para una película.
     * Se utiliza para validar que el año introducido sea realista.
     */
    private static final short MIN_ANIO = 1900;

    /**
     * Inicializa el controlador y configura el repositorio de películas.
     * <p>
     * Este método se ejecuta automáticamente cuando se carga la vista FXML.
     * Crea la instancia del repositorio y configura el campo de año con un
     * texto de ayuda que muestra el rango permitido.
     * </p>
     *
     * @param url URL de inicialización (no utilizado).
     * @param resourceBundle Recursos de inicialización (no utilizado).
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        peliculaRepository = new PeliculaRepository(DataProvider.getEntityManagerFactory());

        int anioActual = Year.now().getValue();
        // Establecer texto de ayuda con el rango permitido, por ejemplo "1900 - 2025".
        txtAnio.setPromptText(MIN_ANIO + " - " + anioActual);
    }

    /**
     * Maneja el evento de guardar una nueva película.
     * <p>
     * Este método se ejecuta cuando el usuario hace clic en el botón "Guardar".
     * Realiza las siguientes validaciones:
     * <ul>
     *   <li>Verifica que los campos obligatorios (título, género, año) no estén vacíos</li>
     *   <li>Valida que el año sea un número válido</li>
     *   <li>Valida que el año esté dentro del rango permitido (1900 - año actual)</li>
     * </ul>
     * </p>
     * <p>
     * Si todas las validaciones pasan, crea una nueva película con los datos
     * introducidos y la guarda en la base de datos. Después muestra un mensaje
     * de confirmación y cierra la ventana.
     * </p>
     *
     * @param actionEvent Evento de acción generado al presionar el botón de guardar.
     */
    @FXML
    public void guardar(ActionEvent actionEvent) {
        String titulo = txtTitulo.getText();
        String genero = txtGenero.getText();
        String anioStr = txtAnio.getText();
        String director = txtDirector.getText();
        String descripcion = txtDescripcion.getText();

        // Validar que los campos obligatorios no estén vacíos.
        if (titulo.isBlank() || genero.isBlank() || anioStr.isBlank()) {
            JavaFXUtil.showModal(
                    Alert.AlertType.ERROR,
                    "Datos incompletos",
                    "Faltan campos obligatorios",
                    "Título, género y año son obligatorios."
            );
            return;
        }

        Short anio;
        try {
            // Validar que el año sea un número válido.
            anio = Short.parseShort(anioStr);
        } catch (NumberFormatException e) {
            JavaFXUtil.showModal(
                    Alert.AlertType.ERROR,
                    "Año incorrecto",
                    "Formato inválido",
                    "Introduce un número válido para el año."
            );
            return;
        }

        int anioActual = Year.now().getValue();

        // Validar que el año esté dentro del rango permitido.
        if (anio < MIN_ANIO || anio > anioActual) {
            JavaFXUtil.showModal(
                    Alert.AlertType.ERROR,
                    "Año fuera de rango",
                    "Valor no realista",
                    "El año debe estar entre " + MIN_ANIO + " y " + anioActual + "."
            );
            return;
        }

        // Crear una nueva película con los datos ingresados.
        Pelicula pelicula = new Pelicula();
        pelicula.setTitulo(titulo);
        pelicula.setGenero(genero);
        pelicula.setAnio(anio);
        pelicula.setDirector(director);
        pelicula.setDescripcion(descripcion);

        // Guardar la película en el repositorio.
        peliculaRepository.save(pelicula);

        JavaFXUtil.showModal(
                Alert.AlertType.INFORMATION,
                "Película creada",
                "Operación completada",
                "La nueva película se ha registrado correctamente."
        );

        cerrarVentana();
    }

    /**
     * Maneja el evento de cancelar la operación.
     * <p>
     * Este método se ejecuta cuando el usuario hace clic en el botón "Cancelar".
     * Cierra la ventana actual sin guardar ningún cambio.
     * </p>
     *
     * @param actionEvent Evento de acción generado al presionar el botón de cancelar.
     */
    @FXML
    public void cancelar(ActionEvent actionEvent) {
        cerrarVentana();
    }

    /**
     * Cierra la ventana actual.
     * <p>
     * Obtiene la referencia a la ventana (Stage) desde el campo de texto y la cierra.
     * </p>
     */
    private void cerrarVentana() {
        Stage st = (Stage) txtTitulo.getScene().getWindow();
        st.close();
    }
}
