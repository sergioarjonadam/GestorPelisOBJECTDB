package org.example.retoconjuntoad_di_2.session;

import org.example.retoconjuntoad_di_2.model.user.User;
import org.example.retoconjuntoad_di_2.utils.SessionService;

import java.util.HashMap;

/**
 * Implementación simple del servicio de sesión para gestionar usuarios logueados.
 * Permite iniciar sesión, verificar si un usuario está logueado, cerrar sesión y almacenar datos adicionales de sesión.
 */
public class SimpleSessionService implements SessionService<User> {

    /**
     * Usuario actualmente logueado en el sistema.
     * Es estático para que sea compartido entre todas las instancias de la clase.
     */
    private static User activeUser = null;

    /**
     * Mapa para almacenar datos adicionales de sesión.
     * Es estático para que sea compartido entre todas las instancias de la clase.
     */
    private static HashMap<String, Object> data = new HashMap<>();

    /**
     * Inicia sesión con el usuario proporcionado.
     *
     * @param user El usuario que inicia sesión.
     */
    @Override
    public void login(User user) {
        activeUser = user;
    }

    /**
     * Verifica si hay un usuario logueado actualmente.
     *
     * @return true si hay un usuario logueado, false en caso contrario.
     */
    @Override
    public boolean isLoggedIn() {
        return activeUser != null;
    }

    /**
     * Cierra la sesión del usuario actualmente logueado.
     * También limpia los datos adicionales de sesión.
     */
    @Override
    public void logout() {
        activeUser = null;
        data.clear();
    }

    /**
     * Obtiene el usuario actualmente logueado.
     *
     * @return El usuario actualmente logueado, o null si no hay ninguno.
     */
    @Override
    public User getActive() {
        return activeUser;
    }

    /**
     * Almacena un objeto en los datos de sesión con una clave específica.
     *
     * @param key La clave asociada al objeto.
     * @param o El objeto a almacenar.
     */
    @Override
    public void setObject(String key, Object o) {
        data.put(key, o);
    }

    /**
     * Recupera un objeto de los datos de sesión utilizando su clave.
     *
     * @param key La clave asociada al objeto.
     * @return El objeto almacenado, o null si no existe.
     */
    @Override
    public Object getObject(String key) {
        return data.get(key);
    }
}
