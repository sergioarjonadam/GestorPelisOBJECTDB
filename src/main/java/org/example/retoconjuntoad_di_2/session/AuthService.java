package org.example.retoconjuntoad_di_2.session;

import org.example.retoconjuntoad_di_2.model.user.User;
import org.example.retoconjuntoad_di_2.model.user.UserRepository;

import java.util.Optional;

/**
 * Servicio de autenticación para validar usuarios en el sistema.
 * <p>
 * Esta clase proporciona la lógica de negocio para la autenticación de usuarios.
 * Valida las credenciales (nombre de usuario y contraseña) comparándolas con
 * los datos almacenados en la base de datos.
 * </p>
 * <p>
 * <strong>Seguridad:</strong>
 * Actualmente las contraseñas se comparan en texto plano. En una aplicación
 * de producción, deberían almacenarse utilizando algoritmos de hash seguros
 * (por ejemplo, BCrypt) y compararse utilizando métodos seguros de comparación.
 * </p>
 *
 * @author Sistema de Gestión de Películas
 * @version 1.0
 * @see org.example.retoconjuntoad_di_2.model.user.User
 * @see org.example.retoconjuntoad_di_2.model.user.UserRepository
 */
public class AuthService {

    /**
     * Repositorio para gestionar las operaciones CRUD de usuarios.
     * Se utiliza para buscar usuarios en la base de datos durante la autenticación.
     */
    private final UserRepository userRepository;

    /**
     * Constructor que inicializa el servicio de autenticación con un repositorio de usuarios.
     * <p>
     * El repositorio se inyecta como dependencia, lo que permite una fácil
     * sustitución para pruebas unitarias o diferentes implementaciones.
     * </p>
     *
     * @param userRepository Repositorio de usuarios. No debe ser {@code null}.
     */
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Valida las credenciales de un usuario.
     * <p>
     * Este método realiza la autenticación del usuario mediante los siguientes pasos:
     * <ol>
     *   <li>Busca el usuario en la base de datos por su nombre de usuario</li>
     *   <li>Si el usuario existe, compara la contraseña proporcionada con la almacenada</li>
     *   <li>Si las credenciales coinciden, devuelve el usuario autenticado</li>
     *   <li>Si las credenciales no coinciden o el usuario no existe, devuelve un Optional vacío</li>
     * </ol>
     * </p>
     *
     * @param nombreUsuario Nombre de usuario a validar. No debe ser {@code null} ni vacío.
     * @param contrasena Contraseña a validar. No debe ser {@code null} ni vacía.
     * @return Un Optional que contiene el usuario si las credenciales son correctas,
     *         o un Optional vacío si las credenciales son incorrectas o el usuario no existe.
     */
    public Optional<User> validateUser(String nombreUsuario, String contrasena) {
        Optional<User> user = userRepository.findByNombreUsuario(nombreUsuario);

        if (user.isPresent()) {
            // getContrasena() viene de Lombok (@Data) a partir del campo "contrasena"
            if (user.get().getContrasena().equals(contrasena)) {
                return user;
            } else {
                return Optional.empty();
            }
        }

        // Si no existe el usuario, devolvemos Optional.empty()
        return Optional.empty();
    }
}
