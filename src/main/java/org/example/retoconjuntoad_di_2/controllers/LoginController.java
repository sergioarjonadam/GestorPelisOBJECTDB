package org.example.retoconjuntoad_di_2.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.retoconjuntoad_di_2.model.user.User;
import org.example.retoconjuntoad_di_2.model.user.UserRepository;
import org.example.retoconjuntoad_di_2.session.AuthService;
import org.example.retoconjuntoad_di_2.session.SimpleSessionService;
import org.example.retoconjuntoad_di_2.utils.DataProvider;
import org.example.retoconjuntoad_di_2.utils.JavaFXUtil;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controlador para gestionar el inicio de sesión de usuarios.
 * Permite validar credenciales y acceder a la aplicación principal.
 */
public class LoginController implements Initializable {

    @javafx.fxml.FXML
    private TextField txtCorreo; // Campo de texto para ingresar el correo electrónico.

    @javafx.fxml.FXML
    private Label info; // Etiqueta para mostrar mensajes informativos o de error.

    private UserRepository userRepository; // Repositorio para gestionar usuarios.
    private AuthService authService; // Servicio de autenticación de usuarios.
    @javafx.fxml.FXML
    private PasswordField txtContraseña;

    /**
     * Inicializa el controlador y configura los servicios necesarios.
     *
     * @param url URL de inicialización.
     * @param resourceBundle Recursos de inicialización.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Se crean los servicios necesarios para la autenticación.
        userRepository = new UserRepository(DataProvider.getEntityManagerFactory());
        authService = new AuthService(userRepository);
        info.setText(""); // Limpia el mensaje informativo al iniciar.
    }

    /**
     * Maneja el evento de inicio de sesión.
     * Valida las credenciales ingresadas y permite el acceso si son correctas.
     *
     * @param actionEvent Evento de acción generado al presionar el botón de entrar.
     */
    @javafx.fxml.FXML
    public void entrar(ActionEvent actionEvent) {
        info.setText("");

        String usuario = txtCorreo.getText();
        String contrasena = txtContraseña.getText();

        // Validación básica de campos vacíos.
        if (usuario == null || usuario.isBlank() || contrasena == null || contrasena.isBlank()) {
            info.setText("Usuario y contraseña son obligatorios.");
            JavaFXUtil.showModal(
                    Alert.AlertType.WARNING,
                    "Campos obligatorios",
                    "Faltan datos",
                    "Introduce usuario y contraseña."
            );
            return;
        }

        // Validar las credenciales del usuario.
        Optional<User> user = authService.validateUser(usuario, contrasena);

        if (user.isEmpty()) {
            info.setText("Usuario o contraseña incorrectos.");
            JavaFXUtil.showModal(
                    Alert.AlertType.ERROR,
                    "Error de login",
                    "Credenciales inválidas",
                    "El usuario o la contraseña no son correctos."
            );
            return;
        }

        // Si las credenciales son válidas, iniciar sesión y cambiar de vista.
        SimpleSessionService sessionService = new SimpleSessionService();
        sessionService.login(user.get());
        sessionService.setObject("id", user.get().getId());

        // Cambiar a la ventana principal del gestor de películas.
        JavaFXUtil.setScene("/org/example/retoconjuntoad_di_2/main-view.fxml");
    }

    /**
     * Maneja el evento de salir de la aplicación.
     * Finaliza la ejecución del programa.
     *
     * @param actionEvent Evento de acción generado al presionar el botón de salir.
     */
    @javafx.fxml.FXML
    public void Salir(ActionEvent actionEvent) {
        System.exit(0);
    }
}
