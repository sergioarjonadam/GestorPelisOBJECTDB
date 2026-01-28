package org.example.retoconjuntoad_di_2.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.retoconjuntoad_di_2.model.copia.Copia;
import org.example.retoconjuntoad_di_2.model.copia.CopiaRepository;
import org.example.retoconjuntoad_di_2.model.user.User;
import org.example.retoconjuntoad_di_2.session.SimpleSessionService;
import org.example.retoconjuntoad_di_2.utils.DataProvider;
import org.example.retoconjuntoad_di_2.utils.JavaFXUtil;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controlador principal de la aplicación.
 * <p>
 * Este controlador gestiona la vista principal de la aplicación, que muestra
 * una tabla con todas las copias de películas del usuario actualmente logueado.
 * Proporciona funcionalidades para:
 * <ul>
 *   <li>Visualizar las copias del usuario en una tabla</li>
 *   <li>Buscar copias por título de película</li>
 *   <li>Añadir nuevas copias</li>
 *   <li>Ver detalles de una copia</li>
 *   <li>Eliminar copias</li>
 *   <li>Añadir nuevas películas (solo para administradores)</li>
 *   <li>Cerrar sesión</li>
 * </ul>
 * </p>
 * <p>
 * El controlador utiliza un sistema de filtrado en tiempo real para permitir
 * la búsqueda de copias por título de película mientras el usuario escribe.
 * </p>
 *
 * @author Sistema de Gestión de Películas
 * @version 1.0
 */
public class MainController implements Initializable {

    @FXML
    public Button btnAñadir;
    @FXML
    public Button btnBorrar;
    @FXML
    public Button btnDetalle;

    @FXML
    private Button btnLogout;

    @FXML
    private Label welcomeText;

    @FXML
    private Label lblUsuario;

    @FXML
    private Label lblTotalCopias;

    @FXML
    private TableView<Copia> tabla;

    @FXML
    private TableColumn<Copia, String> cId;

    @FXML
    private TableColumn<Copia, String> cTitulo;

    @FXML
    private TableColumn<Copia, String> cGenero;

    @FXML
    private TableColumn<Copia, String> cAnio;

    @FXML
    private TableColumn<Copia, String> cEstado;

    @FXML
    private TableColumn<Copia, String> cSoporte;

    @FXML
    private Button btnAddPelicula;

    /**
     * Campo de texto para realizar búsquedas por título de película.
     * El filtrado se aplica en tiempo real mientras el usuario escribe.
     */
    @FXML
    private TextField txtBuscar;

    /**
     * Servicio de sesión para gestionar el usuario actualmente logueado.
     */
    private SimpleSessionService simpleSessionService;
    
    /**
     * Repositorio para gestionar las operaciones CRUD de copias.
     */
    private CopiaRepository copiaRepository;

    /**
     * Lista observable completa con todas las copias del usuario.
     * Esta lista se carga desde la base de datos y se mantiene en memoria.
     */
    private final ObservableList<Copia> copiasUsuario = FXCollections.observableArrayList();
    
    /**
     * Lista filtrada basada en copiasUsuario.
     * Se actualiza automáticamente cuando cambia el texto de búsqueda.
     */
    private FilteredList<Copia> copiasFiltradas;

    /**
     * Inicializa el controlador y configura la interfaz de usuario.
     * <p>
     * Este método se ejecuta automáticamente cuando se carga la vista FXML.
     * Realiza las siguientes acciones:
     * <ul>
     *   <li>Verifica que haya un usuario logueado</li>
     *   <li>Muestra el nombre del usuario actual</li>
     *   <li>Configura los permisos según el rol del usuario (admin o usuario normal)</li>
     *   <li>Configura las columnas de la tabla</li>
     *   <li>Configura el sistema de búsqueda</li>
     *   <li>Carga las copias del usuario</li>
     * </ul>
     * </p>
     *
     * @param url URL de inicialización (no utilizado).
     * @param resourceBundle Recursos de inicialización (no utilizado).
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        simpleSessionService = new SimpleSessionService();
        copiaRepository = new CopiaRepository(DataProvider.getEntityManagerFactory());

        if (!simpleSessionService.isLoggedIn()) {
            JavaFXUtil.showModal(
                    Alert.AlertType.WARNING,
                    "Sesión",
                    "No hay usuario logueado",
                    "Vuelve a iniciar sesión."
            );
            JavaFXUtil.setScene("/org/example/retoconjuntoad_di_2/login-view.fxml");
            return;
        }

        User user = simpleSessionService.getActive();
        lblUsuario.setText("Usuario: " + user.getNombreUsuario());

        if (!user.isEsAdmin()) {
            btnAddPelicula.setVisible(false);
            btnAddPelicula.setManaged(false);
        }

        configurarTabla();

        // Configurar lista filtrada y búsqueda
        copiasFiltradas = new FilteredList<>(copiasUsuario, copia -> true);
        tabla.setItems(copiasFiltradas);

        configurarBusqueda();

        cargarCopiasUsuario(user);
    }

    /**
     * Configura las columnas de la tabla de copias.
     * <p>
     * Establece los cell value factories para cada columna, extrayendo los valores
     * de los objetos Copia y sus relaciones (Pelicula, User) para mostrarlos en la tabla.
     * </p>
     */
    private void configurarTabla() {
        cId.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getId() != null
                                ? cellData.getValue().getId().toString()
                                : ""
                )
        );

        cTitulo.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getPelicula() != null
                                ? cellData.getValue().getPelicula().getTitulo()
                                : ""
                )
        );

        cGenero.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getPelicula() != null
                                ? cellData.getValue().getPelicula().getGenero()
                                : ""
                )
        );

        cAnio.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        (cellData.getValue().getPelicula() != null
                                && cellData.getValue().getPelicula().getAnio() != null)
                                ? cellData.getValue().getPelicula().getAnio().toString()
                                : ""
                )
        );

        cEstado.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getEstado() != null
                                ? cellData.getValue().getEstado()
                                : ""
                )
        );

        cSoporte.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getSoporte() != null
                                ? cellData.getValue().getSoporte()
                                : ""
                )
        );
    }

    /**
     * Configura el filtro de búsqueda en tiempo real por título de película.
     * <p>
     * Añade un listener al campo de búsqueda que actualiza automáticamente
     * el predicado de filtrado de la lista filtrada. El filtro busca copias
     * cuyo título de película comience con el texto introducido (insensible a mayúsculas).
     * </p>
     * <p>
     * También actualiza el contador de copias visibles cada vez que cambia el filtro.
     * </p>
     */
    private void configurarBusqueda() {
        if (txtBuscar == null) {
            return; // por si el FXML aún no tiene el campo
        }

        txtBuscar.textProperty().addListener((obs, oldValue, newValue) -> {
            String filtro = newValue != null ? newValue.trim().toLowerCase() : "";

            copiasFiltradas.setPredicate(copia -> {
                if (filtro.isEmpty()) {
                    return true;
                }
                if (copia.getPelicula() == null || copia.getPelicula().getTitulo() == null) {
                    return false;
                }
                String titulo = copia.getPelicula().getTitulo().toLowerCase();
                // Empieza por el texto escrito, p.ej. "el pa"
                return titulo.startsWith(filtro);
            });

            // Actualizar contador con las filas visibles
            lblTotalCopias.setText("Total de copias: " + copiasFiltradas.size());
        });
    }

    /**
     * Carga las copias del usuario especificado desde la base de datos.
     * <p>
     * Este método consulta el repositorio para obtener todas las copias asociadas
     * al usuario y las añade a la lista observable. Después de cargar, actualiza
     * el contador de copias visibles (teniendo en cuenta el filtro activo).
     * </p>
     *
     * @param user El usuario cuyas copias se desean cargar.
     */
    private void cargarCopiasUsuario(User user) {
        copiasUsuario.clear();
        List<Copia> copias = copiaRepository.findByUser(user);
        copiasUsuario.addAll(copias);

        // Al recargar, se aplica el filtro actual automáticamente
        lblTotalCopias.setText("Total de copias: " + copiasFiltradas.size());
    }

    /**
     * Maneja el evento de borrado de una copia.
     * <p>
     * Este método se ejecuta cuando el usuario hace clic en el botón "Borrar".
     * Verifica que haya una copia seleccionada en la tabla y muestra un diálogo
     * de confirmación antes de proceder con la eliminación.
     * </p>
     * <p>
     * Si el usuario confirma, elimina la copia de la base de datos y recarga
     * la lista de copias del usuario.
     * </p>
     *
     * @param actionEvent Evento de acción generado al presionar el botón de borrar.
     */
    @FXML
    public void borrar(ActionEvent actionEvent) {
        Copia seleccionada = tabla.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            JavaFXUtil.showModal(
                    Alert.AlertType.INFORMATION,
                    "Borrar copia",
                    "Ninguna copia seleccionada",
                    "Selecciona una copia en la tabla."
            );
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar borrado");
        confirmacion.setHeaderText("¿Seguro que quieres borrar esta copia?");
        confirmacion.setContentText("Esta acción no se puede deshacer.");

        confirmacion.showAndWait()
                .filter(boton -> boton == ButtonType.OK)
                .ifPresent(botonOk -> {
                    copiaRepository.delete(seleccionada);
                    cargarCopiasUsuario(simpleSessionService.getActive());
                });
    }

    /**
     * Maneja el evento de añadir una nueva copia.
     * <p>
     * Este método se ejecuta cuando el usuario hace clic en el botón "Añadir".
     * Crea una nueva instancia de Copia asociada al usuario actual y abre una
     * ventana modal para editar los detalles de la copia.
     * </p>
     * <p>
     * Después de cerrar la ventana de detalle, recarga la lista de copias
     * para reflejar los cambios realizados.
     * </p>
     *
     * @param actionEvent Evento de acción generado al presionar el botón de añadir.
     */
    @FXML
    public void añadir(ActionEvent actionEvent) {
        var user = simpleSessionService.getActive();
        if (user == null) {
            JavaFXUtil.showModal(
                    Alert.AlertType.WARNING,
                    "Sesión",
                    "No hay usuario logueado",
                    "Vuelve a iniciar sesión."
            );
            JavaFXUtil.setScene("/org/example/retoconjuntoad_di_2/login-view.fxml");
            return;
        }

        Copia nueva = new Copia();
        nueva.setUser(user);

        try {
            FXMLLoader loader = new FXMLLoader(
                    JavaFXUtil.class.getResource("/org/example/retoconjuntoad_di_2/copy-detail-view.fxml")
            );
            Parent root = loader.load();

            CopyDetailController controller = loader.getController();
            controller.setCopia(nueva);

            Stage detailStage = new Stage();
            detailStage.setTitle("Nueva copia");
            detailStage.initOwner(JavaFXUtil.getStage());
            detailStage.initModality(Modality.WINDOW_MODAL);
            detailStage.setScene(new Scene(root));
            detailStage.showAndWait();

            cargarCopiasUsuario(simpleSessionService.getActive());

        } catch (Exception e) {
            e.printStackTrace();
            JavaFXUtil.showModal(
                    Alert.AlertType.ERROR,
                    "Error",
                    "No se pudo abrir la ventana de detalle",
                    e.getMessage()
            );
        }
    }

    /**
     * Maneja el evento de visualizar los detalles de una copia seleccionada.
     * <p>
     * Este método se ejecuta cuando el usuario hace clic en el botón "Detalle".
     * Verifica que haya una copia seleccionada en la tabla y abre una ventana
     * modal para mostrar y editar los detalles de la copia.
     * </p>
     * <p>
     * Después de cerrar la ventana de detalle, recarga la lista de copias
     * para reflejar los cambios realizados.
     * </p>
     *
     * @param actionEvent Evento de acción generado al presionar el botón de detalle.
     */
    @FXML
    public void verDetalle(ActionEvent actionEvent) {
        Copia seleccionada = tabla.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            JavaFXUtil.showModal(
                    Alert.AlertType.INFORMATION,
                    "Detalle de copia",
                    "Ninguna copia seleccionada",
                    "Selecciona una copia en la tabla."
            );
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(
                    JavaFXUtil.class.getResource("/org/example/retoconjuntoad_di_2/copy-detail-view.fxml")
            );
            Parent root = loader.load();

            CopyDetailController controller = loader.getController();
            controller.setCopia(seleccionada);

            Stage detailStage = new Stage();
            detailStage.setTitle("Detalle de copia");
            detailStage.initOwner(JavaFXUtil.getStage());
            detailStage.initModality(Modality.WINDOW_MODAL);
            detailStage.setScene(new Scene(root));
            detailStage.showAndWait();

            cargarCopiasUsuario(simpleSessionService.getActive());

        } catch (Exception e) {
            e.printStackTrace();
            JavaFXUtil.showModal(
                    Alert.AlertType.ERROR,
                    "Error",
                    "No se pudo abrir la ventana de detalle",
                    e.getMessage()
            );
        }
    }

    @FXML
    public void añadirPelicula(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    JavaFXUtil.class.getResource("/org/example/retoconjuntoad_di_2/pelicula-detail-view.fxml")
            );
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initOwner(JavaFXUtil.getStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle("Nueva película");
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            JavaFXUtil.showModal(
                    Alert.AlertType.ERROR,
                    "Error",
                    "No se pudo abrir la ventana de película",
                    e.getMessage()
            );
        }
    }

    /**
     * Maneja el evento de cierre de sesión.
     * <p>
     * Este método se ejecuta cuando el usuario hace clic en el botón "Cerrar Sesión".
     * Muestra un mensaje informativo, cierra la sesión del usuario actual y redirige
     * a la pantalla de login.
     * </p>
     *
     * @param event Evento de acción generado al presionar el botón de cerrar sesión.
     */
    @FXML
    public void logout(ActionEvent event) {
        JavaFXUtil.showModal(
                Alert.AlertType.INFORMATION,
                "Cerrar sesión",
                "Sesión cerrada",
                "Has cerrado la sesión correctamente."
        );

        simpleSessionService.logout();
        JavaFXUtil.setScene("/org/example/retoconjuntoad_di_2/login-view.fxml");
    }
}
