package org.example.retoconjuntoad_di_2.session;

import org.example.retoconjuntoad_di_2.model.user.User;
import org.example.retoconjuntoad_di_2.model.user.UserRepository;

import java.util.Optional;

/**
 * Servicio de autenticación para validar usuarios en el sistema.
 * Proporciona métodos para verificar las credenciales de los usuarios.
 */
public class AuthService {

    private final UserRepository userRepository; // Repositorio para gestionar usuarios.

    /**
     * Constructor que inicializa el servicio de autenticación con un repositorio de usuarios.
     *
     * @param userRepository Repositorio de usuarios.
     */
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Valida las credenciales de un usuario.
     * Comprueba si el nombre de usuario y la contraseña proporcionados son correctos.
     *
     * @param nombreUsuario Nombre de usuario a validar.
     * @param contrasena Contraseña a validar.
     * @return Un Optional que contiene el usuario si las credenciales son correctas, o un Optional vacío si no lo son.
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
