package org.example.retoconjuntoad_di_2.session;

import org.example.retoconjuntoad_di_2.model.user.User;
import org.example.retoconjuntoad_di_2.utils.SessionService;

import java.util.HashMap;

/**
 * Implementación simple del servicio de sesión para gestionar usuarios logueados.
 * <p>
 * Esta clase proporciona una implementación básica de {@link SessionService}
 * que mantiene el estado de sesión en memoria utilizando variables estáticas.
 * </p>
 * <p>
 * <strong>Características:</strong>
 * <ul>
 *   <li>Gestiona el usuario actualmente autenticado</li>
 *   <li>Permite almacenar datos adicionales asociados a la sesión</li>
 *   <li>Utiliza almacenamiento estático, por lo que el estado persiste entre instancias</li>
 * </ul>
 * </p>
 * <p>
 * <strong>Limitaciones:</strong>
 * Esta implementación es adecuada para aplicaciones de escritorio de un solo usuario.
 * Para aplicaciones web o multi-usuario, se requeriría una implementación más sofisticada
 * que gestione múltiples sesiones concurrentes (por ejemplo, utilizando un mapa de sesiones
 * identificadas por tokens o IDs de sesión).
 * </p>
 *
 * @author Sistema de Gestión de Películas
 * @version 1.0
 * @see org.example.retoconjuntoad_di_2.utils.SessionService
 * @see org.example.retoconjuntoad_di_2.model.user.User
 */
public class SimpleSessionService implements SessionService<User> {

    /**
     * Usuario actualmente logueado en el sistema.
     * <p>
     * Es estático para que sea compartido entre todas las instancias de la clase.
     * Solo puede haber un usuario logueado a la vez en esta implementación.
     * </p>
     */
    private static User activeUser = null;

    /**
     * Mapa para almacenar datos adicionales de sesión.
     * <p>
     * Permite almacenar objetos arbitrarios asociados a claves de cadena durante
     * la sesión del usuario. Es estático para que sea compartido entre todas las
     * instancias de la clase.
     * </p>
     * <p>
     * Ejemplo de uso: almacenar el ID del usuario, preferencias temporales, etc.
     * </p>
     */
    private static HashMap<String, Object> data = new HashMap<>();

    /**
     * Inicia sesión con el usuario proporcionado.
     * <p>
     * Establece el usuario como el usuario activo en la sesión. Si ya había
     * un usuario logueado, será reemplazado por el nuevo usuario.
     * </p>
     *
     * @param user El usuario que inicia sesión. No debe ser {@code null}.
     */
    @Override
    public void login(User user) {
        activeUser = user;
    }

    /**
     * Verifica si hay un usuario logueado actualmente.
     * <p>
     * Este método permite verificar el estado de autenticación antes de realizar
     * operaciones que requieren un usuario autenticado.
     * </p>
     *
     * @return {@code true} si hay un usuario logueado, {@code false} en caso contrario.
     */
    @Override
    public boolean isLoggedIn() {
        return activeUser != null;
    }

    /**
     * Cierra la sesión del usuario actualmente logueado.
     * <p>
     * Este método limpia tanto el usuario activo como todos los datos adicionales
     * almacenados en la sesión. Después de llamar a este método, el sistema
     * queda en un estado sin usuario autenticado.
     * </p>
     */
    @Override
    public void logout() {
        activeUser = null;
        data.clear();
    }

    /**
     * Obtiene el usuario actualmente logueado.
     * <p>
     * Devuelve la referencia al usuario que inició sesión. Si no hay ningún
     * usuario logueado, devuelve {@code null}.
     * </p>
     *
     * @return El usuario actualmente logueado, o {@code null} si no hay ninguno.
     */
    @Override
    public User getActive() {
        return activeUser;
    }

    /**
     * Almacena un objeto en los datos de sesión con una clave específica.
     * <p>
     * Permite guardar información adicional asociada a la sesión del usuario.
     * Si ya existe un objeto con la misma clave, será reemplazado por el nuevo valor.
     * </p>
     *
     * @param key La clave asociada al objeto. No debe ser {@code null}.
     * @param o El objeto a almacenar. Puede ser {@code null}.
     */
    @Override
    public void setObject(String key, Object o) {
        data.put(key, o);
    }

    /**
     * Recupera un objeto de los datos de sesión utilizando su clave.
     * <p>
     * Si no existe ningún objeto almacenado con la clave especificada,
     * este método devuelve {@code null}.
     * </p>
     *
     * @param key La clave asociada al objeto a recuperar. No debe ser {@code null}.
     * @return El objeto almacenado con la clave especificada, o {@code null} si no existe.
     */
    @Override
    public Object getObject(String key) {
        return data.get(key);
    }
}
