package org.example.retoconjuntoad_di_2.utils;

/**
 * Interfaz que define el contrato para servicios de gestión de sesión de usuario.
 * <p>
 * Esta interfaz proporciona métodos para gestionar el estado de sesión de un usuario
 * en la aplicación, incluyendo inicio de sesión, verificación de estado, cierre de sesión
 * y almacenamiento de datos adicionales asociados a la sesión.
 * </p>
 * <p>
 * La implementación típica mantiene una referencia al usuario actualmente autenticado
 * y permite almacenar datos adicionales en un mapa clave-valor durante la sesión.
 * </p>
 *
 * @param <T> Tipo del objeto que representa al usuario en la sesión.
 * @author Sistema de Gestión de Películas
 * @version 1.0
 */
public interface SessionService<T> {

    /**
     * Establece el objeto (usuario) como activo en la sesión.
     * <p>
     * Este método debe ser llamado después de validar las credenciales del usuario
     * para iniciar una nueva sesión.
     * </p>
     *
     * @param user El usuario que inicia sesión. No debe ser {@code null}.
     */
    void login(T user);

    /**
     * Indica si hay un usuario actualmente logueado en la sesión.
     * <p>
     * Este método permite verificar el estado de autenticación antes de realizar
     * operaciones que requieren un usuario autenticado.
     * </p>
     *
     * @return {@code true} si hay un usuario logueado, {@code false} en caso contrario.
     */
    boolean isLoggedIn();

    /**
     * Cierra la sesión actual y limpia todos los datos asociados.
     * <p>
     * Este método debe ser llamado cuando el usuario cierra sesión o cuando
     * se necesita invalidar la sesión actual. Limpia tanto el usuario activo
     * como cualquier dato adicional almacenado en la sesión.
     * </p>
     */
    void logout();

    /**
     * Devuelve el objeto activo (usuario logueado) en la sesión actual.
     * <p>
     * Si no hay ningún usuario logueado, este método devuelve {@code null}.
     * </p>
     *
     * @return El usuario actualmente logueado, o {@code null} si no hay sesión activa.
     */
    T getActive();

    /**
     * Guarda un objeto cualquiera asociado a una clave en la sesión.
     * <p>
     * Permite almacenar datos adicionales durante la sesión que pueden ser
     * recuperados posteriormente usando la misma clave. Útil para almacenar
     * información temporal o de contexto que necesita estar disponible durante
     * toda la sesión.
     * </p>
     *
     * @param key La clave asociada al objeto. No debe ser {@code null}.
     * @param o El objeto a almacenar. Puede ser {@code null}.
     */
    void setObject(String key, Object o);

    /**
     * Recupera un objeto asociado a una clave de la sesión.
     * <p>
     * Si la clave no existe o no se ha almacenado ningún objeto con esa clave,
     * este método devuelve {@code null}.
     * </p>
     *
     * @param key La clave asociada al objeto a recuperar. No debe ser {@code null}.
     * @return El objeto almacenado con la clave especificada, o {@code null} si
     *         no existe ningún objeto con esa clave.
     */
    Object getObject(String key);
}

