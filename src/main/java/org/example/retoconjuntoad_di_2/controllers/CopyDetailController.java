package org.example.retoconjuntoad_di_2.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.retoconjuntoad_di_2.model.copia.Copia;
import org.example.retoconjuntoad_di_2.model.copia.CopiaRepository;
import org.example.retoconjuntoad_di_2.model.pelicula.Pelicula;
import org.example.retoconjuntoad_di_2.model.pelicula.PeliculaRepository;
import org.example.retoconjuntoad_di_2.utils.DataProvider;
import org.example.retoconjuntoad_di_2.utils.JavaFXUtil;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador para gestionar los detalles de una copia.
 * Permite visualizar, editar, guardar y eliminar copias.
 */
public class CopyDetailController implements Initializable {

    /**
     * Botón para guardar los cambios realizados en la copia.
     */
    @FXML
    public Button btnGuardar;
    
    /**
     * Botón para eliminar la copia actual.
     */
    @FXML
    public Button btnEliminar;
    
    /**
     * Botón para cancelar la operación y cerrar la ventana.
     */
    @FXML
    public Button btnCancelar;

    /**
     * Etiqueta para mostrar el ID de la copia.
     */
    @FXML
    private Label lblId;

    /**
     * ComboBox para seleccionar una película de la lista disponible.
     */
    @FXML
    private ComboBox<Pelicula> comboPelicula;

    /**
     * Etiqueta para mostrar el título de la película seleccionada.
     */
    @FXML
    private Label lblTitulo;

    /**
     * Etiqueta para mostrar el género de la película seleccionada.
     */
    @FXML
    private Label lblGenero;

    /**
     * Etiqueta para mostrar el año de la película seleccionada.
     */
    @FXML
    private Label lblAnio;

    /**
     * ComboBox para seleccionar el estado de la copia.
     * Opciones disponibles: "Nueva", "Buena", "Usada", "Deteriorada".
     */
    @FXML
    private ComboBox<String> comboEstado;

    /**
     * ComboBox para seleccionar el soporte físico de la copia.
     * Opciones disponibles: "DVD", "Blu-ray", "VHS".
     */
    @FXML
    private ComboBox<String> comboSoporte;

    /**
     * Objeto Copia que se está gestionando en esta ventana.
     * Puede ser una copia nueva (sin ID) o una copia existente (con ID).
     */
    private Copia copia;
    
    /**
     * Repositorio para gestionar las operaciones CRUD de copias.
     */
    private CopiaRepository copiaRepository;
    
    /**
     * Repositorio para gestionar las operaciones CRUD de películas.
     */
    private PeliculaRepository peliculaRepository;

    /**
     * Inicializa el controlador y configura los elementos de la interfaz.
     * <p>
     * Este método se ejecuta automáticamente cuando se carga la vista FXML.
     * Realiza las siguientes acciones:
     * <ul>
     *   <li>Crea las instancias de los repositorios</li>
     *   <li>Configura las opciones de estado y soporte en los ComboBox</li>
     *   <li>Carga todas las películas disponibles en el ComboBox de películas</li>
     *   <li>Configura un listener para actualizar los datos de la película cuando se selecciona una</li>
     * </ul>
     * </p>
     *
     * @param url URL de inicialización (no utilizado).
     * @param resourceBundle Recursos de inicialización (no utilizado).
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        copiaRepository = new CopiaRepository(DataProvider.getEntityManagerFactory());
        peliculaRepository = new PeliculaRepository(DataProvider.getEntityManagerFactory());

        // Configurar opciones de estado y soporte.
        comboEstado.getItems().addAll("Nueva", "Buena", "Usada", "Deteriorada");
        comboSoporte.getItems().addAll("DVD", "Blu-ray", "VHS");

        // Cargar todas las películas en el ComboBox.
        comboPelicula.getItems().setAll(peliculaRepository.findAll());

        // Actualizar los campos de información al cambiar la película seleccionada.
        comboPelicula.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> actualizarDatosPelicula(newVal)
        );
    }

    /**
     * Establece la copia que se va a gestionar y rellena los campos con sus datos.
     * <p>
     * Este método debe ser llamado después de cargar la vista FXML para establecer
     * qué copia se está editando. Si se pasa una copia nueva (sin ID), los campos
     * se dejarán vacíos para que el usuario los complete. Si se pasa una copia
     * existente (con ID), los campos se rellenarán con los datos actuales.
     * </p>
     *
     * @param copia Objeto Copia a gestionar. Puede ser {@code null} para crear una nueva copia.
     */
    public void setCopia(Copia copia) {
        this.copia = copia;
        rellenarCampos();
    }

    /**
     * Rellena los campos de la interfaz con los datos de la copia actual.
     * <p>
     * Este método sincroniza el estado de la interfaz con los datos del objeto Copia.
     * Si la copia es nueva (sin ID), los campos se dejan en blanco. Si la copia
     * ya existe, se muestran sus datos actuales y se deshabilita el cambio de película
     * para mantener la integridad de los datos.
     * </p>
     */
    private void rellenarCampos() {
        if (copia == null) {
            return;
        }

        // Mostrar el ID de la copia.
        if (copia.getId() != null) {
            lblId.setText(copia.getId().toString());
        } else {
            lblId.setText("-");
        }

        // Configurar la película seleccionada.
        if (copia.getPelicula() != null) {
            comboPelicula.getSelectionModel().select(copia.getPelicula());
            if (copia.getId() != null) {
                // Si la copia ya existe, no se permite cambiar de película.
                comboPelicula.setDisable(true);
            }
            actualizarDatosPelicula(copia.getPelicula());
        } else {
            actualizarDatosPelicula(null);
        }

        // Configurar estado y soporte.
        if (copia.getEstado() != null) {
            comboEstado.getSelectionModel().select(copia.getEstado());
        }
        if (copia.getSoporte() != null) {
            comboSoporte.getSelectionModel().select(copia.getSoporte());
        }
    }

    /**
     * Actualiza los campos de información de la película seleccionada.
     * <p>
     * Este método se ejecuta automáticamente cuando el usuario selecciona una
     * película diferente en el ComboBox. Actualiza las etiquetas de título,
     * género y año con los datos de la película seleccionada.
     * </p>
     *
     * @param pelicula Película seleccionada. Puede ser {@code null} si no hay selección.
     */
    private void actualizarDatosPelicula(Pelicula pelicula) {
        if (pelicula == null) {
            lblTitulo.setText("-");
            lblGenero.setText("-");
            lblAnio.setText("-");
            return;
        }

        lblTitulo.setText(pelicula.getTitulo() != null ? pelicula.getTitulo() : "-");
        lblGenero.setText(pelicula.getGenero() != null ? pelicula.getGenero() : "-");
        lblAnio.setText(pelicula.getAnio() != null ? pelicula.getAnio().toString() : "-");
    }

    /**
     * Guarda los cambios realizados en la copia.
     * <p>
     * Este método se ejecuta cuando el usuario hace clic en el botón "Guardar".
     * Realiza las siguientes acciones:
     * <ol>
     *   <li>Valida que todos los campos requeridos estén completos (película, estado, soporte)</li>
     *   <li>Actualiza el objeto Copia con los valores de los campos</li>
     *   <li>Guarda la copia en la base de datos</li>
     *   <li>Muestra un mensaje de confirmación</li>
     *   <li>Cierra la ventana</li>
     * </ol>
     * </p>
     *
     * @param actionEvent Evento de acción generado al presionar el botón de guardar.
     */
    @FXML
    public void guardar(ActionEvent actionEvent) {
        if (copia == null) {
            return;
        }

        Pelicula peliculaSeleccionada = comboPelicula.getSelectionModel().getSelectedItem();
        String estado = comboEstado.getSelectionModel().getSelectedItem();
        String soporte = comboSoporte.getSelectionModel().getSelectedItem();

        // Validar que todos los campos requeridos estén completos.
        if (peliculaSeleccionada == null || estado == null || soporte == null) {
            JavaFXUtil.showModal(
                    Alert.AlertType.WARNING,
                    "Guardar copia",
                    "Datos incompletos",
                    "Debes seleccionar película, estado y soporte."
            );
            return;
        }

        // Actualizar los datos de la copia.
        copia.setPelicula(peliculaSeleccionada);
        copia.setEstado(estado);
        copia.setSoporte(soporte);

        // Guardar la copia en el repositorio.
        copiaRepository.save(copia);

        JavaFXUtil.showModal(
                Alert.AlertType.INFORMATION,
                "Guardar copia",
                "Operación realizada",
                "La copia se ha guardado correctamente."
        );

        cerrarVentana();
    }

    /**
     * Elimina la copia gestionada de la base de datos.
     * <p>
     * Este método se ejecuta cuando el usuario hace clic en el botón "Eliminar".
     * Solo elimina la copia si ya está guardada en la base de datos (tiene ID).
     * Si la copia es nueva (sin ID), simplemente cierra la ventana sin guardar.
     * </p>
     * <p>
     * Después de eliminar, muestra un mensaje de confirmación y cierra la ventana.
     * </p>
     *
     * @param actionEvent Evento de acción generado al presionar el botón de eliminar.
     */
    @FXML
    public void eliminar(ActionEvent actionEvent) {
        if (copia == null || copia.getId() == null) {
            // Si la copia no está guardada, solo cerrar la ventana.
            cerrarVentana();
            return;
        }

        // Eliminar la copia del repositorio.
        copiaRepository.delete(copia);

        JavaFXUtil.showModal(
                Alert.AlertType.INFORMATION,
                "Eliminar copia",
                "Operación realizada",
                "La copia se ha eliminado correctamente."
        );

        cerrarVentana();
    }

    /**
     * Cancela la operación y cierra la ventana sin guardar cambios.
     * <p>
     * Este método se ejecuta cuando el usuario hace clic en el botón "Cancelar".
     * Cierra la ventana actual sin realizar ninguna operación de guardado o eliminación.
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
     * Obtiene la referencia a la ventana (Stage) desde el ComboBox de estado y la cierra.
     * </p>
     */
    private void cerrarVentana() {
        Stage stage = (Stage) comboEstado.getScene().getWindow();
        stage.close();
    }
}
